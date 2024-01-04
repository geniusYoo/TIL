# @PathVariable에서 "#" 처리하기

Springboot로 개발을 하던 어느 날 .. @PathVariable을 열심히 쓰던 중에 API Path에 `#` 을 사용해야 할 일이 생겼다.. 하지만 계속되는 400대 에러에 머리가 혼미해지는데 ..

### 문제점
기본적으로 Spring Security에는 여러가지 Filter들이 걸려 있는데, 그 중 `DisableEncodeUrlFilter` 요 녀석이 방해하고 있는 것 같았다. 

>**DisableEncodeUrlFilter** 
>
>**세션 ID가 URL에 포함되는 것을 막기 위해** HttpServletResponse를 사용해서 URL이 인코딩 되는 것을 막기 위한 필터


세션 ID가 `#` 으로 표기되므로, 이 녀석이 인코딩되는 것을 막기 위한 필터

<br>

### 해결 방법
```java
 @GetMapping("/retrieve/{pattern}")
public ResponseEntity<?> retrieve(@PathVariable("pattern") String pattern) throws UnsupportedEncodingException {
    String decodedPattern = URLDecoder.decode(pattern, StandardCharsets.UTF_8);
```
`#` 은 `%23` 을 decode 하면 나오므로, 클라이언트에서 인코딩해서 보내주면 서버에서 디코딩하는 방식을 사용했다.

클라이언트에서 보낼때, `#1` 이렇게 보내지 말고, `%231` 이렇게 보내면 된다 !

<br>

<i>오늘도 맨땅을 파며 몇 시간을 허비했지만 ,,, 또 다른 접근법을 알게 되어,, 행복하다 ,,어 ,,^^</i>