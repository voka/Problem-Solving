import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
public class P2229 {
    static int N;
    static int[] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dp = new int[N+1];
        int[] score = new int[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;++i)
            score[i] = Integer.parseInt(st.nextToken());
        for(int i=1;i<=N;++i){
            int max = score[i], min = score[i];
            for(int j=i;j>0;--j){
                max = Math.max(max,score[j]);
                min = Math.min(min,score[j]);
                dp[i] = Math.max(dp[i], max - min + dp[j-1]);
            }
        }
        System.out.println(dp[N]);

        
    }
}
