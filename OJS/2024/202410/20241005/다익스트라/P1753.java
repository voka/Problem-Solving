import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair>{
    int v;
    int w;
    public Pair(int v, int w){
        this.v = v;
        this.w = w;
    }
    @Override
    public int compareTo(Pair o){
        return this.w - o.w;
    }
}
public class P1753 {
    static int V,E;
    static int[] dist;
    static List<List<Pair>> graph;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(br.readLine());
        graph = new ArrayList<>();
        dist = new int[V+1];
        for(int i=0;i<=V;++i){
            graph.add(new ArrayList<>());
            dist[i] = Integer.MAX_VALUE;
        }
        for(int i=0;i<E;++i){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph.get(u).add(new Pair(v, w));
        }
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(s, 0));
        dist[s] = 0;
        while(!pq.isEmpty()){
            Pair cur = pq.poll();
            if(dist[cur.v] < cur.w) continue;
            for(Pair p : graph.get(cur.v)){
                Pair next = new Pair(p.v, cur.w + p.w);
                if(next.w < dist[next.v]){
                    dist[next.v] = next.w;
                    pq.add(next);
                }
            }
        }
        for(int i=1;i<=V;++i){
            if(dist[i] != Integer.MAX_VALUE) System.out.println(dist[i]);
            else System.out.println("INF");
        }
    }
}
