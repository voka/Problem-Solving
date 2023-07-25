package dp;
import java.io.*;
import java.util.StringTokenizer;
public class _12865_NormalBackpack {
    static int n,k;
    static int[][] dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        dp = new int[n+1][k+1];


        for(int i=1;i<=n;i++){
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            for(int j=1;j<=k;j++){
                if(w > j ){
                    dp[i][j] = dp[i-1][j];
                }
                else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-w]+v);
                }
            }
        }
        System.out.println(dp[n][k]);
    }
}
