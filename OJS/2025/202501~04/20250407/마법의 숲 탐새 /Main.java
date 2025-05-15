import java.io.*;
import java.util.*;

class Point{
    int x;
    int y;
    public Point(int x,int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int R,C,K; 
    static int[] ax,ay,ad,kx;
    // 0 : 북 , 1: 동, 2 : 남 , 3 : 서 
    /// -> 0 : 북, 1 : 서, 2 : 동, 3 : 남 으로 바꾸기
    static int[] dx = {-1,0,1,0}, dy = {0,-1,0,1};
    static int[][] forest;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        ax = new int[K+1];
        ay = new int[K+1];
        ad = new int[K+1];
        kx = new int[K+1];
        forest = new int[R+3][C];
        int ans = 0;
        for(int i=1;i<=K;++i){
            st = new StringTokenizer(br.readLine());
            ay[i] = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            if(d == 1){ // 오른쪽
                ad[i] = 3;
            }else if(d == 2){ // 밑쪽
                ad[i] = 2;
            }else if(d == 3){ // 왼쪽
                ad[i] = 1;
            }else{
                ad[i] = 0;
            }
            move(i); // 정령이동
            if(ax[i] < 4) {
                forest = new int[R+3][C]; // 이동을 완료했을때 그 위치가 숲을 벗어나 있다면 탐색 종료
                continue;
            }else{
                intoMap(i);
            }
            kx[i] = bfs(i);
            ans += (kx[i] - 2);
            //printForest();
        }
        System.out.println(ans);
    }
    static void printForest(){
        System.out.println();
        for(int i=0;i<R+3;++i){
            for(int j=0;j<C;++j){
                System.out.printf("%d ",forest[i][j]);
            }
            System.out.println();
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<R+3) && (y<C);
    }
    static boolean checkAnother(int x, int y){
        return forest[x][y] != 0;
    }
    static boolean canGo(int x, int y){
        return checkRange(x, y) && !checkAnother(x, y);
    }
    static boolean checkDown(int x, int y){
        if(canGo(x+2,y) && canGo(x+1, y-1) && canGo(x+1, y+1)) return true;
        return false;
    }
    static boolean checkLeftDown(int x, int y){
        if(!checkDown(x, y-1)) return false;
        if(canGo(x, y-2) && canGo(x-1, y-1) && canGo(x+1, y-1)) return true;
        return false;
    }
    static boolean checkRightDown(int x, int y){
        if(!checkDown(x, y+1)) return false;
        if(canGo(x, y+2) && canGo(x-1, y+1) && canGo(x+1, y+1)) return true;
        return false;
    }
    static void move(int id){
        int x = 1;
        int y = ay[id];
        while(true){
            boolean canMove = false;
            if(checkDown(x, y)){
                x++;
                canMove = true;
                //System.out.println("down");
            }else if(checkLeftDown(x,y)){
                x++;
                y--;
                ad[id] = (ad[id]+1)%4;
                canMove = true;
                //System.out.println("leftdown");
            }else if(checkRightDown(x,y)){
                x++;
                y++;
                ad[id] = (ad[id]+3)%4;
                canMove = true;
                //System.out.println("rightdown");
            }
            //System.out.printf("d : %d\n", ad[id]);
            if(!canMove) break; // 3가지 방향으로의 움직임 모두 실패했다면 
        }
        ax[id] = x;
        ay[id] = y;
    }
    static void intoMap(int id){
        int x = ax[id];
        int y = ay[id];
        forest[x][y] = id;
        for(int i=0;i<4;++i){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(i == ad[id]) forest[nx][ny] = -id;
            else forest[nx][ny] = id;
        }
    }
    static int bfs(int id){
        //System.out.printf("\nid : %d\n",id);
        int maxX = ax[id];
        int sx = ax[id];
        int sy = ay[id];
        boolean[][] visited = new boolean[R+3][C];
        visited[sx][sy] = true;
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(sx,sy));
        while(!q.isEmpty()){
            Point p = q.poll();
            for(int i=0;i<4;++i){
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if(!checkRange(nx, ny)) continue;
                if(visited[nx][ny]) continue;
                if(forest[nx][ny] == 0) continue;
                if(forest[p.x][p.y]>0 && (Math.abs(forest[p.x][p.y]) != Math.abs(forest[nx][ny]))) continue; // 출구가 아닌데 나가려는 경우
                maxX = Math.max(maxX,nx);
                visited[nx][ny] = true;
                q.add(new Point(nx, ny));
                //System.out.printf("%d,%d\n", nx,ny);
            }
        }
        return maxX;
    }
    
}
