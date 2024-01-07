
public class Main {
    public boolean checkRange(int x, int y){
        return (x>=1) && (x<=8) && (y>=1) &&(y<=8); 
    }
    public int minMovesToCaptureTheQueen(int a, int b, int c, int d, int e, int f) {
        int [][] board = new int[9][9];
        int minMove = 2;
        int[] dx = {1,0,-1,0,1,1,-1,-1};
        int[] dy = {0,1,0,-1,-1,1,-1,1};
        for(int i=0;i<4;++i){
            int nx = a + dx[i];
            int ny = b + dy[i];
            for(int j=0;j<8;++j){
                if(!checkRange(nx, ny)) break;
                if((nx == c) && (ny == d)) break; // 비숍이랑 만나면
                board[nx][ny] = 1;
                nx += dx[i];
                ny += dy[i];
            }
        }
        for(int i=4;i<8;++i){
            int nx = c + dx[i];
            int ny = d + dy[i];
            for(int j=0;j<8;++j){
                if(!checkRange(nx,ny)) break;
                if((nx == a) && (ny == b)) break; // 룩이랑 만나면
                board[nx][ny] = 1;
                nx += dx[i];
                ny += dy[i];
            }
        }
        if( board[e][f] == 1) return 1;
        else return minMove;
    }
}
