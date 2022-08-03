# August 2, 2022

## Network를 이용한 깃헙 사용자 프로필 검색하기 - GithubUserProfile

깃헙 사용자 아이디를 입력하면, 해당 사용자의 프로필을 불러옴

- Network 개념만 잠깐 !
    - URLSession이 network의 주체, configuration을 보고 세션을 생성함
    - 세션을 생성해서 어떤 일을 할 건지에 따라 task 생성 (dataTask, ..)
    - combine을 이용한 dataTaskPublisher가 있는데 그것을 이용하면 리턴값이 data와 URLResponse의 튜플로 들어옴 (그래서 tryMap으로 data만 분리함)
    <br>
- `setupUI()`
    - 썸네일 이미지가 동그란 형태이므로, conerRadius 설정
    
    ```swift
    private func setupUI() {
    	  thumbnail.layer.cornerRadius = 80
    }
    ```
    <br>    
- `embedSearchControl()`
    - SearchController 삽입
    
    ```swift
    private func embedSearchControl() {
        self.navigationItem.title = "Search"
        
        let searchController = UISearchController(searchResultsController: nil)
        searchController.hidesNavigationBarDuringPresentation = false
        searchController.searchBar.placeholder = "geniusYoo"
        searchController.searchResultsUpdater = self
        searchController.searchBar.delegate = self
        self.navigationItem.searchController = searchController
    }
    ```
    
    - `searchResultUpdater` 은 searchBar에 한글자 한글자 입력될 때마다 tracking
    - `searchBar.delegate` 는 클릭되었을 때 처리하려고 설정해줌
    <br>    
- `bind()`
    - 검색해서 받은 데이터로 프로필이 업데이트 될 수 있도록 컬렉션뷰를 업데이트하는 update() 함수를 내장, 변경사항을 구독할 수 있도록 subscibe 코드 작성
    - 위에 user이라는 변수는 `@Published` 형태로 선언되어 있음
    
    ```swift
    private func bind() {
      $user
        .receive(on: RunLoop.main)
        .sink { [unowned self] result in
            self.update(result)
        }.store(in: &subscriptions)
    }
    ```
    <br>    
- `update()`
    - 업데이트 된 데이터를 바탕으로 컬렉션뷰를 업데이트 하는 함수
    
    ```swift
    private func update(_ user: UserProfile?) {
      guard let user = user else {
          self.nameLabel.text = "n/a"
          self.loginLabel.text = "n/a"
          self.followerLabel.text = ""
          self.followingLabel.text = ""
          self.thumbnail.image = nil
          return
      }
      self.nameLabel.text = user.name
      self.loginLabel.text = user.login
      self.followerLabel.text = "follower : \(user.followers)"
      self.followingLabel.text = "following : \(user.following)"
      
      self.thumbnail.kf.setImage(with: user.avatarUrl)
    }
    }
    ```
    
    - kf (KingFish) : url 형태로 들어오는 이미지를 이미지형태로 변환시킴
    - searchBar에 아무것도 검색하지 않고 엔터를 쳤을 경우, user에 nil값이 들어있을 수 있기 때문에 user을 guard let 구문으로 Nil일때의 처리를 해줌
    <br>
