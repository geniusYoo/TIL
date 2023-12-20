import javax.xml.stream.events.StartDocument;
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

        while(true) {
            // N이 K로 가장 가깝게 나누어 떨어지는 수가 무엇인지 => target
            // 17 4 면 target 16
            int target = (N / K) * K;
            // cnt에는 이미 나눌 수 있는 수 다 빼버리고 나머지 하나씩 빼야하는 것만 남겨둠
            cnt += N - target;
            // N을 가장 가깝게 나눌 수 있는 수로 만들어줌
            N = target;

            // N이 K보다 작으면 더이상 나눌 수 없으므로 반복문 탈출
            if(N < K) break;

            // N이 K보다 크면 나눌 수 있으니까 cnt++, K로 나눠줌
            cnt += 1;
            N /= K;
        }
        cnt += (N-1);

        bw.write(cnt + "");
        bw.flush();
    }
}
