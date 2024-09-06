import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
class Pair{
    int x;
    int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {

    static int K; // 정령의 수
    static int R, C; // R : 행 , C : 열
    static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};// 상우하좌 탐색                                                                                                                                                                         
    static int[] CI,DI; /// di ->  0, 1, 2, 3 : 상, 우, 하, 좌 순
    static int[] left = {3,0,1,2}; // 상(0) -> 왼(3), 우(1) -> 상(0), 하(2) -> 우(1), 좌(3) -> 하(2) 
    static int[] right = {1,2,3,0}; // 상(0) -> 우(1), 우(1) -> 하(2), 하(2) -> 좌(3), 좌(3) -> 상(0)
    static int[][] map;
    static int[] ax, ay ; // 골렘 들의 출구 좌표
    static int answer = 0;
    static int cMax; 
    static boolean[][] visited;
    /**
    1. 밑으로 이동 (아래쪽이 비어 있을 경우)
    2. 1번이 안될 경우 왼쪽으로 회전 후 이동 ( 왼쪽, 왼쪽 밑이 이동가능한지 살펴보고 가야함)
    3. 2번이 안될 경우 오른쪽으로 회전 후 이동 (오른쪽, 오른쪽 밑이 이동가능한지 살펴보고 가야함)
    
    골렘이 이동할 수 있는 가장 밑에 도착하면 정령은 골렘 내에서 상하좌우 인접한 칸으로 이동가능.
    특히, 현재 골렘의 출구가 다른 골렘과 인접하고 있다면 해당 출구를 통해 다른 골렘으로 이동가능
    또한 골렘 몸의 전체가 격자안에 들어와 있지 않다면 정답에 포함시키지 않는다.
    이전 골렘의 행의 위치까지만 누적하고 다음 골렘부터는 숲을 비운다음 다시 탐색을 시작한다. 
    **/
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new int[R+4][C+1];
        CI = new int[K];
        DI = new int[K];
        ax = new int[K];
        ay = new int[K];
        for(int i=0;i<K;++i){
            st = new StringTokenizer(br.readLine());
            CI[i] = Integer.parseInt(st.nextToken());
            DI[i] = Integer.parseInt(st.nextToken()); 
        }
        solve();
        System.out.println(answer);
    }
    static void solve(){
        for(int i=0;i<K;++i){
            drop(i);// i 번째 골렘 낙하
        }
    }
    
    
    static void drop(int i){
        //System.out.printf("%d 번째 골렘 낙하\n", i+1);
        int sr = 1; // 초기 정령의 위치
        int sc = CI[i]; //초기 행
        while(true){
            //System.out.printf("(%d,%d), 출구 방향 : %d\n", sr,sc,DI[i]);
            if(checkDown(sr, sc)){
                sr++;
            }else if(checkLeftDown(sr,sc)){
                sr++;
                sc--;
                DI[i] = left[DI[i]];
            }else if(checkRightDown(sr,sc)){
                sr++;
                sc++;
                DI[i] = right[DI[i]];
            }else break;
        }
        //System.out.printf("(%d,%d), 출구 방향 : %d\n", sr,sc,DI[i]);
        ax[i] = sr + dx[DI[i]];
        ay[i] = sc + dy[DI[i]];
        go(sr, sc, i+1);
        //printMap();
        if(sr <= 4) {
            initMap();
            return;
        }
        //System.out.printf("%d 번째 골렘의 출구 좌표 (%d,%d)\n\n", i+1,ax[i],ay[i]);
        cMax = 0;
        visited = new boolean[R+4][C+1];
        visited[sr][sc] = true;
        bfs(sr,sc);
        //System.out.printf("%d 번째 정령 최종 도착 위치 %d\n\n", i+1, cMax-3);
        answer += cMax-3;

    }
    static void bfs(int x, int y){
        Queue<Pair> que = new LinkedList<>();
        visited[x][y] = true;
        que.add(new Pair(x, y));
        while(!que.isEmpty()){
            Pair cur = que.poll();
            cMax = Math.max(cur.x,cMax);
            if(cMax == R+3) break;// 최고로 깊은 위치에 도달했을 경우 탐색 종료 
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(!check(nx,ny)) continue;
                if(map[nx][ny] != 0 && !visited[nx][ny]){
                    if(map[cur.x][cur.y] == map[nx][ny]){ // 같은 골렘 내부일 경우
                        visited[nx][ny] = true;
                        que.add(new Pair(nx, ny));
                    }else{ // 다른 골렘과이 사이일 경우
                        int n = map[cur.x][cur.y]-1;
                        if(cur.x == ax[n] && cur.y == ay[n]){//이동 하는 곳이 출구일 경우
                            visited[nx][ny] = true;
                            que.add(new Pair(nx, ny));
                        }
                    }
                }
            }
        }
        
    }
    static void initMap(){
        //System.out.println("초기화!!!");
        map = new int[R+4][C+1];
    }
    static void printMap(){
        for(int i=1;i<=R+2;++i){
            for(int j=1;j<=C;++j){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static boolean check(int x, int y){
        return (x>=1) && (y>=1) && (x<=R+3) && (y<=C);
    }
    static boolean check(int px, int py, int x, int y){
        boolean f = true;
        for(int i=0;i<4;++i){
            int nx = x + px + dx[i];
            int ny = y + py + dy[i];
            if(!check(nx,ny) || map[nx][ny] != 0){
                f = false;
                break;
            }
        }
        return f;
    }
    static boolean checkDown(int x, int y){
        int px = 1, py = 0; 
        return check(px,py,x,y);
    }
    static boolean checkLeft(int x, int y){
        int px = 0, py = -1; 
        return check(px,py,x,y);
    }
    static boolean checkRight(int x, int y){
        int px = 0, py = 1; 
        return check(px,py,x,y);
    }
    static boolean checkLeftDown(int x, int y){
        return checkLeft(x,y) && (checkDown(x,y-1));
    }
    static boolean checkRightDown(int x, int y){
        return checkRight(x,y) && (checkDown(x,y+1));
    }
    static void go(int x, int y, int n){
        for(int i=0;i<4;++i){
            int nx = x + dx[i];
            int ny = y + dy[i];
            map[nx][ny] = n;
        }
        map[x][y] = n;
    }
    
}