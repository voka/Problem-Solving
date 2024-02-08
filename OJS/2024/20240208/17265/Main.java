import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, minAns = Integer.MAX_VALUE, maxAns = Integer.MIN_VALUE;
    static int[][] board;
    static int[] dx = {1,1,0,0}, dy = {0,0,1,1};
    static int[] ddx = {1,0}, ddy = {0,1};

    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x <N) && (y <N);
     }
    static int calNum(int score, int fNum, int sNum ){
        if(fNum == -1){
            return score + sNum;
        }
        else if(fNum == -2){
            return score - sNum;
        }else{
            return score * sNum;
        }
        
    }
    static  void dfs(int x,int y, int score){
        if(x == N-1 && y == N-1){
            minAns = Math.min(score, minAns);
            maxAns = Math.max(score, maxAns);
            return;
        }
        for(int i=0;i<4;++i){
            int fx = x + dx[i];
            int fy = y + dy[i];
            if(checkRange(fx, fy)){
                for(int j=0;j<2;++j){
                    int sx = fx + ddx[j];
                    int sy = fy + ddy[j];
                    if(checkRange(sx, sy)){
                        int nextScore = calNum(score,board[fx][fy],board[sx][sy]);
                        //System.out.printf("%d, %d -> %d, %d -> %d, %d ==> %d\n", x, y, fx, fy, sx, sy, nextScore);
                        dfs(sx,sy,nextScore);
                    }
                }
            }
        }

    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                char cur = st.nextToken().charAt(0);
                switch (cur) {
                    case '+':
                        board[i][j] = -1;
                        break;
                    case '-':
                        board[i][j] = -2;
                        break;
                
                    case '*':
                        board[i][j] = -3;
                        break;
                    default:
                        board[i][j] = cur - '0';
                        break;
                }
            }
        }


        // for(int i=0;i<N;++i){
        //     for(int j=0;j<N;++j){
        //         System.out.printf("%d ",board[i][j]);
        //     }
        //     System.out.println();
        // }
        dfs(0, 0, board[0][0]);
        System.out.printf("%d %d\n", maxAns, minAns);

    }
}
