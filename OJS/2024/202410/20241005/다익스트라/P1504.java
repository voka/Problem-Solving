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
public class P1504 {
    static int V,E,Max = 8000 * 1000 + 1;
    static List<List<Pair>> graph;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        graph = new ArrayList<>();
        for(int i=0;i<=V;++i){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<E;++i){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph.get(u).add(new Pair(v, w));
            graph.get(v).add(new Pair(u, w));
        }
        int f = 1;
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int[] fDist = getMinDistance(f);
        int[] sDist = getMinDistance(s);
        int[] tDist = getMinDistance(t);
        //printArr(fDist);
        //printArr(sDist);
        //printArr(tDist);
        int answer = Math.min(fDist[s] + sDist[t] + tDist[V], fDist[t] + tDist[s] + sDist[V]);
        if(answer >= Max){
            System.out.println(-1);
        }else{
            System.out.println(answer);
        }
    }
    static void printArr(int[] a) {
        for(int i=0;i<a.length;++i){
            System.out.printf("%d ", a[i]);
        }
        System.out.println();
    }
    static int[] getMinDistance(int s){
        int[] dist = new int[V+1];

        for(int i=0;i<=V;++i){
            dist[i] = Max;
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
        return dist;
    }
}

/*(
4 5
1 2 2
1 3 4
2 3 1
2 4 5
3 4 7
2 3


) */