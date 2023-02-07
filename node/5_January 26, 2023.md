# January 26, 2023

### **HTTP 요청**

- 요청 메세지의 구성

    - 요청 라인

    - 요청 헤더
    - 요청 바디(엔티티) → 요청 메세지의 종류에 따라 바디를 사용하기도, 안하기도 한다.
- 요청 라인
    - 요청 메소드 : `GET`, `POxwST`, `PUT`, `DELETE`

        - `GET` : 일반적으로 웹 브라우저에서 컨텐츠를 요청하기 위한 메소드

        - `POST` : 글을 쓰거나 사진을 올리는 요청에서 사용, 전송하는 데이터를 서버에서 처리할 것을 요청하는 메소드
    - 요청 URL
    - HTTP 버전 : 예) `HTTP/1.1`

- 요청 헤더
    - 헤더는 **키 : 값**으로 구성

    - 주요 요청 헤더
        - `Accept`, `Cookie`, `Content-Type`, `Content-Length` ..

<br>

### **HTTP 요청 정보를 전달할 때**

- 메세지의 바디를 사용하지 않는 경우

    - URL을 이용해서 요청 정보 전달

        - `GET`, `TRACE` 메소드를 이용

    - 경로나 쿼리스트링 사용

        - 경로 `http://idols.com/snsd/taeyon.png`

        - 쿼리스트링 `http://idols.com/q?group=snsd&name=jessica`

    - **메세지의 바디(엔티티)를 사용하지 않는 경우라면 요청의 URL에 경로나 쿼리스트링을 이용해 컨텐츠의 정보를 기술한다.**
- 메세지의 바디를 사용하는 경우
    - URLEncoded 방식

        - 메세지 헤더(Contents-Type) `application/x-www-form-urlencoded`

            - **이름=값** 형태로 데이터를 body에 작성

            - 이걸 보고 url encoding type이구나 알고 쿼리스트링을 잘라서 해석
        - 메세지 바디 : 쿼리스트링
        - 이렇게 작성한 바디 메세지를 분석하기 위해서는 querystring 모듈 필요
    - JSON/XML 방식

        - 메세지 헤더(Contents-Type) `application/json`

        - 메세지 바디 : **이름:값**, 이름:값 … 형태
    - **멀티파트 방식** ⭐
        - 바이너리 파일을 올리는 경우에 주로 사용

        - 하나의 메세지 바디에 파트를 나눠서 작성
        - 메세지 헤더(Contents-Type) : `multipart/form-data; boundary=XXXYYYZZZ`
        - 메세지 바디 : 각 파트를 구분자로 구별해 작성, 개별 파트에는 파트에 작성된 데이터를 식별할 수 있는 세부 Content-Type이 추가됨

<br>       

### **HTTP 응답**

- 응답 메세지의 구성

    - 응답 라인

    - 응답 헤더
    - 응답 바디 → 대부분의 응답 메세지는 메세지 바디를 사용함
- 응답 라인
    - HTTP 버전

    - 상태 코드 (Status Code)
        - 1xx : 정보
        - 2xx : 성공
        - 3xx : 리다이렉션
        - 4xx, 5xx : 요청 오류, 서버 오류
    - 상태 메시지
    - 예) HTTP/1.1 200 OK
- 응답 헤더
    - Content-Type, Content-Length : 바디 데이터의 타입과 크기

    - Set-Cookie : 서버가 클라이언트에게 쿠키를 저장하라는 헤더 필드
    - ETag : 리소스 캐시와 관련, 엔티티 태그
- 응답 바디
    - 바디 데이터 : HTML, XML/JSON, Octet Stream ..
    - 클라이언트가 데이터를 정확하게 해석하려면 데이터의 인코딩 방식을 알려야 함 → 이때 헤더의 Content-Type 헤더필드를 사용함

    - 주요 Contents-Type
        - text/plain, text/html

        - application/json, application/xml
        - image/jpg, video/mp4, audio/mp3
        - Content-Type과 실제 바디 데이터의 타입이 안맞으면 → 제대로 데이터 표현이 안됨