- searchBar에 아이디를 입력하고 Enter 눌렀어! : `searchBar.delegate` 프로토콜의 `searchBarSearchButtonClicked()`
    - keyword : SearchBar에 입력한 텍스트, 비어있는지 확인하기 위해 isEmpty 사용
        - `guard let keyword = searchBar.text ,!keyword.isEmpty else { return }`
      <br>      
    - Resource
        - URL 구성
            - base : 변하지 않는 url 부분 `let base = "https://api.github.com/"`
            - path : username에 따라 바뀌는 url 부분 `let path = "users/\(keyword)”`
            - params : 추가적으로 전달해야하는 parameter `let params: [String: String] = [:]`
            - header : 어떤 형태로 받아오는지에 대한 header `let header: [String: String] = ["Content-Type": "application/json"]`
        - request
            - URL을 받아서 요청을 하려면 url과 request 메소드를 같이 서버에 요청해야 함
            - URLComponents에 base+path url 넣어주고 `var urlComponents = URLComponents(string: base + path)!`
            - URLComponents에 파라미터 추가하는 queryItems
                
                ```swift
                let queryItems = params.map { (key: String, value: String) in
                		return URLQueryItem(name: key, value: value)
                }
                urlComponents.queryItems = queryItems
                ```
                
                - params가 String: String 형태이기 때문에 map을 이용해URLQueryItem 형태로 변환해줌
            - request 만들기 `var request = URLRequest(url: urlComponents.url!)`
            - header 설정
                
                ```swift
                header.forEach { (key: String, value: String) in
                    request.addValue(value, forHTTPHeaderField: key)
                }
                ```
                
            - request 리턴해주면 끝 !
            - 그러면 이제 자원을 다 만들어서 요청하는 과정까지 끝난 것이당
    <br>            
    - Network
        - 만들어진 자원을 가지고 dataTaskPublisher를 이용해 데이터를 가져옴
        - networkService class의 load()
        
        ```swift
        guard let request = resource.urlRequest else {
            return .fail(NetworkError.invalidRequest)
        }
        
        return session
            .dataTaskPublisher(for: request)
            .tryMap { result -> Data in
                guard let response = result.response as? HTTPURLResponse,
                      (200..<300).contains(response.statusCode)
                else {
                    let response = result.response as? HTTPURLResponse
                    let statusCode = response?.statusCode ?? -1
                    throw NetworkError.responseError(statusCode: statusCode)
                }
                return result.data
            }
            .decode(type: T.self, decoder: JSONDecoder())
            .eraseToAnyPublisher()
        }
        ```
        
        - URLSession을 아까 리턴한 request를 가지고 dataTaskPublisher로 만들어줌
        - dataTaskPublisher는 데이터를 뿌려줄 때 <data, Error> 의 튜플로 뿌려주는데, 우리가 디코딩할 때 필요한 것은 data만 필요하므로 tryMap으로 data만 걸러줌
            - 거르기 전에, response가 정상인지 확인하기 위해 statusCode가 200대인지 확인하고 정상이 아니라면 `networkError.responseError(statusCode)` 로 오류를 던져줌
            - 정상이라면 else 구문을 뛰어넘고 result (dataTaskPublisher가 뿌려준 튜플)의 data만 리턴
        - JSONDecoder를 이용해서 decode
    <br><br>  
        

## 깃헙 사용자 프로필을 검색하기 (추가 기능) - GithubUserSearch

SearchBar에서 사용자 아이디를 검색하면, 그 철자가 들어가는 사용자들을 모두 검색해 리스트로 보여줌

- 리스트로 보여줘야하기 때문에 Cell을 구성하는 `configureCollectionView`
    
    ```swift
    private func configureCollectionView() {
      dataSource = UICollectionViewDiffableDataSource<Section, Item>(collectionView: collectionView, cellProvider: { collectionView, indexPath, item in
          guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ResultCell", for: indexPath) as? ResultCell else { return nil}
          cell.user.text = item.login
          return cell
      })
      collectionView.collectionViewLayout = layout()
    }
    ```
    <br>    
- `bind()`
    - users는 배열인데, 배열의 내용이 바뀔때마다 snapshot에 넣어 dataSource에 apply
    
    ```swift
    private func bind() {
      $users
        .receive(on: RunLoop.main)
        .sink { users in
            var snapshot = NSDiffableDataSourceSnapshot<Section, Item>()
            snapshot.appendSections([.main])
            snapshot.appendItems(users, toSection: .main)
            self.dataSource.apply(snapshot)
        }.store(in: &subscriptions)
    }
    ```
    <br>    
- `searchBarSearchButtonClicked()`
    - Resource는 위와 같지만 params에 내가 입력한 keyword가 param으로 들어감 → 그 철자가 들어간 모든 사용자를 검색할 수 있게 함
        
        ```swift
        let resource = Resource<SearchUserResponse>(
            base: "https://api.github.com/",
            path: "search/users",
            params: ["q": keyword],
            header: ["Content-Type" : "application/json"]
        )
        ```
    <br>        
    
    - Network
        
        ```swift
        URLSession.shared.dataTaskPublisher(for: request)
          .map { $0.data }
          .decode(type: SearchUserResponse.self, decoder: JSONDecoder())
          .map { $0.items }
          .replaceError(with: [])
          .receive(on: RunLoop.main)
          .assign(to: \.users, on: self)
          .store(in: &subscriptions)
        ```
        
        - dataTaskPublisher 가 data와 Error를 반환해주는데, 그 중 첫번째인 data만 거르는 작업 `.map { $0.data }`
            - 밑에서 Error도 처리하는데, Error가 나면 빈 배열을 반환하는 `replaceError(with: [])`
        - 디코딩 하고 `.decode(type: SearchUserResponse.self,decoder: JSONDecoder())`
        - SearchUserResponse 타입으로 디코딩을 하면 저 안에는 items라는 배열이 생김. 이 배열은 검색된 사용자들 배열
        - map으로 그 items 배열을 꺼냄
        - assign으로 꺼낸 걸 users라는 SearchResult 형태의 배열에다 바로 꽂아넣음 `.assign(to: \.users, on: self)`
        - subscriptions에 저장
