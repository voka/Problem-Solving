import java.util.*;
//순열 구현
class Solution {
    static int n,answer;
    public int solution(int k, int[][] dungeons) {
        answer = -1;
        n = dungeons.length;
        boolean[] visit = new boolean[n];
        permutation(visit,0,dungeons,k);
        return answer;
    }
    public void permutation(boolean[] visited, int count, int[][] dungeons, int k){
        for(int i=0;i<n;i++){
            if(visited[i] || dungeons[i][0] > k) continue;
            visited[i] = true;
            permutation(visited,count+1,dungeons,k - dungeons[i][1]);
            visited[i] = false;
        }
        answer = Math.max(answer,count);
    }
}