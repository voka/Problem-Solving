package dp;
import java.io.*;
import java.util.StringTokenizer;
public class _11051_BinomialCoefficient {
    static int[] dp = new int[1001];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        dp[0] = 1;
        dp[1] = 1;
        for(int i=2;i<=1000;i++){
            dp[i] = (dp[i-1]*i) % 10007;
        }

        int result = (dp[n] * calc_inverse((dp[k]*dp[n-k]) % 10007 , 10005 ))%10007;
        System.out.println(result);
    }
    public static int calc_inverse(int a, int p){
        int result = 1;
        while(p>0){
            if(p % 2 == 1) {
                result *= a;
                result %= 10007;
                p--;
            }
            a *= a;
            a %= 10007;
            p /= 2;
        }
        return result;
    }
}
