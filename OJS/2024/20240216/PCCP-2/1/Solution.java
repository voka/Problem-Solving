class Solution {
    public int[] solution(String command) {
        int[] answer = {0,0};
        int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
        int dir = 0;
        int x = 0;
        int y = 0; 
        for(int i=0;i<command.length();++i){
            char cur = command.charAt(i);
            if(cur == 'R'){
                dir = dir == 3 ? 0 : dir + 1;
            }else if(cur == 'L'){
                dir = dir == 0 ? 3 : dir - 1;
            }else if(cur == 'G'){
                x += dx[dir];
                y += dy[dir];
            }else{
                x -= dx[dir];
                y -= dy[dir];
            }
        }
        answer[0] = y;
        answer[1] = x;
        return answer;
    }
}