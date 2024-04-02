import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int ans = 0;
        for(int i=0;i<=N;++i){
            if(solve(i) == N){
                ans = i;
                break;
            }
        }
        System.out.println(ans);
    }
    static int solve(int m){
        int re = m;
        while(m>0){
            re += m%10;
            m = m / 10;
        }
        return re;
    }

}