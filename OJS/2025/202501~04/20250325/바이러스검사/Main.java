import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int n,leader,member;
    static int[] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[n];
        for(int i=0;i<n;++i){   
            arr[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        leader = Integer.parseInt(st.nextToken());
        member = Integer.parseInt(st.nextToken());

        long ans = n;
        for(int c : arr){
            int cur = c - leader;
            if(cur < 0) continue;
            if(cur % member == 0){
                ans += (cur/member);
            }else{
                ans += (cur/member) + 1;
            }
        }
        System.out.println(ans);

        // Please write your code here.
    }
}