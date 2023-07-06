import java.io.*;
import java.util.StringTokenizer;

// 백준 구간 합 구하기 4
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 수의 개수
        int M = Integer.parseInt(st.nextToken()); // 합을 구해야 하는 횟수

        int arr[] = new int[N+1];

        st = new StringTokenizer(br.readLine()); // 입력 받아서, 공백으로 자름

        arr[0] = 0;
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= N; i++) {
            arr[i] = arr[i] + arr[i-1]; // 누적합 배열 완성
        }

        int result[] = new int[M];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            result[i] = arr[end] - arr[start-1];
            System.out.println(result[i]);
        }
    }
}