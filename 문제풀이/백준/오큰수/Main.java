import java.io.*;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        int [] num = new int[N];
        int [] answer = new int[N];

        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        // num => 3 5 2 7

        for (int i = N-1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peekLast() <= num[i]) { // 스택이 비어있지 않고, 스택의 top에 있는 숫자가 num 배열의 숫자보다 작다면, 스택에서 제외
                stack.pollLast();
            }
            if(stack.isEmpty()) { // 스택이 비어있다면, 오큰수가 존재하지 않는다는 의미이므로 -1 하고 자기 자신을 스택에 넣음
                answer[i] = -1;
                stack.addLast(num[i]);
            }
            else { // 스택이 비어있지 않다면, 오큰수가 스택의 top 숫자일테니, answer에 대입하고 자기 자신을 스택에 넣음
                answer[i] = stack.peekLast();
                stack.addLast(num[i]);
            }
        }

        for (int i: answer) {
            bw.write(i + " ");
        }

        bw.flush();
    }
}
