import java.io.*;
import java.util.*;

public class P1395 {
    static int N,M;
    static int[] tree, lazy;
    static int H, size;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
    
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = (int) Math.ceil(Math.log(N) / Math.log(2));
        size = (1<<(1+H));
        tree = new int[size];
        lazy = new int[size];
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken())-1;
            int t = Integer.parseInt(st.nextToken())-1;
            if(c == 0){
                updateRange(1,0,N-1,s,t);
            }else{
               sb.append(query(1, 0, N-1, s, t)).append('\n');
            }
        }
        System.out.println(sb.toString());

        
    }

    static void updateLazy(int node, int s, int e){
        if(lazy[node] != 0){
            tree[node] = (e - s + 1) - tree[node];
            if(s != e){
                lazy[node << 1] ^= 1;
                lazy[node << 1 | 1] ^= 1; 
            }
            lazy[node] = 0;
        }

    }
    static void updateRange(int node, int s, int e, int l , int r){
        updateLazy(node,s,e);
        if(l > e || r < s) return;
        if(l <= s &&  e <= r) {
            lazy[node] = 1 - lazy[node];
            updateLazy(node, s, e); 
            return;
        }
        int m = (s+e) >>> 1;
        updateRange(node << 1, s, m, l, r);
        updateRange(node << 1 | 1, m+1, e, l, r);
        tree[node] = tree[node << 1] + tree[node << 1| 1];
    }
    static int query(int node, int s, int e, int l, int r){
        updateLazy(node, s, e);
        if(l > e || s > r) return 0;
        if(l <= s && e <= r){
            return tree[node];
        }
        int m = (s+e) >>> 1;
        int lsum = query(node <<1 , s, m, l, r);
        int rsum = query(node <<1|1, m+1, e, l, r);
        return lsum + rsum;
    }
}
