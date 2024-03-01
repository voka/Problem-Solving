class Pair{
    int x;
    int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}
class Solution {
    static int[][] child = {{-1,-1,-1,-1,-1},{-1,1,1,1,1},{-1,1,2,3,4},{-1,1,2,3,4},{-1,4,4,4,4}};
    public String[] solution(int[][] queries) {
        int n = queries.length;
        String[] answer = new String[n];
        for(int i=0;i<n;++i){
            int cur = solve(queries[i][0],queries[i][1]);
            if(cur == 1) answer[i] = "RR";
            else if(cur == 4) answer[i] = "rr";
            else answer[i] = "Rr";
        }
        return answer;
    }
    public int solve(int x, int y){
        //System.out.printf("%d , %d\n", x,y);
        if(x == 1){
            return 2;
        }
        else if(x == 2){
            return child[x][y];
        }else{
            int group = (y-1)/4; // 자기앞에 있는 그룹 수
            int p = solve(x-1,group + 1); // 부모의 형질
            int rest = y - group*4;
            //System.out.printf("%d, %d -> %d , %d\n", x,y,p,rest);
            return child[p][rest];
        }
    }
}


