import java.io.*;
import java.util.*;
class Point{
    int x;
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int N;
    static int cx,cy,hx,hy;
    static int[][] dp, w;
    static int minCost;
    static Point[] prr;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int tc = 0;
        while(T>0){
            T--;
            tc++;
            minCost = Integer.MAX_VALUE;
            N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            prr = new Point[N+2];
            dp = new int[N+2][(int) Math.pow(2,N+2)];
            w = new int[N+2][N+2];
            cx = Integer.parseInt(st.nextToken());
            cy = Integer.parseInt(st.nextToken());
            hx = Integer.parseInt(st.nextToken());
            hy = Integer.parseInt(st.nextToken());
            prr[0] = new Point(cx, cy);
            prr[N+1] = new Point(hx, hy);
            for(int i=1;i<=N;++i){
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                prr[i] = new Point(x,y);
            }
            w[N+1][N+1] = 0;
            for(int i=0;i<N+2;++i){
                w[i][i] = 0;
                for(int j=0;j<N+2;++j){
                    if(i == j) continue;
                    int dis = getDistance(prr[i], prr[j]);
                    w[i][j] = dis;
                }
                Arrays.fill(dp[i], -1);
            }
            System.out.printf("#%d %d\n",tc,tsp(0,1));
        }
    }
    static int tsp(int id, int visit){
        if(visit == (1<<(N+1)) - 1){
            return (w[id][N+1] != 0) ? w[id][N+1]:Integer.MAX_VALUE;
        }
        if(dp[id][visit] != -1) return dp[id][visit];
        dp[id][visit] = Integer.MAX_VALUE;
        for(int next = 0; next <= N; ++next){
            if(!check(visit, next) && w[id][next] != 0){
                int cost = tsp(next, add(visit, next)) + w[id][next];
                if(cost < dp[id][visit]){
                    dp[id][visit] = cost;
                }
            }
        }
        return dp[id][visit];
    }
    static boolean check(int visit, int id){
        if((visit & (1 << id)) == (1 << id)) return true;
        return false;
    }
    static int add(int visit, int id){
        return visit | (1 << id);
    }
    static int getDistance(Point a, Point b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

}
