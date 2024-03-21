import java.io.*;
import java.util.*;
public class Main {
    static int n, ans = 0;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        solve(n, 1, 2, 3);
        System.out.println(ans);
        System.out.println(sb.toString());
    }
    
    static void solve(int n, int s, int m, int e){
        if(n == 1){
            ans++;
            sb.append(s).append(' ').append(e).append('\n');
            return;
        }
        // 1 -> 2 n-1개 옮김
        solve(n-1, s, e, m);
        // 가장 큰 원판 1 -> 3으로 옮김
        solve(1, s, m, e);
        // 2 -> 3 n-1개 옮김
        solve(n-1, m, s, e);
    }


}