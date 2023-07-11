package ShortestPath;
import java.io.*;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class _1753_ShortestPath {
    static int v, e, start;
    static int[] dist;

    static PriorityQueue<Edge> pqueue = new PriorityQueue<>(new Comparator<Edge>(){
        @Override
        public int compare(Edge o1, Edge o2){return o1.w - o2.w;}
    });
    static ArrayList<ArrayList<Edge>> list  = new ArrayList<>();
    static class Edge{
        int u,v,w;
        Edge(int u, int v, int w){
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(br.readLine());

        dist = new int[v+1];
        for(int i=0;i<=v;i++)
            list.add(new ArrayList<Edge>());

        for(int i=0;i<=v;i++)
            dist[i] = Integer.MAX_VALUE;

        for(int i=0;i<e;i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            list.get(u).add(new Edge(u,v,w));
        }

        dijkstra(start);
        for(int i=1;i<=v;i++){
            if(dist[i] == Integer.MAX_VALUE)
                System.out.println("INF");
            else
                System.out.println(dist[i]);
        }

    }
    public static void dijkstra(int start){
        int[] check = new int[v+1];
        pqueue.add(new Edge(start,start,0));
        dist[start] = 0;

        while(!pqueue.isEmpty()){
            Edge tmp = pqueue.poll();
            int cnode = tmp.v;
            if (check[cnode] == 1)
                continue;
            check[cnode] = 1;

            for(Edge edge : list.get(cnode)){
                if(dist[edge.v] > dist[cnode] + edge.w){
                    dist[edge.v] = dist[cnode] + edge.w;
                    pqueue.add(new Edge(cnode,edge.v,dist[edge.v]));
                }
            }
        }
    }

}
