import java.io.*;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ct {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        StringBuilder document = new StringBuilder();

        while (st.hasMoreTokens()) {
            document.append(st.nextToken());
            document.append(" ");
        }

        String keyword = br.readLine();
        int cnt = 0;
        int idx = 0;
        while(idx + keyword.length() <= document.length()) {
                if(document.substring(idx, idx+keyword.length()).equals(keyword)) {
                    idx += keyword.length();
                    cnt++;
                }
            else {
                idx++;
            }
        }

        bw.write("" + cnt);
        bw.flush();
    }
}
