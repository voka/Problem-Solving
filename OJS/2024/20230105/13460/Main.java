import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Point{
    int x; 
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int N,M,answer = 11;
    static Point goal; // 도착지점 좌표
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1}; 
    static boolean equal(Point cur, Point next){
        return (cur.x == next.x) && (cur.y == next.y);
    }
    static boolean goalIsBetweenTwoPoint(Point cur, Point next){
        boolean checkA = ((cur.x <= goal.x) && (goal.x <= next.x))&&((cur.y <= goal.y) && (goal.y <= next.y)); 
        boolean checkB = ((next.x <= goal.x) && (goal.x <= cur.x))&&((next.y <= goal.y) && (goal.y <= cur.y));
        return checkA || checkB;
    }
    static void dfs(int[][] board, Point red, Point blue, int count){
        

        //printArr(board);
        //System.out.println(count);
        //if(count == 7) return;
        if(answer <= count + 1){
            return;
        }
        // 상하좌우 기울이기
        for(int i=0;i<4;++i){
            Point nred = new Point(0, 0);
            Point nblue = new Point(0, 0);

            if(i==0){ // 오른쪽으로 움직일때
                // x좌표가 큰 구슬부터 이동 
                if(red.x > blue.x){
                    nred = getNextPoint(red,i,7,board);
                    nblue = getNextPoint(blue,i,4,board);
                }else{
                    nblue = getNextPoint(blue,i,4,board);
                    nred = getNextPoint(red,i,7,board);
                }
            }
            else if(i==1){ // 아래쪽 이동
                //y좌표가 큰 구슬부터 이동
                if(red.y > blue.y){
                    nred = getNextPoint(red,i,7,board);
                    nblue = getNextPoint(blue,i,4,board);
                }else{
                    nblue = getNextPoint(blue,i,4,board);
                    nred = getNextPoint(red,i,7,board);
                }
            }
            else if(i==2){// 왼쪽으로 이동
                // x좌표가 작은 구슬부터 이동 
                if(red.x < blue.x){
                    nred = getNextPoint(red,i,7,board);
                    nblue = getNextPoint(blue,i,4,board);
                }else{
                    nblue = getNextPoint(blue,i,4,board);
                    nred = getNextPoint(red,i,7,board);
                }
            }
            else if(i==3){// 위쪽으로 이동
                //y좌표가 작은 구슬부터 이동 
                if(red.y < blue.y){
                    nred = getNextPoint(red,i,7,board);
                    nblue = getNextPoint(blue,i,4,board);
                }else{
                    nblue = getNextPoint(blue,i,4,board);
                    nred = getNextPoint(red,i,7,board);
                }
            }
            if(goalIsBetweenTwoPoint(blue, nblue)){ // 파란공이 빠지면 실패
                //board 원 상태로
                board[nred.y][nred.x] = 0;
                board[red.y][red.x] = 7;
                board[nblue.y][nblue.x] = 0;
                board[blue.y][blue.x] = 4;
                continue;
            }
            if(goalIsBetweenTwoPoint(red, nred)){ //빨간공이 도착지에 도착했다면
                //빨간공 지우기
                board[nred.y][nred.x] = 0;
                board[red.y][red.x] = 0;
                Point nnblue = getNextPoint(nblue, i, 4, board);// 파란공 같은 방향으로 다시 굴리기
                if(!goalIsBetweenTwoPoint(nnblue, nblue)) answer = Math.min(count+1,answer);
                
                //board 원 상태로
                board[red.y][red.x] = 7;
                board[nnblue.y][nnblue.x] = 0;
                board[nblue.y][nblue.x] = 0;
                board[blue.y][blue.x] = 4;
                continue;
            }
            // 도착지에 있지 않다면 다시 반복
            // 두 구슬이 하나라도 움직였다면
            if(!equal(red,nred) || !equal(blue,nblue)) dfs(board, nred, nblue, count+1);
            //board 원 상태로
            board[nred.y][nred.x] = 0;
            board[red.y][red.x] = 7;
            board[nblue.y][nblue.x] = 0;
            board[blue.y][blue.x] = 4;

        }
        
    }
    static void printArr(int[][] board){
        System.out.println("Map=========");
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                System.out.printf("%d ",board[i][j]);
            }
            System.out.println();
        }
    }
    static Point getNextPoint(Point cur, int direction, int color, int[][] board){
        int cx = cur.x, cy = cur.y;
        int px = dx[direction], py = dy[direction];
        int nx = cx + px,ny = cy + py;
        while(checkRange(nx, ny) && (board[ny][nx] == 0)){
            nx += px;
            ny += py;
        }
        board[cy][cx] = 0;
        board[ny-py][nx-px] = color;
        return new Point(nx-px, ny-py);
    }
    static boolean checkRange(int x, int y){
        return (1<=x) && (x<M-1) && (1<=y) && (y < N-1);
    }
    
    public static void main(String[] args) throws IOException, NumberFormatException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Point red = new Point(0,0); // 빨간 구슬 좌표 
        Point blue = new Point(0,0); // 파란 구슬 좌표
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int[][] board = new int[N][M];
        for(int i=0;i<N;++i){
            String temp = br.readLine();
            for(int j=0;j<M;++j){
                switch (temp.charAt(j)) {
                    case '#':
                        board[i][j] = -1;
                        break;
                    case 'B':
                        board[i][j] = 4; 
                        blue = new Point(j,i);
                        break;
                    case 'R':
                        board[i][j] = 7;
                        red = new Point(j,i);
                        break;
                    case 'O':
                        goal = new Point(j,i);
                        break;
                    default:
                        break;
                }
            }
        }
        dfs(board,red,blue,0);
        if(answer==11) System.out.println(-1);
        else System.out.println(answer);
    }
    
}

/**
 *
########
#.####.#
#...#B##
#.##.R.#
######.#
##.##O.#
###.##.#
########

4 6
######
#R####
#B..O#
######

4 6
######
#R#O##
#B...#
######
 */