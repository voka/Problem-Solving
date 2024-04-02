import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
// 10분
public class Main {
    static int[] dp, arr;
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[N];
        dp = new int[N];
        for(int i=0;i<N;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int ans = -1;
        dp[0] = arr[0];
        for(int i=0;i<N;++i){
            dp[i] = Math.max(dp[i],arr[i]); 
            for(int j=i+1;j<N;++j){
                if(arr[i] > arr[j]){
                    dp[j] = Math.max(dp[j], dp[i] + arr[j]);
                }
            }
            ans = Math.max(dp[i],ans);
        }
        System.out.println(ans);
    }
}
