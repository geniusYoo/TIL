import java.util.Collections;
import java.util.PriorityQueue;

class Solution {
    public int solution(int n, int k, int[] enemy) {
        // n : 병사의 수
        // k : 무적권 스킬 갯수
        // enemy[i] : 적 수

        int answer = 0;

        PriorityQueue <Integer> pq = new PriorityQueue(Collections.reverseOrder());
        for (int e : enemy) {
            pq.add(e);
            // 무적권을 쓸 수 있는 경우, 적군의 수가 병사의 수 보다 많을 때
            if (k >0 && n < e) {
                n+= pq.poll(); // 무적권을 써서 회복
                k--;
            }

            n-= e;
            if (n < 0)
                break;
            answer++;
        }
        return answer;
    }
}