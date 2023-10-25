import java.util.HashSet;

class Solution {
    public int solution(int[] topping) {
        int answer = 0;

        HashSet<Integer> set = new HashSet<>();
        int [] arr = new int [topping.length];
        int [] arr2 = new int [topping.length];

        for (int i = 0; i < topping.length; i++) {
            set.add(topping[i]);
            arr[i] = set.size();
        }
        set.clear();

        for (int i = topping.length - 1; i >= 0; i--) {
            set.add(topping[i]);
            arr2[i] = set.size();
        }

        for (int i = 0; i < topping.length-1; i++) {
            if(arr[i] == arr2[i+1])
                answer++;
        }

        return answer;
    }
}