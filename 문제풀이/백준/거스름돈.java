import java.io.*;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ct {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = 1000;
        int [] changes = {500, 100, 50, 10, 5, 1}; // 잔돈 배열
        int money = Integer.parseInt(st.nextToken()); // 지불할 돈
        int change = N - money; // 잔돈
        int result = 0;
        int i=0;

        while (change != 0 && i < 6) {
            if(change / changes[i] >= 1) {
                int num = (change / changes[i]);
                change -= (changes[i] * num);
                result += num;
                i++;
            }
            else i++;
        }

        bw.write("" + result);
        bw.flush();
    }
}
