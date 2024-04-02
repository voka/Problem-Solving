import java.io.*;
import java.util.*;
class Tri implements Comparable<Tri>{
    int a;
    int b;
    int w;
    public Tri(int a, int b, int w){
        this.a = a;
        this.b = b;
        this.w = w;
    }
    @Override
    public int compareTo(Tri o){
        if(this.w > o.w) return 1;
        else if(this.w < o.w) return -1;
        return 0;
    }

}
public class Main {
    static int n,m;
    static int[] parents;
    static List<Tri> edges = new ArrayList<>();
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
        }else{
            parents[a] = b;
        }
    }
    public static void main(String[] args) throws IOException {
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        parents = new int[n];
        for(int i=0;i<n;++i){
            parents[i] = i;
        }
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken())-1;
            int w = Integer.parseInt(st.nextToken());
            edges.add(new Tri(a,b,w));
        }
        int ans = 0;
        Collections.sort(edges);
        for(Tri t : edges){
            if(findP(t.a) != findP(t.b)){
                ans += t.w;
                unionP(t.a,t.b);
            }
        }
        System.out.println(ans);
    }

}