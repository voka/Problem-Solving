import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair> {
    int next;
    int cost; 
    public Pair(int next, int cost){
        this.next = next;
        this.cost = cost;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.cost > o.cost){
            return 1;
        }
        else if(this.cost < o.cost){
            return -1; 
        }
        return 0;
    }
    
}
public class Main {
    static int n, m, s, d; 
    static List<List<Pair>> graph; 
    static int[] dist;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new ArrayList<>();
        dist = new int[n];
        for(int i=0;i<n;++i){
            graph.add(new ArrayList<>());
            dist[i] = Integer.MAX_VALUE;
        }
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken());
            graph.get(a).add(new Pair(b, c));
            graph.get(b).add(new Pair(a, c));
        }
        st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken())-1;
        d = Integer.parseInt(st.nextToken())-1;

        findShortestPath();
        System.out.println(dist[d]);
    }
    static void findShortestPath(){
        dist[s] = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(s, 0 ));
        while(!pq.isEmpty()){
            Pair cur = pq.poll();
            if(dist[cur.next] < cur.cost) continue; 
            for(Pair pair : graph.get(cur.next)){
                if(dist[pair.next] > pair.cost + cur.cost){
                    dist[pair.next] = pair.cost + cur.cost;
                    pq.add(new Pair(pair.next, dist[pair.next]));
                }
            }

        }
    }
}
