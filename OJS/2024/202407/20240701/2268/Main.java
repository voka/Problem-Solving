import java.io.*;
import java.util.*;
public class Main {

    static int N,M;

    static int[] arr;
    static long[] tree;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N];
        tree = new long[N*4];
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            int j = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            if(c == 0){
                if(j < k){
                    sb.append(query(0, N-1, 1, j-1, k-1));
                }else{
                    sb.append(query(0, N-1, 1, k-1, j-1));
                }
                sb.append('\n');
            }else{
                update(0, N-1, 1, j-1, k - arr[j-1]);
                arr[j-1] = k;
            }
        }
        System.out.println(sb.toString());
    }
    static long query(int s, int e, int node, int l, int r){
        if(s > r || e < l) return 0L;
        if(l <= s && e <= r) return tree[node];
        int m = (s + e) / 2;
        return query(s, m, node * 2, l, r) + query(m+1,e,node*2 + 1, l , r);
    }
    static void update(int s, int e, int node, int idx, long target){
        if( idx < s || idx > e) return;
        tree[node] += target;
        if( s == e) return;
        int m = (s+e)/2;
        update(s, m, node*2, idx, target);
        update(m+1, e, node*2 + 1, idx, target);
    }
}
