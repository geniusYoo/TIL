# Intellij에서 DB 복사하기

Intellij로 개발을 하는 도중, Local 환경에서 테스트하는 것이 편해서 더미 데이터를 로컬 MySQL DB에다 세팅해놨는데, AWS에 올라가 있는 DB에 더미 데이터를 그대로 옮길려는데 한번에 옮길 수 있는 방법이 없을까? 당연히 있겠지 🧐


### 방법
1. Intellij에 두개의 DB를 연결한다.

2. 내가 옮기려는 원본 DB Host 아래의 DB를 우클릭하고 Import/Export -> Copy Tables To .. 를 클릭한다.
3. 그러면 Table을 Import하는 창이 나오는데, 상단의 `Target Schema for all sources:` 에 내가 옮길 DB를 선택해서 아래의 Import를 누르면 모든 테이블의 모든 데이터가 복사된다.

<br>
<i>이제 더미데이터를 어디에든지 슉슉 복사할 수 있게 되었다 -!</i>