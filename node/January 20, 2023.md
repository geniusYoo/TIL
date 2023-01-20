# January 20, 2023

### **Cluster**

- 여러 시스템을 하나로 묶어서 사용하는 기술

- node.js에서 클러스터 모듈은 하나의 시스템에 포함된 프로세스나 코어를 하나처럼 동작시키는 기능을 제공
- node.js는 1개의 싱글 스레드로 동작하는데 클러스터를 이용해 멀티코어의 성능을 효율적으로 사용
- 클러스터링 : 마스터와 워커 프로세스
    - 마스터 : 메인 프로세스, 워커를 생성
    - 워커 : 보조 프로세스, 동시에 동작하는 워커 프로세스의 개수는 CPU의 코어 수보다 많지 않도록 작성하는 것이 좋음
- 클러스터 모듈 `var cluster = require(’cluster’);`
- 클러스터 생성 `cluster.fork();`
- 클러스터 구분하기
    - `cluster.isMaster`
    - `cluster.isWorker`
    
<br>

### **Cluster의 이벤트**

- Master
    - fork : 워커 생성
    - online : 워커 생성 후 동작
    - listening : 워커에 작성한 서버의 listen 이벤트 (client의 접속을 listen)
    - disconnect : 워커 연결 종료
    - exit : 워커 프로세스 종료
- Worker
    - message : 메세지 이벤트
    - disconnect : 워커 연결 종료
    
<br>

### **Worker**

- 워커 접근 `cluster.worker`
- 워커 식별자 (워커 id) `worker.id`
- 워커 종료 `worker.kill([signal=’SIGTERM’])`

<br>

### **Cluster의 생성과 동작**

- 마스터-워커 생성
    
    ```jsx
    if(cluster.isMaster) {
    	// Master code
    	cluster.fork();
    }
    else {
    	// Worker code
    }
    ```
    

- 클러스터를 사용하는 대략적인 구조
    
    ```jsx
    if(cluster.isMaster) {
    	cluster.fork();
    	cluster.on('online', function(worker) {
    		// worker 생성 후 실행
    		console.log('worker is online, id: ' + worker.id);
    	});
    	
    	cluster.on('exit', function(worker, code, signal) {
    		// worker 종료 이벤트
    		console.log('worker exit, id: ' + worker.id);
    	});
    }
    else {
    	var worker = cluster.worker;
    	// worker 종료
    	worker.kill();
    }
    ```
    
<br>

### **Cluster 데이터 전달**

```jsx
if(cluster.isMaster) {
	var worker = cluster.fork();
	cluster.on('message', function(message) { // 4:받으면
		console.log('Master received' + message); // 5:console.log
	});
	
	cluster.on('online', function(worker) {
		worker.send({message: 'Hello Worker'}); // 1:워커에 데이터 보내고
	});
}
else {
	var worker = cluster.worker;
	worker.on('message', function(message) { // 2:워커에서 받으면
		process.send({message: 'Fine Thank you'}); // 3:마스터에 보내고
	});
}
```