import java.io.*;
import java.util.*;
class Point implements Comparable<Point>{
    int id;
    int dis;
    int x;
    int y;
    public Point(int dis, int x, int y, int id){
        this.dis = dis;
        this.x = x;
        this.y = y;
        this.id = id;
    }
    @Override
    public int compareTo(Point o){
        if(this.dis > o.dis) return 1;
        else if(this.dis < o.dis) return -1;
        if(this.x > o.x) return 1;
        else if(this.x < o.x) return -1;
        if(this.y > o.y) return 1;
        else if(this.y < o.y) return -1;
        return 0;
    }
}
public class Main {
    static int n,m,carX,carY,count = 0;
    static long battery;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static int[] sx, sy, ex, ey;
    static boolean[] complete; 
    static boolean[][] wall; // true : 지나갈 수 없음, false : 지나갈 수 있음
    static boolean[][] visited;
    static PriorityQueue<Point> pq = new PriorityQueue<>();
    static boolean allStop = false;
    public static void main(String[] args) throws IOException {
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        battery = Long.parseLong(st.nextToken());
        wall = new boolean[n][n];
        sx = new int[m];
        sy = new int[m];
        ex = new int[m];
        ey = new int[m];
        complete = new boolean[m];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                wall[i][j] = Integer.parseInt(st.nextToken()) == 1 ? true : false;
            }
        }
        st = new StringTokenizer(br.readLine());
        carX = Integer.parseInt(st.nextToken())-1;
        carY = Integer.parseInt(st.nextToken())-1;
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            sx[i] = Integer.parseInt(st.nextToken())-1;
            sy[i] = Integer.parseInt(st.nextToken())-1;
            ex[i] = Integer.parseInt(st.nextToken())-1;
            ey[i] = Integer.parseInt(st.nextToken())-1;
        }

        while(m>count){
            findAllSP(); // 모든 최단거리 검색
            if(allStop) break;
            goToDes(); // 승객 옮기기
            if(allStop) break;
            //printCar();
        }
        long ans = (m>count) ? -1 : battery;
        System.out.println(ans);
    }
    static void printCar(){
        System.out.printf("(%d, %d), %d\n", carX, carY, battery);
    }
    static int[][] bfs(){
        int[][] dp = new int[n][n];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        Queue<Point> que = new ArrayDeque<>();
        dp[carX][carY] = 0;
        que.add(new Point(0, carX, carY, -1));
        while(!que.isEmpty()){
            Point cur = que.poll();
            if(dp[cur.x][cur.y] < cur.dis) continue;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                int nDis = cur.dis+1;
                if(checkRange(nx,ny) && !wall[nx][ny] && dp[nx][ny] > nDis){
                    dp[nx][ny] = nDis;
                    que.add(new Point(nDis, nx, ny, -1));
                }
            }
        }
        return dp;
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<n) && (y<n);
    }
    static void findAllSP(){
        pq.clear();
        int[][] bfsMap = bfs();
        for(int i=0;i<m;++i){
            if(complete[i]) continue; // 이동이 끝난 손님은 넘어가기
            // 손님의 현재 위치
            int cx = sx[i];
            int cy = sy[i];
            if(bfsMap[cx][cy] == Integer.MAX_VALUE){
                allStop = true;
                return;
            }
            pq.add(new Point(bfsMap[cx][cy], cx, cy, i));
            //System.out.printf("%d, %d, %d, %d\n", bfsMap[cx][cy], cx, cy, i);
        }
    }
    static void goToDes(){
        if(!pq.isEmpty()){
            Point c = pq.poll();
            // 승객 태우기
            carX = c.x;
            carY = c.y;
            int[][] bfsMap = bfs();
            int curEx = ex[c.id];
            int curEy = ey[c.id];
            int dis = bfsMap[curEx][curEy];
            if(dis == Integer.MAX_VALUE){
                allStop = true;
                return;
            }
            int useBattery = c.dis + dis;
            if(useBattery > battery){
                allStop = true;
                return;
            }
            // 이동한 만큼 배터리를 소모시키고, 손님을 태우고 이동한 만큼 * 2 를 해서 더해준다.
            battery = battery - useBattery + dis*2;
            carX = curEx;
            carY = curEy;
            complete[c.id] = true; // c.id 인덱스를 가진 손님 목적지 까지 이송 완료 표시
            count++;
            //System.out.printf("id : %d, (%d, %d) -> (%d, %d) / dis : %d\n", c.id, c.x, c.y , curEx, curEy, c.dis);
        }else{
            allStop = true;
            return;
        }
    }
}