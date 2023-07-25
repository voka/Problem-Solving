package dp;
import java.io.*;
import java.util.StringTokenizer;
public class _1149_RGBDistance {
    static int n;
    static int[][] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        dp = new int[n][3]; // 0 : r, 1 : g, 2 : b
        for(int i=0;i<n;i++){
            st = new StringTokenizer(br.readLine());
            int R = Integer.parseInt(st.nextToken());
            int G = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            if(i == 0){
                dp[0][0] = R;
                dp[0][1] = G;
                dp[0][2] = B;
            }
            else {
                dp[i][0] = Math.min(dp[i - 1][1] + R, dp[i - 1][2] + R);
                dp[i][1] = Math.min(dp[i - 1][0] + G, dp[i - 1][2] + G);
                dp[i][2] = Math.min(dp[i - 1][0] + B, dp[i - 1][1] + B);
            }
        }
        System.out.println(Math.min(Math.min(dp[n-1][0],dp[n-1][1]),dp[n-1][2]));
    }
}
