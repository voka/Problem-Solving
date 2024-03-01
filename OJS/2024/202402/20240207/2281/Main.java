import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n,m;
    static int[] names, dp;//dp[i] -> i번째 이름을 새로운 줄에 쓸때 남는 공간의 최소값
    static int dfs(int c){
        if(dp[c] < Integer.MAX_VALUE) return dp[c];
        int empty = m - names[c]; // 이번 줄의 남는 칸 개수
        for(int i=c+1;i<=n;++i){
            if(empty < 0) break; // 남는 칸이 없다면 이름 쓰기를 종료한다.
            if(i == n){ // 마지막 줄이라면
                dp[c] = 0;
                break;
            }
            dp[c] = Math.min(dp[c], empty * empty + dfs(i));
            empty = empty - names[i] - 1;
        }
        return dp[c];

    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        names = new int[n];
        dp = new int[n];
        for(int i=0;i<n;++i){
            dp[i] = Integer.MAX_VALUE;
            names[i] = Integer.parseInt(br.readLine()); 
        }
        dp[n-1] = 0; // 이 숫자는 항상 맨마지막 줄에 있으므로 0
        System.out.println(dfs(0));
        
        
    }
}
