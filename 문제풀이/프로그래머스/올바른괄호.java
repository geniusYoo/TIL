import java.io.*;
import java.util.ArrayDeque;

class Solution {
    // 실패하는 조건
    // 1. 맨 마지막까지 스택에 값이 남아있을 때 (짝이 안맞는다는 의미)
    // 2. 닫히는 괄호가 들어왔을 때, 스택에 여는 괄호가 없을 때
    boolean solution(String s) throws IOException {
        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.addLast(s.charAt(i));
            }
            else { // ')'
                if (stack.contains('(')) {
                    stack.remove('(');
                }
                else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}