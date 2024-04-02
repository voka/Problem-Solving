import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
class Pair{
    int next;
    int w;
    public Pair(int next, int w){
        this.next = next;
        this.w = w;
    }
}
public class Main {
    static int n, _MAX = 100001;
    static int[][] dp;
    //static List<List<Pair>> graph = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        dp = new int[n][n];
        StringTokenizer st;
        // for(int i=0;i<=n;++i){
        //     graph.add(new ArrayList<>());
        // }
        for(int i=0;i<n;++i){
            Arrays.fill(dp[i], _MAX);
        }
        for(int i=0;i<n-1;++i){
            dp[i][i] = 0;
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            //graph.get(a).add(new Pair(b, c));
            //graph.get(b).add(new Pair(a, c));
            dp[a-1][b-1] = c;
            dp[b-1][a-1] = c;
        }
        dp[n-1][n-1] = 0;
        int ans = Integer.MIN_VALUE;
        for(int k=0;k<n;++k){
            for(int i=0;i<n;++i){
                for(int j=0;j<n;++j){
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                    if(dp[i][j] != _MAX) ans = Math.max(ans, dp[i][j]);
                }
            }
        }
        System.out.println(ans);

    }
}