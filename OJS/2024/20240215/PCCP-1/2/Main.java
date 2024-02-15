// 완전탐색
class Solution {
    static int n,m, answer = Integer.MIN_VALUE;
    public int solution(int[][] ability) {
        n = ability.length;
        m = ability[0].length;
        boolean[] visit = new boolean[n];
        dfs(0,visit,0,ability);
        return answer;
    }
    public void dfs(int aIndex, boolean[] check, int score, int[][] ability){
        if(aIndex == m){
            answer = Math.max(answer,score);
            return;
        }
        for(int i=0;i<n;++i){
            if(!check[i]){
                check[i] = true;
                dfs(aIndex+1, check, score+ability[i][aIndex], ability);
                check[i] = false;
            }
        }
    }
}