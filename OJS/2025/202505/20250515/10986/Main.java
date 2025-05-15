import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N,M;
    static long[] sum;
    static long[] mod;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        sum = new long[N+1];
        mod = new long[M];
        st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;++i){
            sum[i] = sum[i-1] + Integer.parseInt(st.nextToken());
            int m = (int) (sum[i] % M) ;
            mod[m]++;
        }
        long cnt = mod[0];
        for(int i=0;i<M;++i){
            if(mod[i] > 1){
                cnt += (mod[i] * (mod[i]-1) / 2);
            }
        }
        System.out.println(cnt);
    }  
}
