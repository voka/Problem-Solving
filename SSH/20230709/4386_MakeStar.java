package MinimumSpanningTree;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Comparator;
import java.util.PriorityQueue;
public class _4386_MakeStar {
    static int n;
    static double result = 0;
    static int[] root;
    static PriorityQueue<Edge> pQueue = new PriorityQueue<>(new Comparator<Edge>(){
        @Override
        public int compare(Edge o1, Edge o2){
            if(o1.cost <= o2.cost) return -1;
            else return 1;
        }
    });
    static class Edge{
        int a,b;
        double cost;
        Edge(int a, int b, double cost){
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }
    static class Star{
        double x,y;
        Star(double x, double y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        root = new int[n];
        for(int i=0;i<n;i++)
            root[i]=i;
        Star[] stars = new Star[n];
        for(int i=0;i<n;i++){
            st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            stars[i] = new Star(x,y);
        }
        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
                pQueue.add(new Edge(i,j, Math.sqrt(Math.pow(stars[i].x -stars[j].x , 2) + Math.pow(stars[i].y -stars[j].y , 2))));
            }
        }
        while(!pQueue.isEmpty()){
            Edge tmp = pQueue.poll();
            if(union(tmp.a, tmp.b)){
                result += tmp.cost;
            }
        }
        System.out.printf("%.2f",result);
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
            if(x < y) root[y] = x;
            else root[x] = y;
            return true;
        }
        else
            return false;
    }
}
