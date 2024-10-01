import java.util.*;
//순열 구현
class Solution {
    static int n,answer;
    public int solution(int k, int[][] dungeons) {
        answer = -1;
        n = dungeons.length;
        boolean[] visit = new boolean[n];
        permutation(visit,new ArrayList<>(),dungeons,k);
        return answer;
    }
    public void permutation(boolean[] visited, List<Integer> re, int[][] dungeons, int k){
        if(re.size() == n){
            int my = k;
            int count = 0;
            for(int i: re){
                int min = dungeons[i][0];
                int minus = dungeons[i][1];
                if(min <= my){
                    my -= minus;
                    count++;
                }else break;
            }
            answer = Math.max(answer,count);
        }
        for(int i=0;i< n;i++){
            if(visited[i]) continue;
            visited[i] = true;
            re.add(i);
            permutation(visited,re,dungeons,k);
            re.remove(re.size()-1);
            visited[i] = false;
        }
    }
}