import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] honey;
    static long[] sum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        sum = new long[N+1];
        honey = new int[N+1];
        for(int i=1;i<=N;++i){
            honey[i] = Integer.parseInt(st.nextToken());
            sum[i] = sum[i-1] + honey[i];
        }
        long answer = 0;
        //1. 벌 - 벌 - 벌통 (첫번째 벌은 위치 1에 존재, 벌통은 N에 존재)
        for(int i=2;i<N;++i){
            answer = Math.max(answer, (sum[N] - sum[i] + sum[N] - sum[1] - honey[i]));
        }
        //2. 벌 - 벌통 - 벌
        for(int i=2;i<N;++i){
            answer = Math.max(answer,(sum[i] - sum[1] + sum[N-1] - sum[i-1]));
        }
        //3. 벌통 - 벌 - 벌
        for(int i=2;i<N;++i){
            answer = Math.max(answer,(sum[N-1] - honey[i] + sum[i-1]));
        }
        System.out.println(answer);
    }
}
