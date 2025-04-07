import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Path implements Comparable<Path>{
    int id;
    long cost;
    public Path(int id, long cost){
        this.id = id;
        this.cost = cost;
    }
    @Override
    public int compareTo(Path p){
        return Long.compare(this.cost,p.cost);
    }
}

public class P20183 {
    static int N,M,A,B,C;
    static long costs[];
    static List<List<Path>> paths = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken())-1;
        B = Integer.parseInt(st.nextToken())-1;
        C = Integer.parseInt(st.nextToken());
        costs = new long[M];
        for(int i=0;i<N;++i){
            paths.add(new ArrayList<>());
        }
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken())-1;
            long c = Long.parseLong(st.nextToken());
            paths.get(a).add(new Path(b, c));
            paths.get(b).add(new Path(a, c));
            costs[i] = c;
        }
        Arrays.sort(costs);
        int s = 0, e = M-1;
        long answer = Long.MAX_VALUE;
        while(s<=e){
            int m = (s+e)/2;
            if(isMinPath(costs[m])){
                e = m-1;
                answer = costs[m];
            }else{
                s = m+1;
            }
        }
        if(answer == Long.MAX_VALUE) System.out.println(-1);
        else System.out.println(answer);
    }
    static boolean isMinPath(long MAXcost){
        PriorityQueue<Path> pq = new PriorityQueue<>();
        long[] minCost = new long[N];
        for(int i=0;i<N;++i) minCost[i] = Long.MAX_VALUE;
        minCost[A] = 0;// 출발지는 cost 0;
        pq.add(new Path(A, 0));
        while(!pq.isEmpty()){
            Path cur = pq.poll();
            if(minCost[cur.id] < cur.cost) continue;
            for(Path p : paths.get(cur.id)){
                if(minCost[p.id] <= minCost[cur.id] + p.cost) continue;
                if(p.cost > MAXcost) continue;
                minCost[p.id] = minCost[cur.id] + p.cost; // 최소 비용으로 가는 방법을 찾았으므로 배열에 저장
                pq.add(new Path(p.id, minCost[p.id]));

            }
        }
        if(minCost[B] <= C) return true;
        else return false;
    }
}
