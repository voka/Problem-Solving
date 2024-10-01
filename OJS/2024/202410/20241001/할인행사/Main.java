import java.util.*;
class Solution {
    static HashMap<String,Integer> dict = new HashMap<>();
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;
        int idx = 0;
        for(String s : want){
            dict.put(s,idx++);// 인덱스
        }
        for(int i=0;i<discount.length;++i){
            int[] goal = new int[11];
            int last10days = Math.min(discount.length, i + 10);
            for(int d = i; d<last10days;++d){
                if(dict.containsKey(discount[d])){
                    int id = dict.get(discount[d]);
                    goal[id]++;
                }
                if(check(goal,number)) answer++;
            }
        }
        return answer;
    }
    static boolean check(int[] goal,int[] number){
        for(int i=0;i<number.length;++i){
            if(goal[i] < number[i]){
                return false;
            }
        }
        return true;
    }
}