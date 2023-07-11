package ShortestPath;
import java.io.*;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
public class _1916_MinimumCost {
    static int n,m,start,end;
    static int[] dist;
    static ArrayList<ArrayList<Bus>> list = new ArrayList<>();

    static class Bus{
        int end, cost;
        Bus(int end, int cost){
            this.end = end;
            this.cost = cost;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        dist = new int[n+1];
        for(int i=0;i<=n;i++){
            list.add(new ArrayList<Bus>());
            dist[i] = Integer.MAX_VALUE;
        }

        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            list.get(a).add(new Bus(b, cost));
        }
        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        dijkstra(start);

        System.out.println(dist[end]);
    }
    public static void dijkstra(int start){
        PriorityQueue<Bus> pqueue = new PriorityQueue<>(new Comparator<Bus>(){
            @Override
            public int compare(Bus o1, Bus o2){return o1.cost - o2.cost;}
        });
        boolean[] check = new boolean[n+1];
        pqueue.add(new Bus(start,0));
        dist[start] = 0;
        while(!pqueue.isEmpty()){
            Bus tmp = pqueue.poll();
            int cur = tmp.end;

            if(check[cur] == true) continue;
            check[cur] = true;

            for(Bus bus : list.get(cur)){
                if(dist[bus.end] > dist[cur] + bus.cost){
                    dist[bus.end] = dist[cur]+ bus.cost;
                    pqueue.add(new Bus(bus.end, dist[bus.end]));
                }
            }
        }
    }
}
