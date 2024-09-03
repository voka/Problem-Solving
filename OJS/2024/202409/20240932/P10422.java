import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class P10422 {
    static int T;
    static long[] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        dp = new long[5001];
        T = Integer.parseInt(br.readLine());
        dp[0] = 1;
        for(int i=2;i<=5000;i=i+2){ // 짝수에 대해서만 값 넣어주기
            for(int j=2;j<=i;j=j+2){
                dp[i] += dp[j-2] * dp[i-j]; // dp[6] = dp[0]*dp[6] + dp[2]*dp[4] + dp[6]*dp[0]
                dp[i] = dp[i]%1000000007;
            }
        }
        for(int i=0;i<T;++i){
            int c = Integer.parseInt(br.readLine());
            System.out.println(dp[c]);
        }
        
    }
}
// * 카탈란 수
