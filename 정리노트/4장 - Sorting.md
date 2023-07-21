# 4장 - Sorting

- 정렬 기초
    - Bubble Sort, Insertion Sort, Selection Sort → `O(N^2)`
        - 예외적으로 Insertion Sort는 데이터가 거의 정렬되어 있다면 `O(N)` 만에 가능하다. → 정렬이 되면 멈추기 때문
    - Stable Sort / Unstable Sort (직전 순서 보장 여부) 인지 알고 있으면 좋음
- 자바는 Primitive 타입이면 (int, long, float, double) → Quick Sort(Dual Pivot Quick Sort)
- Reference 타입이면 (Object) → Tim Sort (Merge Sort + Insertion Sort)로 정렬됨

- 자바 관련 지식

```java
String a = "abc";
String b = "abc";

// '==' 비교는 identityHashCode를 비교해서 같으면 true, 다르면 false를 리턴함.
// 값 비교가 아니어서 같은 객체로 인식함. 그래서 true가 나옴.
if (a == b) { 
	System.out.println("equal");
}

else {
	System.out.println("different");
}

a += "d"; // String은 Immutable 객체이기 때문에, 데이터가 변하면 새로운 객체를 만들어서 복사하기 때문에 시간이 꽤 걸림.
b += "d"; 

// 이렇게 += 로 더하면 새로운 객체를 만들어서 복사하기 때문에 O(N)의 시간이 걸림.
// 그래서 시간을 좀더 빠르게 하기 위해서 StringBuilder를 쓰면 좀 더 빠름!
// StringBuilder sb = new StringBuilder()

if (a == b) {
	System.out.println("equal");
}

else {
	System.out.println("different");
}

// 출력은 equal, different가 나옴!

```

- Mutable vs Immutable
    - Mutable : 데이터 변경 가능
    - Immutable : 데이터 불변

- Counting Sort
    - 정렬해야 할 데이터가 정수이면서, 정수의 범위가 대략 1,000,000을 넘지 않을 때 `O(N)` 만에 데이터를 정렬할 수 있는 알고리즘
    - 특별히 정렬 API를 사용하는 것이 제한적일 때만 대안으로 사용 ,,