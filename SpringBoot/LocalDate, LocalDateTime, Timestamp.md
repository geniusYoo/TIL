# LocalDate, LocalDateTime, Timestamp

캘린더 앱을 토이 프로젝트, 개인 프로젝트, 졸업 프로젝트에 이어 4번째 개발하다 보니 날짜, 시간 데이터 관리의 중요성을 피부로 느끼고 있다. MySQL을 많이 사용했는데, 날짜 데이터를 항상 String 형태의 고정 값으로 사용했었는데, 변환 과정을 계속 거쳐야 해서 매우 불편했었다. 그래서 이번 프로젝트에서는 LocalDate, LocalDateTime, Timestamp를 적절히 사용하여 개발했는데 이들을 소개하고 사용법을 간단하게 적어보겠다.

### LocalDate
`"2023-12-29"` 형태로 날짜만 다룬다.

### LocalDateTime
`"2023-12-29T21:30:00"` 형태로 날짜와 시간까지 다룬다.

### Timestamp
`"2023-12-29 21:30:00"` 형태로 날짜와 시간까지 다룬다. LocalDateTime과 다른 점으로는 MySQL에서 지원하기 때문에, Timestamp 객체 자체를 DB에 넣을 수 있다.

### 여러가지 사용법

```java
// Timestamp -> LocalDate/LocalDateTime
Timestamp.toLocalDate()
Timestamp.toLocalDate().toLocalDateTime()

// LocalDate -> LocalDateTime -> Timestamp
LocalDate.atTime(hour, minute)
Timestamp.valueOf(LocalDateTime)

// LocalDate 요일 알아내기
LocalDate.getDayOfWeek()

// Timestamp 한시간 뒤로 늘리기
final long oneHourInMillis = 60 * 60 * 1000;
timestamp.setTime(timestamp.getTime() + oneHourInMillis)

// Timestamp로 시점을 포함하는지 여부 검사하기
// A는 schedule, B는 Indexing Block (내가 칠할 블록)
// case 1 : IB의 start == schedule의 start
// case 2 : IB의 end == schedule의 end
// case 3 : schedule의 end가 IB의 start와 end 사이에 있는 경우, 위로 걸쳐있는 경우
// case 4 : schedule의 start가 IB의 start와 end 사이에 있는 경우,아래로 걸쳐있는 경우
// case 5 : schedule의 start, end가 IB에 쏙 들어가 있는 경우, start와 end가 겹쳐지지 않고
// case 6 : schedule의 start, end가 IB를 완전히 감싸는 경우
startA.equals(startB)
|| endA.equals(endB)
|| (endA.after(startB)&&endA.before(endB))
|| (startA.after(startB)&&startA.before(endB))
|| (startB.before(startA) && endB.after(endA))
|| (startA.before(startB)&&endA.after(endB));
```

<br>
<i>내가 나중에 보려고 써놓음 ,, Timestamp 이번에 야무지게 썼다 흐흐</i>

