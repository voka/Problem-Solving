import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/1753

class Pair implements Comparable<Pair>{
    int v;
    int cost;
    public Pair(int v, int cost){
        this.v = v;
        this.cost = cost;
    }
    @Override
    public int compareTo(Pair o){
        return this.cost - o.cost;
    }
}
public class 다익스트라 {
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
            if(dist[cur.v] < cur.cost) continue;
            for(Pair p : graph.get(cur.v)){
                Pair next = new Pair(p.v, cur.cost + p.cost);
                if(next.cost < dist[next.v]){
                    dist[next.v] = next.cost;
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