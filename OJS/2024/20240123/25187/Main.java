import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, M, Q;
    static int[] parents,value;

    static int findP(int x){
        if(parents[x] != x){
            parents[x] = findP(parents[x]);
        }
        return parents[x];
    }
    static void union(int a, int b){
        a = parents[a];
        b = parents[b];
        if( a < b){
            parents[b] = a;
        }else{
            parents[a] = b;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        parents = new int[N+1];
        value = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;++i){
            parents[i] = i;
            int cur = Integer.parseInt(st.nextToken());
            if(cur == 1){
                value[i] = 1;
            }else{
                value[i] = -1;
            }
        }
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if(findP(a) != findP(b)){
                union(a, b);
            }
        }
        for(int i=1;i<=N;++i){
            findP(i); /// 여기서 최종 부모 업데이트 안해주면 틀림. 혹은 밑에서 parents[cur] 대신 findP(cur) 써야 할듯. 
            //System.out.printf("%d ",parents[i]);
            if(parents[i] != i) value[parents[i]] += value[i];
        }
        // for(int i=1;i<=N;++i){
        //     System.out.printf("%d ",value[i]);
        // }
        // System.out.println();
        
        for(int q=0;q<Q;++q){
            int cur = Integer.parseInt(br.readLine());
            if(value[parents[cur]] > 0) sb.append('1');
            else sb.append('0');
            if(q != Q-1) sb.append('\n');
        }
        System.out.println(sb.toString());
    }
}
