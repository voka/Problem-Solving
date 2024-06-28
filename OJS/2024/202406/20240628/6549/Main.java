import java.io.*;
import java.util.*;
public class Main {
    static int n;
    static int []arr;
    static int []tree;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while(true){
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            if(n == 0) break;
            arr = new int[n];
            tree = new int[n*4];
            for(int i=0;i<n;++i){
                arr[i] = Integer.parseInt(st.nextToken());
            }
            init(0,n-1,1);
            sb.append(getMaxSize(0,n-1)).append('\n');
        }
        System.out.println(sb.toString());

    }
    static int init(int s, int e, int node){
        if(s == e) return tree[node] = s;
        int m = (s+e)/2;
        init(s,m,node*2);
        init(m+1,e,node*2+1);
        if(arr[tree[node*2]] <= arr[tree[node*2+1]]) return tree[node] = tree[node*2];
        else return tree[node] = tree[node*2+1];
    }
    static int getMinH(int s, int e, int node, int l, int r){
        if(l > e || r < s) return -1;
        if(l <= s && e <= r) return tree[node];
        int m = (s + e) / 2;
        int lm = getMinH(s,m,node*2,l,r);
        int rm = getMinH(m+1,e,node*2+1,l,r);
        if(lm == -1) return rm;
        if(rm == -1) return lm;
        if(arr[lm] <= arr[rm]) return lm;
        else return rm;
    }
    static long getMaxSize(int s, int e){
        int minH = getMinH(0,n-1,1,s,e);
        long answer = (long)(e-s+1) * (long)arr[minH];
        if(s < minH){
            answer = Math.max(answer,getMaxSize(s,minH-1));
        }
        if(minH < e){
            answer = Math.max(answer,getMaxSize(minH+1,e));
        }
        return answer;
    }
}
