import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
class Edge implements Comparable<Edge>{
    int v;
    long c;

    public Edge(int v, long c){
        this.v = v;
        this.c = c;
    }
    @Override
    public int compareTo(Edge e){
        return Long.compare(c, e.c);
    }
}

public class Main {
    static int N, M, K;
    static ArrayList<ArrayList<Edge>> graph; 
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        graph = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        long[] dist = new long[N+1];
        for(int i=0;i<=N;++i){
            graph.add(new ArrayList<>());
            dist[i] = Long.MAX_VALUE;
        }
        for(int i=1;i<=M;++i){
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            graph.get(v2).add(new Edge(v1, c));
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        st = new StringTokenizer(br.readLine());
        boolean[] visit = new boolean[N+1];
        for(int i=1;i<=K;++i){
            int n = Integer.parseInt(st.nextToken());
            pq.add(new Edge(n, 0));
            dist[n] = 0;
            visit[n] = true; // 면접장인 도시만 건너뛰기 (면접장이 아닌 도시는 거쳐 가면서 비용이 더 작아질 수 있음.)
        }
        while(!pq.isEmpty()){
            Edge e = pq.poll();
            if(dist[e.v] < e.c){
                continue;
            }
            for(Edge ed : graph.get(e.v)){
                if(visit[ed.v]) continue;
                long nCost = e.c + ed.c;
                if(nCost < dist[ed.v]){
                    dist[ed.v] = nCost;
                    pq.add(new Edge(ed.v, nCost));
                }
            }
        }
        int minId = 0;
        long maxValue = 0;
        for(int i=1;i<=N;++i){
            if(maxValue < dist[i]){
                maxValue = dist[i];
                minId = i;
            }
        }
        System.out.println(minId);
        System.out.println(maxValue);

    }
}
