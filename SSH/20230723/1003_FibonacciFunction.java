package dp;
import java.io.*;
public class _1003_FibonacciFunction {
    static int[] dp0 = new int[41];
    static int[] dp1 = new int[41];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());
        dp0[0] = 1;
        dp0[1] = 0;
        dp1[0] = 0;
        dp1[1] = 1;

        for(int i=2;i<=40;i++){
            dp0[i] = dp0[i-1]+dp0[i-2];
            dp1[i] = dp1[i-1]+dp1[i-2];
        }
        for(int i=0;i<t;i++){
            int n = Integer.parseInt(br.readLine());
            System.out.println(dp0[n]+" "+dp1[n]);
        }
    }
}
