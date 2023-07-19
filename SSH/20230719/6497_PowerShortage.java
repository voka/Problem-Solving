package MinimumSpanningTree;
import java.io.*;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.Comparator;
public class _6497_PowerShortage {
    static int m, n;
    static int[] root;

    static class Load{
        int a,b,cost;
        Load(int a, int b, int cost){
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String tmp = "";

        while( !(tmp = br.readLine()).equals("0 0")){
            PriorityQueue<Load> pqueue = new PriorityQueue<>(new Comparator<Load>(){
                @Override
                public int compare(Load o1, Load o2){return o1.cost - o2.cost;}
            });
            StringTokenizer st = new StringTokenizer(tmp);
            m = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());

            root = new int[m];
            for(int i=0;i<m;i++)
                root[i] = i;

            int totalCost = 0;
            for(int i=0;i<n;i++){
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int z = Integer.parseInt(st.nextToken());
                pqueue.add(new Load(x,y,z));
                totalCost += z;
            }
            int minimumCost = 0;
            while(!pqueue.isEmpty()){
                Load load = pqueue.poll();
                if(union(load.a, load.b)){
                    minimumCost += load.cost;
                }
            }
            System.out.println(totalCost-minimumCost);

        }
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
