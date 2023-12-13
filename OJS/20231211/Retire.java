import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Retire {
    static int N;
    static int [] T;
    static int [] P; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        T = new int[N+1];
        P = new int[N+1];
        for(int i=0;i<=N-1;++i){
            String[] tp = br.readLine().split(" ");
            T[i] = Integer.parseInt(tp[0]);
            P[i] = Integer.parseInt(tp[1]);
        }
        int []dp = new int[N+1];
        int answer = 0;
        for(int i=0;i<=N;++i){
            answer = Math.max(answer,dp[i]);
            if(i+T[i]<=N){
                dp[i+T[i]] = Math.max(answer + P[i], dp[i+T[i]]);
            }
            // System.out.printf("%d, %d\n",i,answer);
            // for(int j=0;j<=N;++j){
            //     System.out.printf("%d ",dp[j]);
            // }
        }
        System.out.println(dp[N]);
    }   
}
/**
 * N = 1, answer = 0
 * 0   0   0   10   0   0   0   0
 * N = 2, answer = 0
 * 0   0   0   10   0   0   20   0
 * N = 3, answer = 10
 * 0   0   0   10   0   0   20   0
 * N = 4, answer = 30
 * 0   0   0   10   30   0   20   0
 * N = 5, answer = 30
 * 0   0   0   10   30   0   45   0
 * N = 6, answer = 45
 * 0   0   0   10   30   0   45   0
 * N = 7, answer = 45
 * 0   0   0   10   30   0   45   0
 * N = 8, answer = 45
 * 0   0   0   10   30   0   45   45
 * 
 * 
 */