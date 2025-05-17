import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] arr = new int[1000001];
        long[] sum = new long[1000001];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            int g = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            arr[x] = g;
        }
        for(int i=0;i<=1000000;++i){
            if(i != 0) sum[i] = sum[i-1] + arr[i];
            else sum[i] = arr[i];
        }
        int answer = -1;
        if(K > 1000000 - K){
            answer = (int) sum[1000000];
        }
        for(int p=K;p<=1000000-K;++p){
            int cur = 0;
            if(p + K > 1000000){
                cur += sum[1000000];
            }else{
                cur += sum[p+K];
            }
            if(p-K-1 >= 0){
                cur -= sum[p-K-1];
            }
            answer = Math.max(answer,cur);
        }
        System.out.println(answer);
    }
    
}
