import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P12865 {
    static int[] w,v;
    static int[][] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        w = new int[n+1];
        v = new int[n+1];
        dp = new int[n+1][k+1];
        for(int i=1;i<=n;++i){
            st = new StringTokenizer(br.readLine());
            w[i] = Integer.parseInt(st.nextToken());
            v[i] = Integer.parseInt(st.nextToken());
            
        }
        for(int i=1;i<=n;++i){
            for(int j=1;j<=k;++j){
                // 물건을 담을 무게가 된다면 이전 값과 이번 물건의 무게를 뺀 배낭의 값에 이번 물건의 가치를 더해 비교하고 가치가 더 크다면 담는다.
                if(j >= w[i]) dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-w[i]] + v[i]);
                else dp[i][j] = dp[i-1][j];  // 물건을 담을 수 없다면 이전값과 동일하게 만든다. 

            }
        }
        System.out.println(dp[n][k]);
    }
}
