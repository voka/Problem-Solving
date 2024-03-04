import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Bomb implements Comparable<Bomb>{
    int cx;
    int cy; 
    int count;
    int rCount;
    public Bomb(int cx, int cy, int count, int rCount){
        this.cx = cx;
        this.cy = cy;
        this.count = count;
        this.rCount = rCount;
    }
    @Override
    public int compareTo(Bomb b){
        //폭탄 개수가 큰 순
        if(this.count < b.count) return 1;
        else if(this.count > b.count) return -1;
        // 빨간 폭탄 개수가 작은 순
        if(this.rCount > b.rCount) return 1;
        else if(this.rCount < b.rCount) return -1;
        // 기준점의 행이 가장 큰 순
        if(this.cx < b.cx) return 1;
        else if(this.cx > b.cx) return -1;
        // 기준점의 열이 가장 작은 순
        if(this.cy > b.cy) return 1;
        else if(this.cy < b.cy) return -1;
        return 0;
    }
    @Override
    public String toString(){
        return String.format("center : (%d, %d), count : %d, redCount : %d", this.cx,this.cy,this.count, this.rCount);
    }

}
public class Main {
    static int n,m,ans = 0;
    static int[][] map; // -1: 돌, -2: 빈칸, 0 : 빨간 폭탄, 1 ~ m 다른 색깔의 폭탄
    static boolean[][] visited;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){ 
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        solve();
        

    } 
    static void solve(){
        while(true){
            //System.out.println("====================");
            init(); //사용할 배열들 초기화
            // 폭탄 무리 찾기, 각 무리당 개수 구하기
            PriorityQueue<Bomb> pq = new PriorityQueue<>();
            for(int i=0;i<n;++i){
                for(int j=0;j<n;++j){
                    if(!visited[i][j] && (map[i][j] >= 1)){
                        visited[i][j] = true;
                        Bomb cur = bfs(i, j);
                        if(cur.count >= 2) pq.add(cur);
                    }
                }
            }
            if(pq.size() == 0) break;
            Bomb bGroup = pq.poll();//제거할 폭탄 묶음
            //System.out.println(bGroup);
            ans += bGroup.count * bGroup.count;
            init();
            killBomb(bGroup.cx, bGroup.cy);
            //printMap();
            //중력 작용
            grabity();
            //printMap();
            //반시계 90도 회전
            rotate();
            //printMap();
            //중력 작용
            grabity();
            //printMap();
        }
        System.out.println(ans);
        
    }
    static void printMap(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void init(){
        visited = new boolean[n][n];
    }
    static boolean checkRange(int x, int y){
        return (x >= 0) && (y>=0) && (x<n) && (y<n);
    }
    static void grabity(){
        for(int j=0;j<n;++j){
            int x = n-1;// 가장 밑에서 시작
            while(true){
                if(map[x][j] == -2) {
                    Point next = findNextBombInX(x, j);
                    if(!next.isEmpty()){
                        map[x][j] = map[next.x][next.y]; // 폭탄 내리고
                        map[next.x][next.y] = -2;// 빈칸으로 만들기
                    }
                }
                x--;
                if(x == 0){
                    break;
                }
            }
            
        }
    }
    static Point findNextBombInX(int x, int y){
        for(int i=x-1;i>=0;--i){
            if(map[i][y] >= 0) return new Point(i, y); 
            if(map[i][y] == -1) break;
        }
        return new Point(-1, -1);
    }
    static void rotate(){
        int[][] temp = new int[n][n];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                temp[n-j-1][i] = map[i][j];
            }
        }
        map = temp;
        /**
         * 
         * 
         (0,0) -> (3,0)
         (1,0) -> (3,1)
         (2,0) -> (3,2)
         (3,0) -> (3,3)
         (3,3) -> (0,3)
         (3,2) -> (1,3)
         (i,j) -> (n-1-j, i)
         */
    }
    static void killBomb(int x, int y){ // 중심점 좌표를 받고 bfs를 통해 -2로 만들기
        int color = map[x][y];
        visited[x][y] = true;
        Queue<Point> que = new LinkedList<>();
        que.add(new Point(x, y));
        while(!que.isEmpty()){
            Point cur = que.poll();
            map[cur.x][cur.y] = -2;// 폭탄제거
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny) && !visited[nx][ny] && ((map[nx][ny] == color) || (map[nx][ny] == 0))){
                    visited[nx][ny] = true;
                    que.add(new Point(nx, ny));
                    
                }
            }
        }

    }
    static Bomb bfs(int x, int y){
        int count = 1, rCount = 0;
        int color = map[x][y];
        int cx = x, cy = y;// 폭탄의 기준점 찾기
        List<Point> red = new ArrayList<>();
        Queue<Point> que = new LinkedList<>();
        que.add(new Point(x, y));
        while(!que.isEmpty()){
            Point cur = que.poll();
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny) && !visited[nx][ny] && ((map[nx][ny] == color) || (map[nx][ny] == 0))){
                    visited[nx][ny] = true;
                    count++;
                    if(map[nx][ny] == 0){ // 빨강 폭탄일 경우
                        rCount++;// 해당 무리의 빨간 폭탄 개수 증가 
                        red.add(new Point(nx, ny));
                    }else if(map[nx][ny] == color){ // 자신의 색인 경우
                        // 기준점 업데이트
                        if(cx < nx){
                            cx = nx;
                            cy = ny;
                        }else if(cx == nx && cy > ny){
                            cy = ny;
                        }
                    }
                    que.add(new Point(nx, ny));
                    
                }
            }
        }
        // 빨간폭탄 방문표시 제거
        for(Point r : red){ 
            visited[r.x][r.y] = false;
        }
        return new Bomb(cx, cy, count, rCount);

    }
}
class Point{
    int x;
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean isEmpty(){
        return (x==-1)&&(y==-1);
    }
}
