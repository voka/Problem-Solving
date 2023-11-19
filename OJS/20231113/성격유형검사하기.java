import java.util.*;

class Solution {
    public String solution(String[] survey, int[] choices) {
        String answer = "";
        int N = survey.length;
        String mbti = "RTCFJMAN";
        int[] my = new int[8];
        for(int i = 0;i<N;++i){
            int cur = choices[i] - 4 ; 
            int mbti_index = 0;
            if(cur < 0){
                for(int j=0;j<8;++j){
                    if(mbti.charAt(j) == survey[i].charAt(0)){
                        mbti_index = j;
                    }
                }
                my[mbti_index] += -cur;
            }
            else{
                for(int j=0;j<8;++j){
                    if(mbti.charAt(j) == survey[i].charAt(1)){
                        mbti_index = j;
                    }
                }
                my[mbti_index] += cur;
            }
        }
        
        for(int i = 0; i < 8 ;i = i + 2){
            if(my[i] < my[i+1]){
                answer += String.valueOf(mbti.charAt(i+1));
            }
            else{
                answer += String.valueOf(mbti.charAt(i));
            }
        }
        return answer;
    }
}
