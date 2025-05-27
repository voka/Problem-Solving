import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Point implements Comparable<Point>{
    int x;
    int y;
    int cost;
    public Point(int x,int y,int cost){
        this.x = x;
        this.y = y;
        this.cost = cost;
    }
    @Override
    public int compareTo(Point p){
        return Integer.compare(this.cost, p.cost);
    }
}

public class Main {
    static int L, sx, sy , tx, ty;
    static int[][] dp;
    static int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2}, dy= {1,2,2,1,-1,-2,-2,-1};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while(T > 0){
            T--;
            L = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            sx = Integer.parseInt(st.nextToken());
            sy = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            tx = Integer.parseInt(st.nextToken());
            ty = Integer.parseInt(st.nextToken());
            dp = new int[L][L];
            init();
            find();
            sb.append(dp[tx][ty]).append("\n");
        }
        System.out.println(sb.toString());
    }
    static void init(){
        for(int i=0;i<L;++i){
            for(int j=0;j<L;++j){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    static void find(){
        PriorityQueue<Point> pq = new PriorityQueue<>();
        pq.add(new Point(sx, sy, 0));
        dp[sx][sy] = 0;
        while(!pq.isEmpty()){
            Point p = pq.poll();
            //System.out.printf("%d, %d \n", p.x, p.y);
            if(dp[p.x][p.y] < p.cost) continue;
            for(int i=0;i<8;++i){
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if(!check(nx, ny)) continue;
                //System.out.printf("cost : %d, nCost : %d\n", p.cost, p.cost + 1);
                int nCost = p.cost + 1;
                if(dp[nx][ny] > nCost){
                    dp[nx][ny] = nCost;
                    pq.add(new Point(nx, ny, nCost)); 
                }
            }
        }
    }
    
    static boolean check(int x, int y){
        return (x >=0) && (y>=0) && (x < L) && (y < L);
    }
}
