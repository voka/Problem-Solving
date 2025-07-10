import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] arr = new int[N+1][M];
        int[][][] dp = new int[N+1][M][3];
        for(int i=1;i<=N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;++j){
                arr[i][j] = Integer.parseInt(st.nextToken());
                for(int k=0;k<3;++k) dp[i][j][k] = Integer.MAX_VALUE;
            }
        }
        for(int i=1;i<N+1;++i){
            for(int j=0;j<M;++j){
                if(j==0){ // 0 : 오른쪽 위에서 , 1: 위에서, 2: 왼쪽 위에서
                    dp[i][j][0] = Math.min(dp[i][j][0], Math.min(dp[i-1][j+1][1], dp[i-1][j+1][2]) + arr[i][j]);
                    dp[i][j][1] = Math.min(dp[i][j][1], Math.min(dp[i-1][j][0],dp[i-1][j][2]) + arr[i][j]);
                }
                else if(j==M-1){
                    dp[i][j][1] = Math.min(dp[i][j][1], Math.min(dp[i-1][j][0], dp[i-1][j][2]) + arr[i][j]);
                    dp[i][j][2] = Math.min(dp[i][j][2], Math.min(dp[i-1][j-1][0], dp[i-1][j-1][1]) + arr[i][j]);
                }else{
                    dp[i][j][0] = Math.min(dp[i][j][0], Math.min(dp[i-1][j+1][1], dp[i-1][j+1][2]) + arr[i][j]);
                    dp[i][j][1] = Math.min(dp[i][j][1], Math.min(dp[i-1][j][0], dp[i-1][j][2] ) + arr[i][j]);
                    dp[i][j][2] = Math.min(dp[i][j][2], Math.min(dp[i-1][j-1][0], dp[i-1][j-1][1]) + arr[i][j]);
                }
            }
        }
        int answer = Integer.MAX_VALUE;
        for(int i=0;i<M;++i){
            answer = Math.min(answer, Math.min(dp[N][i][0],Math.min(dp[N][i][1],dp[N][i][2])));
        }
        System.out.println(answer);

    }
}
