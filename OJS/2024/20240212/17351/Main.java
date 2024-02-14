import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int N;
    static int[] dx = {0,1}, dy = {1,0};
    static int[][] board;
    static int[][][] dp; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dp = new int[N][N][4];
        board = new int[N][N];
        for(int i=0;i<N;++i){
            String temp = br.readLine();
            for(int j=0;j<N;++j){
                switch (temp.charAt(j)) {
                    case 'M':
                        board[i][j] = 1;
                        break;
                    case 'O':
                        board[i][j] = 2;
                        break;
                    case 'L':
                        board[i][j] = 3;
                        break;
                    case 'A':
                        board[i][j] = 4;
                        break;
                    default:
                        board[i][j] = -1;
                        break;
                }
                Arrays.fill(dp[i][j], -1);
            }
        }
        int ans = 0;
        if(board[0][0] == 1){
            ans = dfs(0,0,1);
        }else{
            ans = dfs(0,0,0);
        }
        System.out.println(ans);
    }    
    static boolean checkRange(int x, int y){
        return (x < N) && (y < N);
    }
    static int dfs(int x, int y, int cnt){
        //System.out.printf("%d, %d, %d\n", x, y, cnt);
        if(x == N-1 && y == N-1) return 0;
        if(dp[x][y][cnt] != -1) return dp[x][y][cnt]; 
        dp[x][y][cnt] = 0;
        for(int i=0;i<2;++i){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(!checkRange(nx, ny)) continue;
            if(board[nx][ny] == 1){//M인 경우
                dp[x][y][cnt] = Math.max(dp[x][y][cnt], dfs(nx, ny, 1)); 
            }
            else if(cnt + 1 == board[nx][ny]){ 
                if(board[nx][ny] == 4){ // A 인 경우 M을 다시 찾아야 함.
                    dp[x][y][cnt] = Math.max(dp[x][y][cnt], dfs(nx, ny, 0) + 1);
                }
                else{// O, L 인경우
                    dp[x][y][cnt] = Math.max(dp[x][y][cnt], dfs(nx, ny, cnt + 1));
                }
            }
            else{// -1인 경우 다시 M을 찾아야 함.
                dp[x][y][cnt] = Math.max(dp[x][y][cnt], dfs(nx, ny, 0));
            }
        }
        return dp[x][y][cnt];
    }
}
