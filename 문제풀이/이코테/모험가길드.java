import java.io.*;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ct {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 모험가의 수

        String level = br.readLine(); // 각 모험가의 공포도 값
        String [] arr = level.split(" ");
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        int cnt = 0;

        // 2 3 1 2 2 -> 3 2 2 2 1
        for (int i = 0; i < arr.length; i++) {
            pq.add(Integer.parseInt(arr[i]));
        }

        while (!pq.isEmpty() && pq.peek() <= pq.size()) {
            int num = pq.peek();
            for (int i = 0; i < num; i++) {
                pq.poll();
            }
            cnt += 1;
        }
        bw.write("" + cnt);
        bw.flush();
    }
}
