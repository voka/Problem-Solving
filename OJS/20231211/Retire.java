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
        }
        System.out.println(answer);
    }   
}