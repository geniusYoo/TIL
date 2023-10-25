import java.util.ArrayList;
import java.util.HashMap;

class Solution {
    public String[] solution(String[] record) {
        HashMap<String, String> name = new HashMap<>();
        HashMap<String, String> msg = new HashMap<>();
        msg.put("Enter", "님이 들어왔습니다.");
        msg.put("Leave", "님이 나갔습니다.");

        for (int i = 0; i < record.length; i++) {

            String [] split = record[i].split(" ");
            if(split.length == 3) {
                name.put(split[1], split[2]); // id-nickname
            }
        }

        ArrayList<String> answer = new ArrayList<>();

        for (int i = 0; i < record.length; i++) {
            String [] split = record[i].split(" ");
            if (msg.containsKey(split[0])) {
                answer.add(name.get(split[1]) + msg.get(split[0]));
            }
        }
        return answer.toArray(new String[0]);
    }
}