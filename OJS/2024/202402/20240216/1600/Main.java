import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
class Triple{
    int x; 
    int y; 
    int k;
    
    public Triple(int x, int y, int k){
        this.x = x;
        this.y = y;
        this.k = k;
    }
}
public class Main {
    static int H,W,K,MY_MAX = 200 * 200 + 1;
    static int[] dx = {0,1,0,-1}, dy = {1,0,-1,0};
    static int[] ddx = {-2,-1,2,1,-2,-1,2,1}, ddy = {1,2,1,2,-1,-2,-1,-2};
    static int[][] board;
    static int[][][] dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        K = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        boolean[][][] visited = new boolean[H][W][K+1];
        board = new int[H][W];
        dp = new int[H][W][K+1];
        for(int i=0;i<H;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<W;++j){
                int cur = Integer.parseInt(st.nextToken());
                board[i][j] = cur;
                Arrays.fill(dp[i][j], MY_MAX);
            }
        }
        
        Queue<Triple> que = new LinkedList<>();
        visited[0][0][K] = true;
        dp[0][0][K] = 0;
        que.add(new Triple(0,0,K));
        while(!que.isEmpty()){
            Triple cur = que.poll();
            int x = cur.x;
            int y = cur.y;
            int k = cur.k; 
            for(int i=0;i<4;++i){
                int nx = x + dx[i];
                int ny = y + dy[i];
                if(checkRange(nx, ny) && board[nx][ny] != 1 && !visited[nx][ny][k]){
                    visited[nx][ny][k] = true;
                    dp[nx][ny][k] = Math.min(dp[nx][ny][k], dp[x][y][k] + 1);
                    que.add(new Triple(nx, ny, cur.k));
                }
            }
            if(k >= 1){//말처럼 이동할 수 있다면
                for(int i=0;i<8;++i){
                    int nx = x + ddx[i];
                    int ny = y + ddy[i];
                    if(checkRange(nx,ny) && board[nx][ny] != 1 && !visited[nx][ny][k-1]){
                        //System.out.printf("%d, %d => %d, %d\n", nx, ny, nx, ny );
                        dp[nx][ny][k-1] = Math.min(dp[nx][ny][k-1], dp[x][y][k] + 1);
                        visited[nx][ny][k-1] = true;
                        que.add(new Triple(nx, ny, k-1));
                    }
                }  
            }
            
        }
        //printArray(dp);
        int answer = MY_MAX;
        for(int i=0;i<=K;++i){
            answer = Math.min(dp[H-1][W-1][i],answer);
        }
        if(answer == MY_MAX) System.out.println(-1);
        else System.out.println(answer);
    }
    static boolean checkRange(int x, int y){
        return (x >= 0) && (y>=0) && (x < H) && (y <W);
    }
    static void printArray(int[][][] map){
        for(int i=0;i<H;++i){
            for(int j=0;j<W;++j){
                System.out.print("(");
                for(int k=0;k<=K;++k){
                    System.out.printf("%d,",map[i][j][k]);
                }
                System.out.print("), ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