<br>

### **HTTP 모듈**

HTTP 서버를 생성하거나 HTTP 클라이언트를 작성할 수 있는 기능을 제공

- 기본 모듈 `var http = require(’http’)`

- 모듈 클래스
    - Server

        - `http.Server` : HTTP 서버

        - `http.InComingMessage` : HTTP 서버로 도착하는 클라이언트의 요청 메세지, Readable Stream
        - `http.ServerResponse` : HTTP 서버의 응답 클래스 (서버→클라이언트)
    - Client
        - `http.Client` : HTTP 클라이언트

        - `http.ClientRequest` : HTTP 클라이언트 요청 메시지 (클라이언트→서버)
        - `http.InComingMessage` : HTTP 클라이언트가 서버에서 전달받는 응답 메세지, Redable Stream
    - Client(`ClientRequest`) **→** Server(`InComingMessage`)
    - Client(`InComingMessage`) **←** Server(`ServerResponse`)
    

<br>


### **HTTP 서버**

- 서버 생성 `var server = http.createServer([requestListener])`
- HTTP 서버 주요 이벤트
    - request : 클라이언트의 요청 도착

    - connection : 소켓 연결
    - close : 서버 종료
- HTTP 서버 메소드
    - `server.listen()` : 서버의 네트워크 포트와 결합해 해당 포트로 전달되는 요청에 반응

    - `server.close()` : 추가적인 클라이언트 요청을 더이상 안 받음
    - `server.setTimeout()` : 요청을 처리하는 제한 시간 설정
- HTTP 서버 동작시키기
    - 서버 객체 생성 `var server = http.createServer()`

    - 클라이언트 접속 대기 `server.listen(port)`
- HTTP 서버 구동 코드
    
    ```jsx
    var http = require('http');
    var server = http.createServer(function(req, res) {
    	res.write('Hello World');
    	res.end();
    }).listen(3000);
    ```
    

<br>

### **HTTP 요청 분석하기**

- 클라이언트의 요청 분석

- request 이벤트 리스너의 파라미터 `req`
    - InComingMessage 타입(클라이언트가 서버에게 요청한 메시지)

    - `req.url` : 요청 url, 경로와 쿼리 문자열
    - `req.method` : 요청 메소드
    - `req.headers` : 요청 메시지의 헤더
    - `req(streamable)` : 요청 메시지 바디 → 스트림을 다루는 방식
- 요청 URL 경로와 쿼리 문자열 분석, URL 분석할 땐 URL 모듈 사용
    - `var url = require(’url’)`

    - `url.parse(req.url, **true**)` → true로 했기 때문에 객체로 파싱됨
- 요청 메시지 헤더 분석
    - `var headers = req.headers`

    - `headers.content-type`
    - `headers.user-agent`
- 요청 메시지 바디 분석 → InComingMessage의 data 이벤트 사용

<br>

### **HTTP 응답**

- 서버에서 요청 메시지 분석이 끝나면 요청 처리 후 클라이언트에게 응답 메시지 전송

- `http.ServerResponse` 클래스 사용
- 메시지 상태, 응답 메세지의 헤더 라인의 상태 코드, 상태 메시지
    - `res.statusCode`

    - `res.statusMessage`
- 메시지 헤더
    - `res.writeHead()` : 상태 코드와 헤더 필드를 동시에 작성할 수 있음

    - `res.setHeader()` : 헤더 필드의 이름과 필드 값을 별도의 파라미터로 분리해 입력
    - `res.removeHeader()`
    - `res.getHeader()`
- 메시지 바디, 스트림 모듈의 WritableStream에 정의된 메소드와 같음
    - `res.write()`

    - `res.end()`
    - 응답 메시지 작성을 마치면 `end()`를 이용해 메시지가 끝났다는 것을 알려야 함
    - `end()`에도 메시지 바디에 기록되어야 하는 데이터를 파라미터로 입력 가능
