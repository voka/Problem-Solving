import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
class Pair implements Comparable<Pair>{
    int a;
    int b;
    int c;
    public Pair(int a, int b, int c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @Override
    public int compareTo(Pair o){
        return this.c - o.c;
    }
    @Override
    public String toString(){
        return String.format("a = %d, b = %d, c = %d\n", a,b,c );
    }
}

public class Main {
    static long answer = 0;
    static int[] parent;
    static List<Pair> edges;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int V, E;
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        parent = new int[V+1];
        for(int i=1;i<=V;++i){
            parent[i] = i;
        }
        edges = new ArrayList<>();
        for(int i=0;i<E;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            edges.add(new Pair(a,b,c));
        }
        Collections.sort(edges);
        for(Pair p : edges){
            if(findP(p.a) != findP(p.b)){
                //System.out.println(p.toString());
                unionP(p.a, p.b);
                answer += p.c;
            }
        }
        System.out.println(answer);

    }
    static int findP(int x){
        if(parent[x] != x) {
            parent[x] = findP(parent[x]);
        }
        return parent[x];
    }
    static void unionP (int a, int b){
        int Pa = findP(a);
        int Pb = findP(b);
        if(Pa > Pb){
            parent[Pa] = Pb; 
        }else{
            parent[Pb] = Pa;
        }
    }
}
