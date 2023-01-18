# January 19, 2023

- ### node.js 입문 
    - javascript 언어로 이루어짐

    - 비동기 I/O
    - 이벤트 기반
    - 네트워크 어플리케이션을 위한 플랫폼 (Server 개발에 유용)
    - Framework
    - Single Thread

<br>

- ### 동기 vs 비동기 방식의 코드 차이
    - 동기식

        ```javascript
        function add(i, j) {
            return i + j;
        }

        var result = add(1, 2);
        console.log('result : ', result);
    - 비동기식
        ```javascript
        function add(i, j, callback) {
            var result = i + j;
            callback(result);
        }

        add(1, 2, function(result) {
            console.log('result : ', result);
        });
        ```
    - 동기식은 return으로 처리, 비동기식은 callback으로 처리

<br>

- ### 비동기 방식의 API는 에러를 다룰 수 있도록 callback의 첫번째 parameter를 error로 사용한다.

    ```javascript
    callbackFunc(arg1, arg2, function(err, result) {
        if(err) {
            // error handling
            return;
        }

        // code
    })
    ````


<br>

- ### Module
    - 다른 언어의 라이브러리라고 생각하면 됨

    - node.js의 간단한 구조

    - 필요한 모듈을 로딩

<br>

- ### Use Module 
    - Module Loading
        - ` var readline =require('module name') `

    - Create Object
        - ` var rl = readline.createInterface();`

    - Use Method
        - ` rl.setPrompt('>>'); `

<br>

- ### 전역 객체 process
    - 어플리케이션 프로세스 실행 정보 : env, version, arch, platform, argv

    - 이벤트 : exit, beforeExit, uncaughtException

    - 함수 : exit, nextTick(이벤트 루프 내 동작을 모두 실행 후 callback 실행)

<br>

- ### Timer
    - 지연 동작 : ``setTimeout``

    - 반복 동작 : ``setInterval``

    - 타이머 취소 : ``clearTimeout()``

<br>

- ### Console
    - 객체에 어떤 값이 들어있을 지 알고싶을 때 ***,***(콤마) 사용하면 객체의 내용을 나열해 표현해줌

    - +(더하기) 기호를 사용하면 문자열 형태로 변환해 출력

    - 실행시간 측정
        - 시작 시점 설정 : ``console.time(TIMER_NAME)``
        
        - 종료 시점 설정 : ``console.timeEnd(TIMER_NAME)``

<br>

- ### Utility Module
    - Module Loading
        - ``var util = require('util');``

    - format

    - inherit

<br>

- ### Event
    - 이벤트 다루는 **EventEmitter**

    - node.js는 이벤트 처리를 하는 경우가 많음. 예를들어 클라이언트의 접속 요청, 소켓에 데이터 도착, 파일 열기/읽기 완료 등등 ..
    - 이벤트 처리는 비동기 처리, 리스너 함수 사용

    - 이벤트 함수 등록
        - `emitter.addListener(event, listener)`
        - `emitter.on(event, listener)` : 얘를 많이 씀 !!
        - `emitter.once(event, listener)` : 이벤트가 발생한 첫 번째에만 작동
    
            ``` javascript
            process.on('exit', function() {
                console.log('occur exit event');
            })

            process.once('exit', function() {
                console.log('occur exit event once');
            })
            ```   
    - custom event
        - EventEmitter 객체에 커스텀 이벤트
            ``` javascript
            var custom = new event.EventEmitter(); // Emitter 객체 생성

            custom.on('tick', function() { // 이벤트 리스너 등록
                console.log('occur custom event');
            })

            custom.emit('tick'); // 이벤트 강제로 발생시킴
            ```

<br>

- ### Path
    - path 모듈
        - ` var pathUtil = require('path');`
    
    - path.
        - basename() : 파일 이름

        - dirname() : 파일이 포함된 폴더 경로
        - extname() : 확장자

    - `path.parse()` : 경로를 분석해 객체 형태로 제공

<br>

- ### File System
    - 파일 시스템 모듈
        - `var fs = require('fs');`
    
    - 비동기식, 동기식 모두 제공
        - 비동기식 
            - `fs.readFile('a.txt', 'utf-8', function(err, data){});`
            - callback 함수의 err 파라미터 사용
        
        - 동기식
            - `var data = fs.readFileSync('a.txt', 'utf-8');`
            - try ~ catch 문 사용

        - 동기식에는 메소드에 *Sync*가 붙음!

    - 파일을 다룰 때는 경로명 또는 파일 디스크립터로 파일을 다룬다.

    - 파일 접근 여부 확인 후 읽기
        - 동기식
            ```javascript
            try {
                fs.accessSync(file, fs.F_OK);
                console.log('file access OK');
                var data = fs.readFileSync(file, 'utf-8');
                console.log('file contents : ', data);
            }
            catch(exception) {
                // File not exists
                console.log('file not exists : ', exception);
            }
            ```
        - 비동기식
            ```javascript
            fs.access(file, fs.F_OK, function(err) {
                if(err) {
                    // error handling
                }
                fs.readFile(file, 'utf-8', function(err, data) {
                    if(err) {
                        // error handling
                    }
                    
                    console.log(data);
                })
            });
            ```
    - 파일 상태
        - `fs.stat`
        - `fs.statSync`