- 주의사항

    - 응답 메시지의 헤더는 바디를 작성하기 전까지만 작성 가능
        - 헤더 → 바디 순으로!

        - `setHeader, writeHead` → `write, end`
    - setHeader에 text/plain 형태로 작성해두고 write 함수에 html 형식으로 작성하면

        - plain 형태로 나옴 .. Header에 명시된 것이 우선인 듯 함
 
<br>


### HTTP 서버 작성

- 정적 파일 요청

    - 정적인 컨텐츠 (이미지, html, 음악 등) 요청 → 미리 작성된 파일로 응답
        - 예) http://www.geniuus.com/image/cat.png

        - 요청 경로 `req.url`
        - 요청 경로 분석 `path 모듈`
    - 순서
        - 요청 메세지에서 파일의 접근 정보를 분석 `URL, Path 모듈`

        - 파일을 읽고 `fs 모듈` → `fs.readFile(filepath, callback)`
        - 그 내용을 응답 메시지로 보냄 `res.write(), res.end()`
    - 정적 파일 요청에 대한 응답
        
        ```jsx
        var http = require('http');
        var fs = require('fs');
        
        var server = http.createServer(function(req, res) {
        	fs.access('./ca11.jpg', function(err) { // 해당 경로의 파일 존재 확인하고
        		if ( err ) { // 경로에 파일이 없으면
        			res.statusCode = 404; // 404 에러
        			res.end();
        			return;
        		}
        		fs.readFile('./cat.jpg', function(err, data) { // 파일을 읽고	
        			res.statusCode = 200;
        			res.setHeader('Content-type', 'image/jpg'); // 이미지라면		
        			res.end(data); // 응답 메시지의 바디에 data 작성, 응답 메시지로 img를 주는 코드
        		});
        	});
        }).listen(3000);
        ```
        

<br>


### **정적 파일 요청 - 스트림 파이프**

- 입력 스트림

    - `fs.createReadStream()`

    - fs 모듈을 이용해 createReadStream으로 파일에서 입력 스트림을 얻어옴
- 출력 스트림
    - `res`

    - 응답 메시지가 출력 스트림이므로 별도로 스트림을 얻는 작업이 필요 없음
- 파이프로 연결
    - `fs.createReadStream(path).pipe(res)`
    - 입력 스트림을 출력 스트림으로 바로 연결

    - 스트림으로 연결 시, 입력 스트림의 값을 다 보내면 자동으로 출력 스트림이 종료됨
    - 스트림 방식으로 정적 리소스를 응답하면 서버 반응성이 좀 더 좋아짐

<br>


### **정적 파일 서비스**

- 요청 URL의 경로를 실제 파일 경로로 매핑

    - 예) myServer.com/resource/image.png → `./resource/image.png`

- 요청 URL에서 바로 파일 경로 얻어내기

    - `var pathUtil = require(’path’)`

    - `var path = __dirname + pathUtil.sep + ‘resources’ + req.url`
        - __dirname : 현재 폴더 `.`
        
        - pathUtil.sep : 경로 구분자 `/`
        - ‘`resources`’
        - req.url : url 문자열 `image.png`
        
        ⇒ 다 합치면 `./resource/image.png`
        

<br>


### **웹 페이지 서비스**

```jsx
var http = require('http');
var url = require('url');
var server = http.createServer(function(req, res) {
	// URL 분석 : 쿼리 문자열
	var parsed = url.parse(req.url, true);
	var query = parsed.query;
	
	// start와 end
	var start = parseInt(query.start);
	var end = parseInt(query.end);
	
	if ( !start || !end ) {
		res.statusCode = 404;
		res.end('Wrong Parameter');
	}
	else {
		// 합계 구하기
		var result = 0;
		for(var i = start ; i < end ; i++) {
			result += i;
		}
		res.statusCode = 200;
		res.end('Result : ' + result);
	}

}).listen(3000);

// http://127.0.0.1:3000/cal?start=1&end=10
```