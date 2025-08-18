import java.io.*;
import java.util.*;

class Edge implements Comparable<Edge>{
    int v;
    int w;
    int cost;
    public Edge(int v, int w, int cost){
        this.v = v;
        this.w = w;
        this.cost = cost;
    }
    @Override
    public int compareTo(Edge e){
        return this.cost - e.cost; 
    }

}
public class P14950 {

    static int T,M,N;
    static int [] parent, size; 
    static Edge[] edges;

    static int findP(int x){
        if(parent[x] != x ) return parent[x] = findP(parent[x]);
        return parent[x];
    }
    static void unionP(int a, int b){
        int ap = findP(a);
        int bp = findP(b);
        int root, child; 

        if(size[bp] == size[ap]){
            if(bp < ap){
                root = bp;
                child = ap;
            }else{
                root = ap; 
                child = bp;
            }
        }else{
            if(size[bp] > size[ap]){
                root = bp;
                child = ap;
            }else{
                root = ap;
                child = bp;
            }
        }
        parent[child] = root;

    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        T = Integer.parseInt(st.nextToken());

        edges = new Edge[M];
        parent = new int[N+1];
        size = new int[N+1];

        for(int i=1;i<=N;++i){
            parent[i] = i;
            size[i] = 1;
        }

        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());

            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            edges[i] = new Edge(v, w, cost);
            
        }
        Arrays.sort(edges);

        int answer = 0;
        int tCost = 0;
        for(int i=0;i<M;++i){
            Edge e = edges[i];
            if(findP(e.v) != findP(e.w)){
                unionP(e.v, e.w);
                answer += e.cost + tCost;
                tCost += T;
            }

        }
        System.out.println(answer);
    }    
}
