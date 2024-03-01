//구현 
class Solution {
    public String solution(String input_string) {
        String answer = "";
        int[] alpha = new int[27];
        int pre = input_string.charAt(0) - 'a';
        alpha[pre] = 1;
        for(int i=1;i<input_string.length();++i){
            int cur = input_string.charAt(i) - 'a';
            if(pre != cur){
                alpha[cur]++;
            }
            pre = cur;
        }
        for(int i=0;i<27;++i){
            if(alpha[i] >= 2){
                char cur = (char)('a' + i);
                answer += cur;
            }
        }
        if(answer == "") answer = "N";
        return answer;
    }
}