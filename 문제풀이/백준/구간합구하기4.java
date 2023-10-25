import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 동굴의 넓이, <= 200,000
        int H = Integer.parseInt(st.nextToken()); // 동굴의 높이, <= 500,000

        int[] road = new int[H + 2]; // 피해야 하는 장애물 개수의 배열

        for (int i = 1; i <= N; i++) {
            int a = Integer.parseInt(br.readLine()); // 입력으로 종유석 또는 석순의 크기가 들어옴
            if (i % 2 == 0) { // i가 짝수일때 = 종유석, 위에서 내려옴
                road[1]++;
                road[a + 1]--;
            }
            else { // i가 홀수일때 = 석순, 밑에서 올라옴
                road[H - a + 1]++;
                road[H + 1]--;
            }
        }

        for (int i = 2; i <= H; i++) { // Prefix Sum
            road[i] += road[i - 1];
        }

        int ans1 = Integer.MAX_VALUE; // 피해야 하는 장애물 개수의 최소값
        int ans2 = 0; // 구간의 개수

        for (int i = 1; i <= H; i++) {
            if (ans1 > road[i])  // road 배열의 최소값 찾기
                ans1 = road[i];
        }

        for (int i = 1; i <= H; i++) {
            if (ans1 == road[i])
                ans2++;
        }

        bw.write(ans1 + " " + ans2 + "\n");
        bw.flush();

    }

}