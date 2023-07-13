import java.util.ArrayDeque;

class Solution {
    public int[][] solution(int[][] rc, String[] operations) {
        int[][] answer = new int [rc.length][rc[0].length];

        ArrayDeque<Integer> left = new ArrayDeque<Integer>();
        ArrayDeque<ArrayDeque<Integer>> mid = new ArrayDeque<ArrayDeque<Integer>>();
        ArrayDeque<Integer> right = new ArrayDeque<Integer>();

        // 입력으로 받은 2차원 배열을 각 deque에 세팅
        for (int i = 0; i < rc.length; i++) {
            left.addLast(rc[i][0]);
            ArrayDeque<Integer> midQueue = new ArrayDeque<Integer>();
            for (int j = 1; j < rc[i].length - 1; j++) {
                midQueue.addLast(rc[i][j]);
            }
            mid.addLast(midQueue);
            right.addLast(rc[i][rc[i].length - 1]);
        }

        for ( String operation : operations) {
            if (operation.equals("ShiftRow")) {
                left.addFirst(left.pollLast());
                right.addFirst(right.pollLast());
                mid.addFirst(mid.pollLast());
            }

            else { // Rotate
                mid.peekFirst().addFirst(left.pollFirst()); // 1
                right.addFirst(mid.peekFirst().pollLast()); // 2
                mid.peekLast().addLast(right.pollLast()); // 3
                left.addLast(mid.peekLast().pollFirst()); // 4
            }
        }

        for (int i = 0; i < answer.length; i++) {
            answer[i][0] = left.pollFirst();
            ArrayDeque<Integer> midQueue = mid.pollFirst();
            for (int j = 1; j < answer[i].length - 1; j++) {
                answer[i][j] = midQueue.pollFirst();
            }
            answer[i][answer[i].length - 1] = right.pollFirst();
        }
        return answer;
    }


}

