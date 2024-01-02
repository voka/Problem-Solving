import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Point implements Comparable<Point>{
    int idx;
    long cost;
    public Point(int idx, long cost){
        this.idx = idx;
        this.cost = cost;
    }
    @Override
    public int compareTo(Point o) {
        return Long.compare(this.cost, o.cost);
    }
}

public class Main {
    static int N,M,A,B;
    static long C, answer = Long.MAX_VALUE;
    static long[] costs;
    static List<List<Point>> path = new ArrayList<>();
    static boolean minPath(long maxCost){
        PriorityQueue<Point> myque = new PriorityQueue<>();
        long[] dp = new long[N];
        for(int i=0;i<N;++i) dp[i] = Long.MAX_VALUE;
        dp[A] = 0;
        myque.add(new Point(A, 0));
        while(!myque.isEmpty()){
            Point cur = myque.poll();
            if(dp[cur.idx] < cur.cost) continue;
            for(Point p: path.get(cur.idx)){
                if(dp[p.idx] <= dp[cur.idx] + p.cost) continue;
                if(p.cost > maxCost) continue; 
                dp[p.idx] = dp[cur.idx] + p.cost;
                myque.add(new Point(p.idx, dp[p.idx]));
            }
        }
        if(dp[B] <= C) return true;
        else return false;
    }
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        N = Integer.parseInt(temp[0]);
        M = Integer.parseInt(temp[1]);
        A = Integer.parseInt(temp[2]) - 1;
        B = Integer.parseInt(temp[3]) - 1;
        C = Long.parseLong(temp[4]);
        costs = new long[M];
        for(int i=0;i<N;++i){
            path.add(new ArrayList<Point>());
        }
        for(int i=0;i<M;++i){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()),b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            path.get(a-1).add(new Point(b-1, c));
            path.get(b-1).add(new Point(a-1, c));
            costs[i] = c;
        }
        Arrays.sort(costs);
        int s = 0, e = M-1;
        while(s <= e){
            int m = (s + e) / 2;
            if(minPath(costs[m])){
                e = m - 1;
                answer = Math.min(answer,costs[m]);
            } 
            else s = m + 1;
        }
        if(answer == Long.MAX_VALUE) System.out.println(-1);
        else System.out.println(answer);
    }
}
