import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] dp,w;
    static int N;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        w = new int[N][N];
        dp = new int[N][(int) Math.pow(2,N)];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                w[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.fill(dp[i], -1);
        }
        System.out.println(tsp(0,1));

    }
    static int tsp(int city, int status){
        if(status == (1<<N) -1){ // 모든 도시를 다 방문했다면
            return (w[city][0] != 0) ? w[city][0]: Integer.MAX_VALUE ;
        }
        if(dp[city][status] != -1) return dp[city][status];
        dp[city][status] = Integer.MAX_VALUE;
        for(int next=0;next<N;++next){
            if(!check(status, next) && w[city][next] != 0){ // 방문한적없고 갈수 없지 않다면
                int cost = tsp(next, visit(status, next)) + w[city][next];
                if(cost < dp[city][status]){
                    dp[city][status] = cost;
                }
            }
        }

        return dp[city][status];
    }

    static int visit(int status, int city){
        return status | (1<<city);
    }
    static boolean check(int status, int city){
        if((status & (1<<city)) == (1<<city)) return true;
        else return false;
    }
}
