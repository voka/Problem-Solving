import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        long[] res = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<n;++i){
            res[i] = Long.parseLong(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        long nc = Long.parseLong(st.nextToken());
        long mc = Long.parseLong(st.nextToken());
        long ans = 0;
        for(int i=0;i<n;++i){
            long target = res[i];
            long check = nc;
            ans+=1;
            if(check < target){
                long rest = target - check;
                double plus = Math.ceil((double)rest/mc);
                ans+=plus;
            }
        }
        System.out.println(ans);
    }
}