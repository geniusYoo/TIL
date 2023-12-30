# Java 코드 실행 시간 계산하기

<br>

```java
long beforeTime = System.currentTimeMillis(); //코드 실행 전의 시간 

(,,, other codes)

long afterTime = System.currentTimeMillis(); // 코드 실행 후의 시간 
long secDiffTime = (afterTime - beforeTime); // 두 시간의 차 
System.out.println("시간차이(m) : " + secDiffTime);
```

<br>

<i>코테에서 시간복잡도 계산은 매우 중요한데 맨날 까먹어서 기록해둔다 ,,!</i>