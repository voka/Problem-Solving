import java.io.*;
import java.util.*;

class Edge implements Comparable<Edge>{
    int u;
    int v;
    int cost;
    public Edge(int u, int v, int cost){
        this.u = u;
        this.v = v;
        this.cost = cost;
    }
    @Override
    public int compareTo(Edge e){
        if(this.cost > e.cost) return 1;
        else if(this.cost <e.cost) return -1;
        return 0;
    }
}
public class P27945 {
    static int N,M;
    static int[] parents;
    static Edge[] paths;
    static int findP(int x){
        if(parents[x]!=x){
            parents[x] = findP(parents[x]);
        }
        return parents[x];
    }
    static void unionP(int a, int b){
        a = findP(a);
        b = findP(b);
        if(a < b){
            parents[b] = a;
        }else{
            parents[a] = b;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        parents = new int[N+1];
        for(int i=1;i<=N;++i){
            parents[i] = i;
        }
        paths = new Edge[M];
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            paths[i] = new Edge(u,v,t);
        }
        Arrays.sort(paths);
        int day = 1;
        for(int i=0;i<N-1;++i){
            Edge edge = paths[i];
            if(edge.cost != day){
                break;
            }
            int u = edge.u;
            int v = edge.v;
            if(findP(u) != findP(v)){
                unionP(u, v);
                day++;
            }
        }
        System.out.println(day);

    }
}
