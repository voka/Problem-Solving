import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    
    static int n;
    static int[] dp;
    static List<List<Integer>> myList;

    static int getMinTime(int id){
        System.out.println(id);
        // leaf node
        // if(myList.get(id).size() == 0) {
        //     return 0;// 자식노드가 없는경우 0초가 걸림
        // }
        // leaf node가 아닐 경우에 child 부터 계산
        for(int child : myList.get(id)){
            dp[child] = 1 + getMinTime(child); // child 노드 개별로 전파하는 시간 업데이트
        }
        // System.out.printf("id : %d\nTime1 :", id);
        // for(int i=0;i<n;++i){
        //     System.out.printf(" %d",dp[i]);
        // }
        // System.out.println();
        // 많이 걸리는 순서대로 정렬
        Collections.sort(myList.get(id), Comparator.comparingInt(p -> -dp[p]));
        for(int i=0;i<myList.get(id).size();++i){
            int cur = myList.get(id).get(i); //child 노드 번호
            dp[cur] = dp[cur] + i; // 깊이 순서대로 탐색 -> 전파 시간 + 나의 차례
            dp[id] = Math.max(dp[id],dp[cur]); // 자식 서브 트리 중 가장 오래 걸린 시간을 자기 자신한테 없데이트
        }
        // System.out.print("Time2 :");
        // for(int i=0;i<n;++i){
        //     System.out.printf(" %d",dp[i]);
        // }
        // System.out.println();
        return dp[id];
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        dp = new int[n];
        myList = new ArrayList<>();
        for(int i=0;i<n;++i){
            myList.add(new ArrayList<>());
        }
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        for(int i=0;i<n;++i){
            int p = Integer.parseInt(st.nextToken());
            if(p != -1) myList.get(p).add(i);
        }
        getMinTime(0);
        System.out.println(dp[0]);
        // System.out.print("Time :");
        // for(int i=0;i<n;++i){
        //     System.out.printf(" %d",dp[i]);
        // }
        // System.out.println();

    }
}
