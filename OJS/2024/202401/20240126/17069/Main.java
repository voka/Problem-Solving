import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] map;
    static long[][][] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        dp = new long[3][N+1][N+1];
        map = new int[N+1][N+1];
        for(int i=1;i<=N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=N;++j){
                int cur = Integer.parseInt(st.nextToken());
                if(cur == 1){
                    map[i][j] = cur;
                }
            }
        }
        // 가로 방향일때 1,2에 있을 수 있는 경우의 수
        dp[0][1][2] = 1;
        for(int i=1;i<=N;++i){
            for(int j=3;j<=N;++j){
                // 가로 방향 = 대각선에서 오는 개수 + 가로 방향에서 오는 개수
                if(map[i][j] == 0) dp[0][i][j] =  dp[2][i][j-1] + dp[0][i][j-1];
                // 세로 방향 = 대각선에서 오는 개수 + 세로 방향에서 오는 개수
                if(map[i][j] == 0) dp[1][i][j] = dp[2][i-1][j] + dp[1][i-1][j];
                // 대각선 방향 = 대각선에소 오는 개수 + 가로 + 세로
                if(map[i][j] == 0 && map[i-1][j] == 0 && map[i][j-1] == 0) dp[2][i][j] = dp[2][i-1][j-1] + dp[1][i-1][j-1] + dp[0][i-1][j-1]; 
            }
        }
        // System.out.println("DP ==============");
        // for(int i=1;i<=N;++i){
        //     for(int j=1;j<=N;++j){
        //         for(int d=0;d<3;++d){
        //             System.out.printf("%d ",dp[d][i][j]);
        //         }
        //         System.out.print(", ");
        //     }
        //     System.out.println();
        // }
        // System.out.println();
        System.out.println(dp[0][N][N] + dp[1][N][N] + dp[2][N][N]);
    }
}
