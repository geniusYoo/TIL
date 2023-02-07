# January 27, 2023

### **HTTP GET, POST**

- GET 요청

    - URL로 요청 정보 전달, URL만 분석

    - 길이 제한O, 암호화에 불리
    - 메시지의 바디를 사용하지 않음
    - 속도가 빠름
- POST 요청
    - 메시지 바디(엔티티)로 요청 정보 전달

    - 바디 분석 필요
    - 사진을 업로드하거나 글을 쓰는 요청에 해당
    - 길이 제한X, 암호화에 유리
    - 요청 처리 속도가 느림

<br>

### **HTTP POST - Form Encoding**

HTML의 폼에서 submit 버튼을 누르거나 함수 호출로 submit 되면 요청 메시지가 만들어짐

- form 요청 → 요청 바디에 요청 정보 작성

- 요청 바디에 메시지 인코딩 방식 작성 `enctype`
    - `application/x-www-form-urlencoded`

    - `multipart/form-data`
    - `text/plain`
    - 웹 브라우저에서는 urlencoded, multipart 인코딩을 많이 사용함
- urlencoded
    - **이름=값** 방식으로 작성, 쿼리 문자열

    - URL을 구성하는 쿼리 문자열과 비슷하지만 메시지 인코딩 방식은 조금 다름
    - `key1=value1&key2=value2`
- multipart
    - 파일, 글자 등 여러 데이터 전송

    - form 태그의 enctype 속성에 `multipart/form-data` 입력 → 입력 안하면 디폴트로 urlencoded
    - 메시지 헤더의 Content-Type 필드의 값에 boundary(파트 구별자)가 추가됨
    
<br>


### **HTTP POST 요청 처리**

HTTP 클라이언트(웹 브라우저)가 POST 방식으로 요청 메시지를 보내면, 서버는 메시지의 바디를 분석해야 함

- 요청 메시지 `req.InComingMessage`

    - 입력 스트림이기도 하므로 스트림에서 데이터를 읽어오는 방식으로 작성
- Readable Stream
- 이벤트 `data, end`

```jsx
var body = "";

req.on('data', function(chunk) { // 요청 메시지의 바디를 모두 읽어들일 때 까지 계속 동작
	console.log('%d bytes', chunk.length);
	body += chunk; // 버퍼링
});

req.on('end', function() { // 요청 메시지의 바디를 모두 읽어왔을 때, 메시지 분석이 가능한 상태
	// urlencoded 방식이라 가정, querystring 모듈 사용해 분석
	var parsed = querystring.parse(body);
	console.log('name1 : ' + parsed.name1); // 바디 메시지 중 name1, name2의 값을 콘솔로 출력
	console.log('name2 : ' + parsed.name2);
});
```
<br>


### **PRG 패턴**

Post-Redirect-Get, 중복 POST 요청을 방지하는 패턴

- POST 요청 처리 후 redirect 응답

- refresh → GET 요청 중복 OK
- POST 요청 메시지 바디 분석이 끝나고 서버의 동작을 마치면 리다이렉션하는 응답 메시지를 클라이언트에게 보냄

```jsx
var http = require('http');
var querystring = require('querystring');

var movieList = [{ title: '스타워즈4', director: '조지루카스' }];

var server = http.createServer(function (req, res) {
    if (req.method.toLowerCase() == 'post') { // POST 요청이면 영화를 추가
        addNewMovie(req, res);
    }
    // GET이면 목록 출력
    else {
        showList(req, res);
    }
});
server.listen(3000);

function showList(req, res) {
    res.writeHeader(200, { 'Content-Type': 'text/html; charset=UTF-8' });
    res.write('<html>');
    res.write('<meta charset="UTF-8">');
    res.write('<body>');

    res.write('<h3>Favorite Movie</h3>');
    res.write('<div><ul>');

		// DB 역할을 하는 movieList 배열을 돌면서 응답 메시지에 작성
    movieList.forEach(function (item) {
        res.write('<li>' + item.title + '(' + item.director + ')</li>');
    }, this);
    res.write('</ul></div>');

    res.write(
        '<form method="post" action="."><h4>새 영화 입력</h4>' +
        '<div><input type="text" name="title" placeholder="영화제목"></div>' +
        '<div><input type="text" name="director" placeholder="감독"></div>' +
        '<input type="submit" value="upload">' +
        '</form>'
        );
    res.write('</body>');
    res.write('</html>');
    res.end();
}

function addNewMovie(req, res) {
    var body = '';
    req.on('data', function(chunk) {
        body += chunk;
    });
    req.on('end', function() {
				// POST 요청 처리가 끝났으니 querystring 모듈로 분석해서 각 프로퍼티 분리
        var data = querystring.parse(body);
        var title = data.title;
        var director = data.director;
        
				// 배열에 추가된 영화를 집어넣고
        movieList.push({title:title, director:director});
        
				// PRG 패턴 이용하여 redirection
        res.statusCode = 302;
        res.setHeader('Location', '.');
        res.end();
    });
}
```
<br>


### **멀티파트 요청 ⭐**

- 예) 사진, 글+사진 올리기

- 메시지 바디 기록 방식 `multipart/form-data`
- 메시지 바디 내 파트 구성
    - 파트 구분자 (XXXYYYZZZ)

    - 파트 인코딩
    - 파트 내 정보

<br>


### **멀티파트 요청 분석 ⭐**

