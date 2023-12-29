# Key, Value 쌍을 중첩 정렬하기 (Feat. TreeMap, ArrayList)

TreeMap은 Key를 기준으로 정렬된다. 나는 Key를 기준으로 정렬하되, Value를 더 우선순위를 두고 중첩 정렬을 하고 싶었다.

### 사용법
1. TreeMap에 데이터를 세팅한다. 어차피 자동 정렬된다.
2. `Map.Entry` 를 사용하여 Map을 EntrySet 형태로 캐스팅해 ArrayList에 넣는다.
3. `Collections.sort(Map.Entry.comparingByValue())` 를 사용하여 Map의 Value로 정렬한다.
4. 이렇게 하면 ArrayList에는 TreeMap으로 인해 Key로 정렬된 데이터가 들어간 후, Value로 중첩 정렬이 된다.

### 예시
```java
TreeMap<LocalDate, String> tree = new TreeMap<>();
List<Map.Entry<LocalDate, String>> entries = new ArrayList<>(tree.entrySet());
// Collections.reverseOrder() : 내림차순으로 정렬
entries.sort(Map.Entry.comparingByValue(Collections.reverseOrder())); 
```

<br>
<i>우린 이제 Key로 정렬된 상태에서 Value까지 정렬할 수 있게 되었다 얏호 -!</i>


