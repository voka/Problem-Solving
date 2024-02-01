import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int T, k;
    static int[] p, n;
    static int[] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        T = Integer.parseInt(br.readLine());
        k = Integer.parseInt(br.readLine());
        p = new int[k];
        n = new int[k];
        dp = new int[T+1]; // dp[i] => i원을 만들 수 있는 경우의 수
        for(int i=0;i<k;++i){
            st = new StringTokenizer(br.readLine());
            p[i] = Integer.parseInt(st.nextToken());
            n[i] = Integer.parseInt(st.nextToken()); 
        }
        dp[0] = 1;
        for(int i=0;i<k;++i){
            for(int money=T;money>=0;--money){
                for(int j=1;j<=n[i];++j){ // 동전 개수 만큰 반복
                    int plusIdx = money - j * p[i];
                    if(plusIdx < 0) continue;
                    dp[money] += dp[plusIdx];
                }
            }
        }
        System.out.println(dp[T]);
    }
}
