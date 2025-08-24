import java.io.*;
import java.util.*;
public class P14245 {
    static int n;
    static int[] arr;
    static int[] lazy;
    static int size,H;
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[n];
        for(int i=0;i<n;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        H = (int) Math.ceil(Math.log(n) / Math.log(2));
        size = 1<<(1+H);
        lazy = new int[size];
        StringBuilder sb = new StringBuilder();
        int m = Integer.parseInt(br.readLine());
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            if(t == 1){
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                updateRange(1, 0, n-1, a, b, c);
            }else{
                int x = Integer.parseInt(st.nextToken());
                sb.append(querySingle(1, 0, n-1, x)).append('\n');
            }
        }
        System.out.println(sb);
    }
    static void updateRange(int node, int s, int e, int l, int r, int k){
        if(l > e || s > r){
            return;
        }
        if(l <= s && e <= r){
            lazy[node] ^= k;
            return;
        }
        int m = (s+e) >>> 1;
        updateRange(node << 1, s, m, l, r, k);
        updateRange(node << 1 | 1, m+1, e, l, r, k);
    }
    static int querySingle(int node, int s, int e, int id){
        if(id > e || id < s) return 0;
        if(s==e){
            return arr[s] ^ lazy[node];
        }
        int m = (s+e) >>> 1;
        if(id <= m) return lazy[node] ^ querySingle(node << 1, s, m, id);
        else return lazy[node] ^ querySingle(node << 1 | 1, m+1, e, id);
    }
}
