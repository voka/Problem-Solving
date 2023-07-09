package MinimumSpanningTree;
import java.io.*;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.Comparator;
public class _1649_CityDividePlan {
    static int n,m,max,result=0;
    static int[] root;
    static PriorityQueue<Edge> pQueue = new PriorityQueue<>(new Comparator<Edge>(){
        @Override
        public int compare(Edge o1, Edge o2){return o1.cost - o2.cost;}
    });
    static class Edge{
        int a,b,cost;
        Edge(int a, int b, int cost){
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        root = new int[n+1];
        for(int i=0;i<=n;i++)
            root[i] = i;

        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            pQueue.add(new Edge(a,b,c));
        }

        while(!pQueue.isEmpty()){
            Edge tmp = pQueue.poll();
            if(union(tmp.a, tmp.b)){
                result += tmp.cost;
                max = tmp.cost;
            }
        }
        System.out.println(result-max);
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

        if( x != y){
            if(x < y) root[y] = x;
            else root[x] = y;
            return true;
        }
        else
            return false;

    }
}
