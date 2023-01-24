# January 24, 2023

### **콜백과 콜백 헬**

- 연속된 비동기 동작 코드를 위해선 콜백 안에 콜백을 넣어야 함

    - 예) 이미지 업로드 후 DB에 저장, 다수의 이미지에서 썸네일 생성 후 업로드

    - 콜백의 연속으로 콜백 지옥으로 ,,

<br>

### **Async**

- 대표적인 기능

    - 행위 순서 제어 : `series`, `seriesEach`, `parallels`, `waterfall`

    - 컬렉션 : `each`, `forEachOf`, `map`, `filter`

<br>

### **Async_ series**

순차적으로 실행

- callback 호출 : 다음 태스크로 진행

- 태스크 완료 : 다음 태스크 실행
- 완료 콜백으로 동작 결과 전달

```jsx
async.series([
  // task들을 배열 형태의 콜백으로 나열
	function task1(callback) {
		callback(null, 'result1');
	},
	function task2(callback) {
		callback(null, 'result2');
	},
	function task3(callback) {
		callback(null, 'result3');
	}
	],
	// 완료 콜백으로 동작들의 결과를 전달받음
	function(err, results) {
		// results : ['result1', 'result2', 'result3']
	}
);
```

<br>

### **Async_ waterfall**

순차 실행, 다음 태스크로 정보를 전달하려고 할 때

- 다음 태스크로 전달할 값을 콜백의 파라미터로

- 태스크 함수의 파라미터로 전달 이전의 태스크의 값 전달

```jsx
async.waterfall([
  // task 함수의 파라미터로 전달 이전의 태스크의 값을 전달함 
	// value를 다음 태스크의 arg로 전달
	function task1(callback) {
		callback(null, 'value');
	},
	function task2(arg, callback) {
		callback(null, 'value1', 'value2');
	},
	function task3(arg1, arg2, callback) {
		callback(null, 'result');
	}
	],
	// 완료 콜백으로 동작들의 결과를 전달받음
	function(err, results) {
	}
);
```

<br>

### **Async_ parallel**

여러 태스크를 동시에 실행, 모든 태스크를 마치면 완료 콜백

```jsx
async.parallel(
[
	function task1(callback) {
		callback(null, 'Result of task 1');
	},
	function task2(callback) {
		callback(null, 'Result of task 2');
	},
	function task3(callback) {
		callback(null, 'Result of task 3');
	}
],
	// 완료 콜백으로 동작들의 결과를 전달받음
	function(err, results) {
		console.log('The end of all tasks, results: ', results);
	}
);
```

<br>

### **Async_ 컬렉션**

- 컬렉션 내 각 항목을 사용하는 비동기 동작

    - 다수의 파일(배열)을 비동기 API로 읽기

    - 다수의 파일을 비동기 API로 존재하는지 확인하기

    - 예) 배열에 저장된 10개의 파일이 모두 존재하는지 비동기 API로 확인하는 코드의 경우, 개별 파일을 비동기 API로 접근하여 다루는 비동기 태스크들이 그 예가 되겠다.

- 비동기 순회 동작
    - `each`, `eachSeries`, `eachLimit`
    - `map`, `filter`
    - `reject`, `reduct` …


<br>

### **Async_ each**

컬랙션과 사용하는 `each`

```jsx
async.each(array, function(item, callback) {
	// 비동기 동작 code
	callback(null);
} function(err) {
	// async.each 완료
});
```

each의 파라미터들

- `array` : 컬랙션에 해당하는 배열

- `function(item, callback)` : 컬랙션의 개별 항목과 함께 동작하는 비동기 태스크를 작성
    - `item` : 배열의 원소가 하나씩 전달됨

    - `callback` : 비동기 태스크가 끝나거나 에러 발생 시 호출되는 callback
- `function(err)` : 배열 내 모든 항목을 이용해 비동기 태스크를 마치거나, 중간에 에러가 발생한 경우 동작하는 callback 지정

<br>

### **Promise**

비동기 동작의 흐름 제어, 체이닝

- 객체 생성 `new Promise( function() { .. } );`

- 상태
    - pending : 동작 완료 전, 진행 중

    - fulfilled : 비동기 동작 성공
    - rejected : 동작 실패

```jsx
function task1(fullfill, reject) {
	console.log('Task1 시작');
	setTimeout(function() {
		console.log('Task1 끝');
		//fullfill('Task1 결과');
		reject('Error msg');
	}, 300);
}

function fullfilled(result) {
	console.log('fullfilled : ', result);
}

function rejected(err) {
	console.log('rejected : ', err);
}

new Promise(task1).then(fullfilled, rejected);
```

<br>

### **Promise를 사용하는 태스크**

```jsx
function task() {
	return new Promise(function(fulfill, reject) {
		if(success)
			fulfill('Success');
		else
			reject('Error');
	});
}

// task가 promise를 리턴하므로 그 다음 동작을 지정하는 then으로 결과에 따른 코드 작성
task(arg).then(fullfilled, rejected); 
```