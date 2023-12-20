# Specification

<br>

JpaRepository를 사용하려는 도중 쿼리를 2개 이상 조합해야 할 일이 생겼는데, `findBy` 메소드를 2개 이상 사용해야 했다. 

여기서 혹시 동적으로 쿼리를 엮어서 한번에 조회할 수 없을까? 하는 고민에서 비롯된 것이 JPA의 Specification이 아닐까 싶다 😎

<br>

### **사용법**

`JpaSpecificationExecutor<ScheduleEntity>` 상속

`static Specification<Type> function` 작성

`List<Type> findAll(Specification<Type> spec);` 으로 한 번에 조회

`root` : 조회할 Entity, get() 메소드를 지원하는데 매개변수로는 실제 DB Entity의 Field 이름을 작성해야 하고, 반환되는 Entity의 Type을 Casting할 수 없음

`builder` : 쿼리를 쉽게 작성하게 해주는 도구. equals(), between() 등의 여러 메소드가 있어 실제 연산을 수행해준다

`return (root, query, builder)` : Specification 함수는 Expression을 Return 해야 한다.

<br>

### **예시**

DB에 저장되어 있는 사용자의 일정이 여러개 있다. 
이 일정을 내가 원하는 시간대, 선호 요일에 맞춰서 조건에 걸리는 Entity만 골라내는 쿼리를 작성하고 싶다.

<br>

```java
static Specification<ScheduleEntity> hasPreferredTimeRange(Timestamp startTime, Timestamp endTime) {
    return (root, query, builder) ->
        builder.or(
            builder.between(root.get("dtStart"), startTime, endTime),
            builder.between(root.get("dtEnd"), startTime, endTime)
        );
    }
```
    

<br>

*우린 이제 2개 이상의 쿼리를 FindBy 메소드를 체이닝하는 것이 아니라 findAll로 Specification을 엮어서 처리할 수 있게 되었다 얏호 -!*