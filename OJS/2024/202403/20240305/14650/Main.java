import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// 9분
public class Main {
    static int N, ans = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dfs(1,1);
        dfs(2, 1);
        System.out.println(ans);
    }
    static void dfs(int cur,int cnt){
        if(cnt == N){
            //System.out.println(cur);
            if(cur % 3 == 0){
                ans++;
            }
            return;
        }
        for(int i=0;i<=2;++i){
            dfs(cur*10 + i, cnt + 1);
        }
    }
}
