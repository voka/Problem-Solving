import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P9251 {
    static int[][] dp;
    int[] a,b;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String a = br.readLine();
        String b = br.readLine();
        dp = new int[a.length()+1][b.length()+1];
        for(int i=1;i<=a.length();++i){
            for(int j=1;j<=b.length();++j){
                if(a.charAt(i-1) == b.charAt(j-1)){
                    dp[i][j] = Math.max(dp[i-1][j-1] + 1, Math.max(dp[i-1][j],dp[i][j-1]));
                }else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        System.out.println(dp[a.length()][b.length()]);
        
    }
}
