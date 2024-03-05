import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
// 20분
public class Main {
    static int N, Q;
    static int[] arr, sum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        sum = new int[N];
        int cnt = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;++i){
            arr[i] = Integer.parseInt(st.nextToken());
            if(i>0){
                if(arr[i] < arr[i-1]){
                    cnt++;
                }
                sum[i] = cnt;
            }
        }
        Q = Integer.parseInt(br.readLine());
        for(int i=0;i<Q;++i){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            System.out.println(sum[y-1] - sum[x-1]);
        }
    }    
}
