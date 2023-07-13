import java.util.ArrayDeque;

class Solution {
    private static final int HIT = 1;
    private static final int MISS = 5;

    public int solution(int cacheSize, String[] cities) {
        int answer = 0;

        ArrayDeque<String> deque = new ArrayDeque<>();

        for (int i = 0; i < cities.length; i++) {
            cities[i] = cities[i].toUpperCase();

            if (deque.contains(cities[i])) {
                answer += HIT;
                deque.remove(cities[i]);
                deque.addFirst(cities[i]);
            }

            else {
                deque.addFirst(cities[i]);
                answer += MISS;
                if(deque.size() > cacheSize)
                    deque.pollLast();
            }
        }
        return answer;
    }
}