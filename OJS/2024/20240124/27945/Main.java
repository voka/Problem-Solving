import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

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
    public int compareTo(Edge o){
        if(this.cost > o.cost) return 1;
        else if(this.cost < o.cost) return -1;
        return 0;
    }
}

public class Main {
    static int N, M;
    static int[] parents;
    static Edge[] paths;

    static int findP(int x){
        if(parents[x] != x){
            parents[x] = findP(parents[x]);
        }
        return parents[x];
    }

    static void unionP(int a, int b){
        a = findP(a);
        b = findP(b);
        if(a < b){
            parents[b] = a;
        }
        else{
            parents[a] = b;
        }
    }
    static void printParents(){
        System.out.println("PARENT +++++++");
        for(int i=1;i<=N;++i){
            System.out.printf("%d ", parents[i]);
        }
        System.out.println();
    }
    public static void main(String[] args) throws IOException {
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
        for(int i = 0; i < N-1; ++i){
            //printParents();
            Edge edge = paths[i];
            if(edge.cost != day){
                break;
            }
            int a = edge.u; 
            int b = edge.v;
            if(findP(a) != findP(b)){
                unionP(a, b);
                day++;
            }
        }  
        System.out.println(day);

    }
}
