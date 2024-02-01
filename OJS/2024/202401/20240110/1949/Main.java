import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[] v;
    static int[][]dp;
    static boolean[] visited;
    static List<List<Integer>> child;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        v = new int[n];
        dp = new int[n][2];
        visited = new boolean[n];
        child = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        for(int i=0;i<n;++i){
            v[i] = Integer.parseInt(st.nextToken());
            child.add(new ArrayList<>());
        }
        
        for(int i=0;i<n-1;++i){
            st = new StringTokenizer(br.readLine()," ");
            int a = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken())-1;
            child.get(a).add(b);
            child.get(b).add(a);
        }
        solve(0);
        // for(int i=0;i<n;++i){
        //     System.out.printf("%d ",Math.max(dp[i][0],dp[i][1]));
        // }
        // System.out.println();
        System.out.println(Math.max(dp[0][0],dp[0][1]));

    }
    static void solve(int x){
        visited[x] = true;
        //1. x 번 마을이 우수마을인 경우
        dp[x][1] = v[x];
        //2. x 번 마을이 우수마을이 아닌 경우
        dp[x][0] = 0; 
        for(int c : child.get(x)){
            if(!visited[c]){ // 처음 방문하는 경우
                solve(c);
                //우수x -> 우수x -> 우수x 보다 우수x -> 우수 -> 우수x 인 경우가 더 크므로 이번 마을이 우수 마을이 아니더라도 최대값을 같기 위해 중간에 한번은 꼭 우수마을이 생긴다.
                dp[x][0] += Math.max(dp[c][0],dp[c][1]); // 이번 마을이 우수 마을이 아닌경우 자식 마을 중 아무거나 큰 인구수를 가진 경우를 입력
                dp[x][1] += dp[c][0]; // 이번 마을이 우수마을 이므로 자식 마을은 우수 마을이 아닌 경우를 더해줘야 함.
            }
        }
    }
}
