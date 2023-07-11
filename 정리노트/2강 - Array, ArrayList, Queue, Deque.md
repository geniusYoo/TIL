# 2강 - Array, ArrayList, Queue, Deque

### Array

- 시간복잡도
    - i번째 index에 access, 끝 지점에 데이터 삽입 → `O(1)`
    - 데이터의 삽입/삭제 (배열의 size가 달라질 때), 탐색 → `O(N)`
        - 새로운 사이즈의 배열을 만들고 데이터를 하나하나 복사해야하기 때문
- O(1)이나 O(logN)이나 시간적으로 크게 차이는 없음! → 2^31이라고 하면 21억이라는 숫자인데 log2^31 = 31이기 때문!

### ArrayList

- 크기가 가변적으로 변하는 경우, 배열은 적합하지 않다 → 사이즈가 바뀌면 오버헤드가 크기 때문
- 데이터가 불변일 때 사용해야하는 자료구조, 데이터를 중간에 삽입하거나 삭제하기에는 좋은 자료구조가 아니다 → 시간이 너무 오래걸림
- 시간복잡도
    - i번째 index에 access, 끝 지점에 데이터 삽입 → `O(1)`
    - (끝지점 제외한 곳에서) 데이터의 삽입, 삭제, 탐색 → `O(N)`
    

### Array vs ArrayList

- ArrayList는 기본적으로 배열로 구성됨
- 기본적으로 Array보다 느린 경향, 사이즈를 가변적으로 조절하기 때문에 사이즈를 조절하는 과정에서 시간이 Array보다 오래걸림
- ArrayList는 사이즈를 2배로 계속 늘려가면서 작업
- 데이터의 개수를 미리 알고있으면 배열을 쓰는게 가장 빠르고, 데이터가 몇개가 될지 모를 때는 ArrayList에 add 하면서 나아가야함!

### Queue

- 시간복잡도
    - (중간이 아니라 맨앞이나 맨끝) 데이터의 삽입, 삭제 → `O(1)`
    - i번째 데이터에 접근, 삽입, 삭제, 탐색 → `O(N)`
- queue.add(), queue.poll() 둘다 O(1)로 엄청 빠름
- FIFO

### Deque (Double Ended Queue)

- 시간복잡도
    - (중간이 아니라 맨앞이나 맨끝) 데이터의 삽입, 삭제 → `O(1)`
    - i번째 데이터에 접근, 삽입, 삭제, 탐색 → `O(N)`
- 양쪽 끝에서 데이터를 삽입, 삭제할 수 있는 자료구조
- Method
    - deque.addFirst(), deque.addLast(), deque.pollFirst(), deque.pollLast()
- deque로 stack, queue를 둘 다 구현 가능
    - addLast()로 데이터를 넣고 pollFirst()로만 꺼내면 queue
    - addLast()로 데이터를 넣고 pollLast()로만 꺼내면 stack
- toUpperCase() 는 O(N)