- 메시지 요청 헤더

    - `Content-type : multipart/form-data; boundary=XXXYYYZZZ`
- 컨텐츠 타입 분석

    - `var contentType = req.headers[’content-type’]`

- 구분자 알아내기
    - `var boundary = element.split(’=’)[1]`

    - boundary=XXXYYYZZZ 이렇게 되어있으니 split으로 자르고 배열 인덱스 [1]이 XXXYYYZZZ 가 되므로 구분자를 알아낼 수 있음!

<br>

### **멀티파트 분석 모듈, formidable ⭐**

멀티파트 메시지 분석용 모듈

- 클래스
    - `Formidable.InComingForm` : 요청 분석 클래스

    - `Formidable.File` : 업로드 된 파일 정보

- Formidable.InComingForm의 이벤트, 프로퍼티
    - 이벤트
        - `field` : 이름, 값 도착

        - `file` : 파일 도착
        - `aborted` : 요청 중지 (클라이언트)
        - `end` : 종료
    - 프로퍼티
        - `form.uploadDir` : 메시지 바디의 파일을 분석하면 이를 저장할(업로드할) 폴더 위치

        - `form.keepExtension` : 확장자 보존
        - `form.multiples` : 다중 파일 업로드
- 메시지 바디 분석
    - `form.parse(req, function(err, fields, files) {} )` → form : InComingForm의 객체

        - fields : 이름-값 데이터

        - files : 업로드 파일 정보
- Formidable.File
    - `file.size` : 업로드 된 파일 크기(바이트)

    - `file.path` : 파일 경로
    - `file.name` : 파일 이름
    - `file.type` : 파일 타입
    - `file.lastModifiedDate` : 최종 변경일
    - `file.hash` : 해쉬 값
- 멀티파트 메시지로 전송되는 파일이 전송되면 임시 이름으로 저장되고, 저장되는 디렉토리 위치는 기본적으로 OS의 임시 폴더 위치임

    - 파일이 저장되는 디렉토리의 위치는 `InComingForm` 클래스의 `uploadDir` 프로퍼티로 설정 가능

    - 업로드되는 파일의 이름은 임의의 이름으로 변경됨 → 그러므로 같은 이름의 파일을 업로드해도 이름이 겹치는 에러가 발생하지 않음

<br>


### **파일 업로드 서비스**

- 파일 업로드 후 ..

    - 파일을 임시 폴더 → 리소스 저장소로 이동
    
    - 리소스 저장소에서 충돌 나지 않도록 이름 변경
    - 날짜, 일련번호, 사용자 계정 …

```jsx
var fs = require('fs');
var pathUtil = require('path');

// 저장될 파일 경로 지정
var uploadDir = __dirname + '/upload';

// 저장될 이미지파일 경로
var imageDir = __dirname + '/image';

var http = require('http');
var formidable = require('formidable');

// DB 대신 쓰는 배열
var paintList = [];

var server = http.createServer(function (req, res) {
   // 루트 경로로 요청
   if (req.url == '/' && req.method.toLowerCase() == 'get') {
      showList(res);
   }
   // <img> 태그로 인한 이미지 요청
   else if (req.method.toLowerCase() == 'get' && req.url.indexOf('/image') == 0) {
      var path = __dirname + req.url;
      res.writeHead(200, { 'Content-Type': 'image/jpeg' })
      fs.createReadStream(path).pipe(res);
   }   
   // 업로드 요청
   else if (req.method.toLowerCase() == 'post') {
      addNewPaint(req, res);
   }

});

function showList(res) {
   res.writeHeader(200, { 'content-type': 'text/html' });

   var body = '<html>';
   body += '<head><meta charset="UTF-8"></head>';
   body += '<body>';
   body += '<h3>Favorite Paint</h3>';

   body += '<ul>';
   paintList.forEach(function (item, index) {
      body += '<li>';
      if (item.image) {
         body += '<img src="' + item.image + '" style="height:100pt"></img>';
      }
      body += item.title;
      body += '</li>';
   });
   body += '</ul>';

   body += '<form action="." enctype="multipart/form-data" method="post">' +
   '<div><label>작품 이름 : </label><input type="text" name="title"></div>' +
   '<div><label>작품 이미지 : </label><input type="file" name="image" value="작품 파일 선택"></div>' +
   '<input type="submit" value="upload">' +
   '</form>';
   body += '</body></html>';

   res.end(body);
}

server.listen(3000, function () {
   console.log('Server is running on 3000');
});

function addNewPaint(req, res) {
    var form = formidable.IncomingForm();
    form.uploadDir = uploadDir;
    
		// HTML 폼에서 입력한 내용을 parse 함수로 파싱
    form.parse(req, function(err, fields, files) {
        var title = fields.title; // 글자 필드는 fields에서
        var image = files.image; // 파일 필드는 files에서
        
        console.log(image);
        
        var date = new Date();
        var newImageName = 'image_' + date.getHours() + date.getMinutes() + date.getSeconds();
        var ext = pathUtil.parse(image.name).ext; // 확장자 얻기
        
        var newPath = __dirname + '/image/' + newImageName + ext;
        
        fs.renameSync(image.path, newPath);
        
        var url = 'image/' + newImageName + ext;
        
        var info = {
            title : title, image:url
        }
        
        paintList.push(info); // 객체 만들어서 넣는 코드

        //PRG 패턴
        res.statusCode = 302;
        res.setHeader('Location', '.');        
        res.end('Success');
        
    });
}
```