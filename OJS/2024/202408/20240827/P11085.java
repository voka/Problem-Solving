import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

class TPair implements Comparable<TPair>{
    int s;
    int e;
    int w;
    public TPair(int s, int e, int w){
        this.s = s;
        this.e = e;
        this.w = w;
    }
    @Override
    public int compareTo(TPair o) {
        if(this.w < o.w) return 1;
        else if(this.w > o.w) return -1;
        return 0;
    }
}
public class P11085 {
    static int P,W,C,V;
    static int[] parent;
    static List<TPair> edges = new ArrayList<>();
    static int findP(int x){
        if(parent[x] != x){
            return findP(parent[x]);
        }
        return parent[x];
    }
    static void unionP(int a, int b){
        int pa = findP(a);
        int pb = findP(b);
        if(pa < pb){
            parent[pb] = pa;
        }else{
            parent[pa] = pb;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()); 
        P = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine()); 
        C = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());
        parent = new int[P];
        for(int i=0;i<P;++i){
            parent[i] = i;
        }
        for(int i=0;i<W;++i){
            st = new StringTokenizer(br.readLine()); 
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            edges.add(new TPair(s, e, w));
        }
        Collections.sort(edges);
        int answer = 0;
        for(TPair tPair : edges){
            unionP(tPair.s, tPair.e);
            if(findP(C) == findP(V)){
                answer = tPair.w;
                break;
            }
        }
        System.out.println(answer);
        
    }
}
