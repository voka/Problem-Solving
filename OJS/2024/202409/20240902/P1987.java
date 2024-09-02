import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P1987 {
    static int[][] board, dp;
    static int R,C,answer = 1;
    static int[] dx = {1,0,-1,0},dy = {0,-1,0,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        board = new int[R][C];
        for(int i=0;i<R;++i){
            String s = br.readLine();
            for(int j=0;j<C;++j){
                char c = s.charAt(j);
                board[i][j] = c - 'A';
            }
        }
        boolean[][] checkBoard = new boolean[R][C];
        boolean[] checkRoad = new boolean[30];
        dp = new int[R][C];
        checkBoard[0][0] = true;
        dp[0][0] = 1;
        checkRoad[board[0][0]] = true;
        dfs(0,0,checkRoad, checkBoard);
        System.out.println(answer);
        //printArr();
    }
    static boolean check(int x, int y){
        return (x>=0) && (y>=0) && (x<R) && (y<C);
    }
    // static void solve(){
    //     for(int i=0;i<4;++i){ // 시작 방향
    //         boolean[][] checkBoard = new boolean[R][C];
    //         boolean[] checkRoad = new boolean[30];
    //         dp = new int[R][C];
    //         checkBoard[0][0] = true;
    //         dp[0][0] = 1;
    //         checkRoad[board[0][0]] = true;
    //         dfs(0,0,checkRoad, checkBoard);

    //     }
    // }
    static void dfs(int x, int y, boolean[] cR, boolean[][] cB){
        for(int i=0;i<4;++i){
            int nx = dx[i] + x;
            int ny = dy[i] + y;
            if(check(nx,ny) && !cB[nx][ny] && !cR[board[nx][ny]]){// 사용한 적 없는 알파벳일때
                if(dp[nx][ny] <  dp[x][y] + 1){
                    cB[nx][ny] = true;
                    cR[board[nx][ny]] = true;
                    int init = dp[nx][ny];
                    dp[nx][ny] = dp[x][y] + 1;
                    answer = Math.max(dp[nx][ny], answer);
                    dfs(nx,ny,cR,cB);
                    cB[nx][ny] = false;
                    cR[board[nx][ny]] = false;
                    dp[nx][ny] = init;

                }
            } 
        }
    }
    static void printArr(){
        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                System.out.printf("%d ",dp[i][j]);
            }
            System.out.println();
        }

    }
}



