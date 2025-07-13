// 4883 백준

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] arr,dp;
    static int MAX = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int tc = 1;
        while(true){
            int N = Integer.parseInt(br.readLine());
            if(N == 0) break;
            arr = new int[N][5];
            dp = new int[N][5];
            for(int i=0;i<N;++i){
                for(int j=0;j<5;++j){
                    arr[i][j] = MAX;
                    dp[i][j] = MAX;
                }
            }
            for(int i=0;i<N;++i){
                st = new StringTokenizer(br.readLine());
                for(int j=1;j<=3;++j){
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            solve(N);
            System.out.printf("%d. %d\n", tc++, dp[N-1][2]);
        }
    }    
    static void solve(int N){
        dp[0][2] = arr[0][2];
        dp[0][3] = arr[0][2] + arr[0][3];
        // dp[1][1] = arr[0][2] + arr[1][1];
        // dp[1][2] = Math.min(arr[0][2], arr[0][2] + arr[0][3]) + arr[1][2];
        // dp[1][3] = Math.min(arr[0][2], arr[0][2] + arr[0][3]) + arr[1][3];
        for(int i=1;i<N;++i){
            for(int j=1;j<=3;++j){
                dp[i][j] = Math.min(Math.min(dp[i-1][j-1],dp[i][j-1]),Math.min(dp[i-1][j],dp[i-1][j+1])) + arr[i][j];
            }
        }
        // for(int i=0;i<N;++i){
        //     for(int j=1;j<=3;++j){
        //         System.out.printf("%d ",dp[i][j]);
        //     }
        //     System.out.println();
        // }
    }
}
