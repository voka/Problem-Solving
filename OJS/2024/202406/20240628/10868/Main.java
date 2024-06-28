import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int INF = 1000000001;
    static int n,m;
    static int[] minTree, arr;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n];
        minTree = new int[n*4];
        for(int i=0;i<n;++i){
            arr[i] = Integer.parseInt(br.readLine());
        }
        initMin(0,n-1,1);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken())-1;
            int min = queryMin(0, n-1, 1, a, b);
            sb.append(min).append('\n');
        }
        System.out.println(sb.toString());
    }
    static int queryMin(int s, int e, int node, int l, int r){
        if( s > r || e < l) return INF;
        if( l <= s && e <= r) return minTree[node];
        int m = (s+e)/2;
        return Math.min(queryMin(s, m, node*2, l, r), queryMin(m+1, e, node*2+1, l, r));
    }
    static int initMin(int s, int e, int node){
        if(s == e) return minTree[node] = arr[s];
        int m = (s+e)/2;
        return minTree[node] = Math.min(initMin(s, m, node*2), initMin(m+1, e, node*2 + 1));
    }
}
