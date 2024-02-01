class Solution {
    static int maxNum = 11112, target;
    static int[] dp;
    public void dfs(int x, int cnt){
        if(dp[x] < cnt) return;
        dp[x] = cnt;
        if(x == target) {
            return;
        }
        if(x % 11 == 0){
            dfs(x/11,cnt+1);
        }
        if(x % 5 == 0){
            dfs(x/5,cnt+1);
        }
        if(x > 1){
            dfs(x-1,cnt+1);
        }
        if(x < maxNum){
            dfs(x+1,cnt+1);
        }
    }
    public int minimumOperationsToMakeEqual(int x, int y) {
        target = y;
        dp = new int[maxNum + 1];
        for(int i=0;i<=maxNum;++i){
            dp[i] = maxNum;
        }
        dp[x] = 0;
        dfs(x,0);
        return dp[y];
    }
}