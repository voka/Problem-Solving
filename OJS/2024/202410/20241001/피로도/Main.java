import java.util.*;
//순열 구현
class Solution {
    static int n;
    static List<List<Integer>> permure;
    public int solution(int k, int[][] dungeons) {
        int answer = -1;
        n = dungeons.length;
        permure = new ArrayList<>();
        boolean[] visit = new boolean[n];
        permutation(visit,new ArrayList<>());
        for(List<Integer> re : permure){
            int my = k;
            int num = 0;
            for(int i: re){
                int min = dungeons[i][0];
                int minus = dungeons[i][1];
                if(min <= my){
                    my -= minus;
                    num++;
                }else break;
            }
            answer = Math.max(answer,num);
            System.out.println();
        }
        //print();
        return answer;
    }
    public void print(){
        for(List<Integer> re : permure){
            for(int i: re){
                System.out.printf("%d ", i);
            }
            System.out.println();
        }
    }
    public void permutation(boolean[] visited, List<Integer> re){
        if(re.size() == n){
            List<Integer> tt = new ArrayList<>();
            for(int i : re){
                tt.add(i);
            }
            permure.add(tt);
        }
        for(int i=0;i<n;i++){
            if(visited[i]) continue;
            visited[i] = true;
            re.add(i);
            permutation(visited,re);
            re.remove(re.size()-1);
            visited[i] = false;
        }
    }
}