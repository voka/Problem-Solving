package MinimumSpanningTree;
import java.io.*;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
public class _1197_MST {

    static class Edge{
        int a, b, cost;
        Edge(int a, int b, int cost){
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }

    static int v,e,MSTcost=0;
    static int[] root;
    static PriorityQueue<Edge> pqueue = new PriorityQueue<>(new Comparator<Edge>(){
        @Override
        public int compare(Edge o1, Edge o2){return o1.cost - o2.cost;};
    });
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        root = new int[v+1];
        for(int i=0;i<=v;i++)
            root[i] = i;

        for(int i=0;i<e;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            pqueue.add(new Edge(a,b,c));
        }

        while(!pqueue.isEmpty()){
            Edge tmp = pqueue.poll();
            if(union(tmp.a, tmp.b)){
                MSTcost += tmp.cost;
            }
        }
        System.out.println(MSTcost);
    }
    public static int find(int x){
        if(root[x] == x)
            return x;
        else
            return find(root[x]);
    }
    public static boolean union(int x, int y){
        x = find(x);
        y = find(y);

        if(x != y){
            if( x < y) root[y] = x;
            else root[x] = y;
            return true;
        }
        else{
            return false;
        }
    }
}
