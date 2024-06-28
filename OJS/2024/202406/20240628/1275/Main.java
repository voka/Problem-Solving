import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int n,q;
    static long[] arr, tree;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        arr = new long[n];
        tree = new long[n*4];
        for(int i=0;i<n;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        init(0,n-1,1);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<q;++i){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            int a = Integer.parseInt(st.nextToken())-1;
            long b = Long.parseLong(st.nextToken());
            long diff = b - arr[a];
            int s = Math.min(x,y);
            int e = Math.max(x,y);
            long sum = query(0, n-1, 1, s, e);
            update(0, n-1, 1, a, diff);
            arr[a] = b;
            sb.append(sum).append('\n');
        }
        System.out.println(sb.toString());
        
    }
    static long init(int s, int e, int node){
        if(s == e) return tree[node] = arr[s];
        int m = (s+e)/2;
        return tree[node] = init(s,m,node*2) + init(m+1,e,node*2+1);
    }
    static long query(int s, int e, int node, int l, int r){
        if( r < s || l > e) return 0;
        if( l <= s && e <= r) return tree[node];
        int m = (s+e)/2;
        return query(s,m,node*2,l,r) + query(m+1,e,node*2+1,l,r);
    }

    static void update(int s, int e, int node, int idx, long diff){
        if( idx < s || idx > e) return;
        tree[node] += diff;
        if(s == e) return;
        int m = (s+e)/2;
        update(s,m,node*2,idx,diff);
        update(m+1,e,node*2+1,idx,diff);
    }
}
