import java.util.PriorityQueue;

class Solution {
    private static class Stone {
        int idx, val;

        public Stone(int idx, int val) {
            this.idx = idx;
            this.val = val;
        }
    }

    public int solution(int[] stones, int k) {
        int answer = 0;
        int i = 0;

        PriorityQueue<Stone> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.val, o1.val));
        for (i = 0; i < k; i++) { // 한번에 뛸 수 있는 칸수만큼
            pq.add(new Stone(i, stones[i]));
        }
        answer = pq.peek().val;

        while (i < stones.length) {
            pq.add(new Stone(i, stones[i])); // 4번째 것부터 넣음
            i++;

            while(pq.peek().idx < i-k) {
                pq.poll();
            }

            answer = Math.min(pq.peek().val, answer);

        }

        return answer;
    }
}