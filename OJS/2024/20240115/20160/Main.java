import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair>{
    long cost;
    int x;
    public Pair(long c, int x){
        this.x = x;
        this.cost = c;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.cost > o.cost){
            return 1;
        }else if(this.cost < o.cost){
            return -1;
        }
        return 0;
    }
}

public class Main {
    // 나는 파는 지점에 최대한 빨리 도착 
    // 아줌마는 파는 지점에 최대한 늦게 도착
    static long MAX = Long.MAX_VALUE;
    static int V, E, S;
    static long[] myShortestPath, sellerTotalTime = new long[10];
    static int[] sellingPoint = new int[10];
    static Map<Integer,long[]> sellerShortestPath;
    static List<List<Pair>> graph = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        sellerShortestPath = new HashMap<>();
        myShortestPath = new long[V];
        sellerTotalTime = new long[10];

        for(int i=0;i<V;++i){
            graph.add(new ArrayList<>());
            myShortestPath[i] = MAX;
        }
        for(int i=0;i<10;++i){
            sellerTotalTime[i] = 0;
        }
        int u=-1,v=-1,w=-1,cur = -1;
        for(int i=0;i<E;++i){
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken()) - 1;
            v = Integer.parseInt(st.nextToken()) - 1;
            w = Integer.parseInt(st.nextToken());
            graph.get(u).add(new Pair(w,v));
            graph.get(v).add(new Pair(w,u));
            
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<10;++i){
            sellingPoint[i] = Integer.parseInt(st.nextToken()) - 1;
            sellerTotalTime[i] = -1;
        }
        sellerTotalTime[0] = 0;
        for(int i=0;i<10;){
            //System.out.printf("%d, %d\n", sellingPoint[i],i);
            int next = -1;
            long[] temp = new long[V];
            Arrays.fill(temp, MAX);
            sellerShortestPath.put(sellingPoint[i],temp);
            findShortestPath(sellingPoint[i],sellerShortestPath.get(sellingPoint[i]));
            for(int j=i+1;j<10;++j){
                if(sellerShortestPath.get(sellingPoint[i])[sellingPoint[j]] < MAX){ // 갈 수 있는 지점을 발견하면
                    next = j;
                    break;
                }
            }
            if(next == -1) break;
            sellerTotalTime[next] = sellerTotalTime[i] + sellerShortestPath.get(sellingPoint[i])[sellingPoint[next]];
            i = next;
            //printArray(sellingPoint);
            //printArrayL(sellerTotalTime);
            //print2DArray();

        }
        S = Integer.parseInt(br.readLine()) - 1;
        findShortestPath(S,myShortestPath);
        int answer = V + 1;
        //printArrayL(myShortestPath);
        //printArrayL(sellerTotalTime);
        for(int i=0;i<10;++i){
            cur = sellingPoint[i];
            if(myShortestPath[cur] <= sellerTotalTime[i]){
                answer = Math.min(answer,cur + 1);
            }
            //System.out.printf("%d ", sellerTotalTime[sellingPoint[i]]);
        }
        if(answer == V+1) answer = -1;
        System.out.println(answer);
    }
    static void findShortestPath(int s, long[] shortest){
        shortest[s] = 0;
        PriorityQueue<Pair> pq  = new PriorityQueue<>();
        pq.add(new Pair(0, s));
        while(!pq.isEmpty()){
            Pair p = pq.poll();
            if(shortest[p.x] < p.cost) continue;
            for(Pair np : graph.get(p.x)){
                int n = np.x;
                long ncost = np.cost + shortest[p.x];
                if(shortest[n] > ncost){
                    shortest[n] = ncost;
                    pq.add(new Pair(ncost, n));
                }
            }
        }
        // while(!pq.isEmpty()){
        //     Pair p = pq.poll();
        //     System.out.printf("%d, %d\n",p.cost,p.x);
        // }
    }
    static void print2DArray(){
        System.out.println();
        for(int i : sellerShortestPath.keySet()){
            for(int j=0;j<sellerShortestPath.get(i).length;++j){
                System.out.printf("%d ", sellerShortestPath.get(i)[j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void printArrayL(long[] k){
        System.out.println();
        for(int i=0;i<k.length;++i){
            System.out.printf("%d ",k[i]);
        }
        System.out.println();
    }
    static void printArray(int[] k){
        System.out.println();
        for(int i=0;i<k.length;++i){
            System.out.printf("%d ",k[i]);
        }
        System.out.println();
    }

}

/**
 * 1 2 3 4 5 1 2 3 4 5
 * 0 1 2 3 -1 4 5 6 7 -1
 */