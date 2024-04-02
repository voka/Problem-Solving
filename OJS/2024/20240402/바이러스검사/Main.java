import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] res = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<n;++i){
            res[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        int nc = Integer.parseInt(st.nextToken());
        int mc = Integer.parseInt(st.nextToken());
        int ans = 0;
        for(int i=0;i<n;++i){
            int target = res[i];
            int check = nc;
            ans+=1;
            while(check < target){
                check+=mc;
                ans++;
            }
        }
        System.out.println(ans);
    }
}