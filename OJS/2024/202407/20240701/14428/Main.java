import java.io.*;
import java.util.*;
public class Main {

    static int N,M;

    static int[] arr;
    static int[] tree;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        tree = new int[N*4];
        init(0,N-1,1);
        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            int j = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            if(c == 1){
                arr[j-1] = v;
                update(0, N-1, 1, j-1);
            }else{
                sb.append(query(0, N-1, 1, j-1, v-1) + 1).append('\n');
            }
        }
        System.out.println(sb.toString());
    }
    static int init(int s, int e, int node){
        if(s==e) return tree[node] = s;
        int m = (s+e)/2;
        init(s, m, node*2);
        init(m+1, e, node*2+1);
        if(arr[tree[node*2]] <= arr[tree[node*2 + 1]]) return tree[node] = tree[node*2];
        else return tree[node] = tree[node*2+1];
    }
    static int query(int s, int e, int node, int l, int r){
        if(s > r || e < l ) return -1;
        if(l <= s && e <= r) return tree[node];
        int m = (s+e)/2; 
        int m1 = query(s, m, node*2, l, r);
        int m2 = query(m+1, e, node*2+1, l, r);
        if(m1 == -1) return m2;
        if(m2 == -1) return m1;
        if(arr[m1] <= arr[m2]) return m1;
        else return m2;
    }
    static int update(int s, int e, int node, int idx){
        if(idx < s || idx > e) return -1;
        if(s==e) return tree[node];
        int m = (s+e) / 2;
        update(s, m, node*2, idx);
        update(m+1,e,node*2+1,idx);
        if(tree[node*2] == -1) return tree[node] = tree[node*2+1];
        if(tree[node*2 + 1] == -1) return tree[node] = tree[node*2]; 
        if(arr[tree[node*2]] <= arr[tree[node*2 + 1]]) return tree[node] = tree[node*2];
        else return tree[node] = tree[node*2+1];
    }
}
/// 1.000.000.000