import javax.xml.stream.events.StartDocument;
import java.io.*;
import java.util.StringTokenizer;

public class ct {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        String N = st.nextToken();
        String [] arr = N.split("");

        int result = 0;
        result = Integer.parseInt(arr[0]);
        for (int i = 0; i < arr.length-1; i++) {
            if(result == 0 || result == 1) {
                result += Integer.parseInt(arr[i+1]);
            }
            else {
                result *= Integer.parseInt(arr[i+1]);
            }
        }
        bw.write(result + "");
        bw.flush();
    }
}
