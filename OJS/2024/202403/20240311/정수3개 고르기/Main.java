import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n,m;
    static int[] nums;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        nums = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<n;++i){
            nums[i] = Integer.parseInt(st.nextToken());
        }

        int ans = -1;
        for(int i=0;i<n-2;++i){
            for(int j=i+1;j<n-1;++j){
                for(int k=j+1;k<n;++k){
                    int cur = nums[i] + nums[j] + nums[k];
                    if(cur <= m){
                        ans = Math.max(ans,cur);
                    }
                }
            }
        }
        System.out.println(ans);
       
    }
}