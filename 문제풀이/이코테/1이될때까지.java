import java.io.*;
import java.util.StringTokenizer;

public class ct {
    public static void main(String[] args) throws IOException {
        int cnt = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        while(N != 1) {
            if (N % K != 0) {
                N -= 1;
            }
            else {
                N /= K;
            }
            cnt += 1;
        }
        bw.write(cnt + "");
        bw.flush();
    }
}
