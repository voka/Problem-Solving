import java.util.*;
import java.io.*;
public class P2042{
    static int N, M, K;
    static long[] arr;
    static long[] tree;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new long[N];
        tree = new long[4*N];
        for(int i=0;i<N;++i){
            arr[i] = Long.parseLong(br.readLine());
        }
        init(0,N-1,1);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<M+K;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if(a == 1){
                long c = Long.parseLong(st.nextToken());
                update(0,N-1,1,b-1,c-arr[b-1]);
                arr[b-1] = c;
            }
            if(a == 2){
                int c = Integer.parseInt(st.nextToken());
                sb.append(sum(0,N-1,1,b-1,c-1)).append('\n');
            }

            // for(int j=0;j<4*N;++j){
            //     System.out.println(tree[j]);
            // }
        }
        System.out.println(sb.toString());
    }
    static long init(int s, int e, int node){
        if(s == e) return tree[node] = arr[s];
        int m = (s+e)/2;
        return tree[node] = init(s,m,node*2) + init(m + 1, e , node *2 + 1);
    }
    static long sum(int s, int e, int node, int l, int r){
        if(l>e || r < s) return 0;
        if(l<=s && e <= r) return tree[node];
        int m = (s+e)/2;
        return sum(s,m,node*2,l,r) + sum(m+1,e,node*2 + 1, l, r);
    }
    static void update(int s, int e, int node, int index, long dif){
        if(index>e || index < s) return;
        tree[node] += dif;
        if(s == e) return;
        int m = (s+e)/2;
        update(s,m,node*2,index,dif);
        update(m+1,e,node*2 + 1, index, dif);
    }
}
/**
 *  1 2 3 4 5
 * 3 -> 6
 * 15 -1 + 3 -> 17
 * 
 * 
 * 
 * 
 * 
 *                 15
 *         6                9
 *      3      3        4       5
 *   1      2
 * 
 * 
 *                 18
 *        6                 12
 *     3     3          7       5
 *  1     2
 * 
 * 
 *      6
 *   3      3
 *  1 2
 * 
 *      4
 *   3     1
 * 1  2 
 *
 *  */

