# String "+" 연산자보다는 StringBuilder를 쓰자 !

### String
String은 `immutable` 

String 객체에 리터럴을 할당하게 되면, 새로운 String 객체를 만들어 할당하는 것과 같은 동작을 함.

따라서, String에 `+` 연산을 한다는 것은 계속해서 새로운 String 
객체를 만든다는 것이므로 오버헤드가 생기기 쉬움.

<br>

### StringBuilder
먼저 StringBuilder는 AbstractStringBuilder라는 클래스를 상속받아 만들어졌는데, 이 안을 살펴보면 필드변수가 모두 `mutable` 

그리고 StringBuilder에서 `append(String str)` 할 때의 로직을 보면 필드변수인 value에 `str.getChars()`라는 메서드를 이용해서 문자열의 값을 수정.

`+` 연산으로 String 객체를 생성하는 비용보다, StringBuilder의 append() 메소드 연산 비용이 더 싸기 때문에, 그만큼 String을 이어주는 연산 비용이 절약됨.

<br>

### 결론
String에다가 다른 값을 연결할 때는, `+` 연산자 보다는 StringBuilder의 `append()` 메소드를 쓰자 !

<br>

<i>코테 공부를 하면서, 이 차이가 매우 크다는 것을 몸소 느끼면서 적어본다 ,,!</i>
