import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    
    static int N;
    static int[] dp;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int maxIncrease = 0;
        dp = new int[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;++i){
            int cur = Integer.parseInt(st.nextToken());
            dp[cur] = dp[cur-1] + 1;
            maxIncrease = Math.max(dp[cur], maxIncrease);
        }
        System.out.println(N-maxIncrease);
    }
}
