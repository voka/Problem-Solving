package dp;
import java.io.*;
public class _9251_LCS {

    static int[][] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str1 = br.readLine();
        String str2 = br.readLine();
        dp = new int[str1.length()+1][str2.length()+1];

        for(int i=1;i<=str2.length();i++){
            for(int j=1;j<=str1.length();j++){
                if(str1.charAt(j-1) == str2.charAt(i-1)){
                    dp[j][i] = dp[j-1][i-1] +1;
                }
                else
                    dp[j][i] = Math.max(dp[j-1][i],dp[j][i-1]);
            }
        }
        System.out.println(dp[str1.length()][str2.length()]);
    }
}
