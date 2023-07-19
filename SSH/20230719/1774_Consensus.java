package MinimumSpanningTree;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
public class _1774_Consensus {
    static int n, m;
    static int[] root;
    static ArrayList<int[]> list = new ArrayList<>();
    static class Connection{
        int a,b;
        double cost;
        Connection(int a, int b, double cost){
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }
    static PriorityQueue<Connection> pqueue = new PriorityQueue<>(new Comparator<Connection>(){
        @Override
        public int compare(Connection o1, Connection o2){
            if(o1.cost <= o2.cost)
                return -1;
            else
                return 1;
        }
    });
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        root = new int[n+1];
        for(int i=0;i<=n;i++)
            root[i] = i;
        for(int i=0;i<n;i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            list.add(new int[]{x, y});
        }
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a,b);
        }
        for(int i = 0;i<n-1;i++){
            for(int j = i+1;j<=n-1;j++){
                double c =  Math.sqrt(Math.pow(list.get(i)[0] - list.get(j)[0],2) + Math.pow(list.get(i)[1] - list.get(j)[1],2));
                pqueue.add(new Connection(i+1,j+1,c));
            }
        }
        double result = 0;
        while(!pqueue.isEmpty()){
            Connection conn = pqueue.poll();
            if(union(conn.a,conn.b)){
                result += conn.cost;
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

        if( x != y){
            if( x < y) root[y] = x;
            else root[x] = y;
            return true;
        }
        return false;
    }
}
