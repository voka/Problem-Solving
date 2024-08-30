import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class P24391 {
    static int N,M;
    static int[] parent;
    static int findP(int x){
        if(parent[x]!=x) return findP(parent[x]);
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
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int answer = 0;
        parent = new int[N+1];
        for(int i=1;i<=N;++i){
            parent[i] = i;
        }
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine()); 
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            unionP(a, b);
        }
        st = new StringTokenizer(br.readLine()); 
        int c = -1;
        for(int i=0;i<N;++i){
            int n = Integer.parseInt(st.nextToken());
            if((c != -1) && (findP(c) != findP(n))){
                answer++;
            }
            c = n;

        }
        System.out.println(answer);
        
    }
}
