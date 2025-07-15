import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

class Point implements Comparable<Point>{
    int x; 
    int y;
    int c;
    public Point(int x, int y, int c){
        this.x = x;
        this.y = y;
        this.c = c;

    }
    @Override
    public int compareTo(Point p) {
        return this.c - p.c;
    }
}

public class Main {
    static PriorityQueue<Point> que;
    static int[] dx = {-1,-1,0,1,1,1,0,-1}, dy = {0,1,1,1,0,-1,-1,-1};
    static int N;
    static boolean[][] visit;
    static int[][] arr,cArr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int tc=1;tc<=T;++tc){
            N = Integer.parseInt(br.readLine());
            que = new PriorityQueue<>();
            arr = new int[N][N];
            cArr = new int[N][N];
            visit = new boolean[N][N];
            for(int i=0;i<N;++i){
                String temp = br.readLine();
                for(int j=0;j<N;++j){
                    if(temp.charAt(j) == '.'){
                        arr[i][j] = 0;
                    }else{
                        arr[i][j] = 1;
                        cArr[i][j] = -1;
                    }
                }
            }
            countMap();
            int answer = solve();
            // for(int i=0;i<N;++i){
            //     for(int j=0;j<N;++j){
            //         System.out.printf("%d ", cArr[i][j]);
            //     }
            //     System.out.println();
            // }
            System.out.printf("#%d %d\n", tc, answer);
        }
        
    }
    static void bfs(Point p){
        //System.out.printf("%d, %d : %d\n",p.x , p.y, p.c);
        visit[p.x][p.y] = true;
        Queue<Point> q = new ArrayDeque<>();
        q.add(p);
        while(!q.isEmpty()){
            Point pp = q.poll();
            if(pp.c != 0) continue;
            for(int i=0;i<8;++i){
                int nx = pp.x + dx[i];
                int ny = pp.y + dy[i];
                if(!check(nx, ny)) continue;
                if(visit[nx][ny]) continue;
                if(cArr[nx][ny] == -1) continue;
                q.add(new Point(nx, ny, cArr[nx][ny]));
                visit[nx][ny] = true;
            }
        }
    }
    static int solve(){
        int count = 0;// 클릭 수
        while(!que.isEmpty()){
            Point p = que.poll();
            //System.out.printf("%d, %d : %d\n",p.x , p.y, p.c);
            if(!visit[p.x][p.y]) {
                //System.out.printf("%d, %d : %d\n", p.x, p.y, p.c);
                count++;
                bfs(p);
            }
        }

        return count;
    }
    static void countMap(){
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                if(cArr[i][j] != -1) {
                    cArr[i][j] = count(i,j);
                    //System.out.printf("%d, %d : %d\n", i, j, cArr[i][j]);
                    que.add(new Point(i, j, cArr[i][j]));
                }    
            }
        }
    }
    static int count(int x, int y){
        int c = 0;
        for(int i=0;i<8;++i){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(!check(nx,ny)) continue;
            if(arr[nx][ny] == 1) c++;
        }
        return c;
    }
    static boolean check(int x, int y){
        return (x>=0) && (y>=0) && (x<N) && (y<N); 
    }
}
