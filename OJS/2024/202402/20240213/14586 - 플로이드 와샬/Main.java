import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair>{
    long x;
    long h;
    public Pair(long x, long h){
        this.x = x;
        this.h = h;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.x > o.x) return 1;
        else if(this.x < o.x) return -1;
        return 0;
    }
    
}
public class Main {
    static int N, MAX_PUSH;
    static Pair[] pair;
    static int[] left, right;
    static int[][] dp;// dp[i][j] : i ~ j까지의 도미노를 넘어뜨릴때 밀어야 하는 최소 횟수
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        MAX_PUSH = N;
        dp = new int[N][N];
        pair = new Pair[N];
        left = new int[N];
        right = new int[N];
        StringTokenizer st;
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long h = Long.parseLong(st.nextToken());
            pair[i] = new Pair(x, h);
            Arrays.fill(dp[i], MAX_PUSH);
        }
        Arrays.sort(pair);
        for(int i=0;i<N;++i){
            right[i] = push(i,1);
            left[i] = push(i,0);
            for(int j=i;j<=right[i];++j){
                dp[i][j] = 1;
            }
            for(int j=left[i];j<=i;++j){
                dp[j][i] = 1;
            }
        }
        for(int k=0;k<N-1;++k){
            for(int i=0;i<N;++i){
                for(int j=0;j<N;++j){
                    if(i == j) dp[i][j] = 1;
                    dp[i][j] = Math.min(dp[i][k] + dp[k+1][j],dp[i][j]);
                }
            }
        }
        System.out.println(dp[0][N-1]);
    }

    // return : i번째 도미노를 dir 방향으로 넘어뜨리려고 할때 넘어지는 최종 인덱스
    static int push(int i, int dir){

        if(dir == 0){// 왼쪽 도미노 넘어뜨리기
            long preRange = pair[i].x - pair[i].h;
            while(i > 0){
                if(pair[i-1].x >= preRange){ // 왼쪽에 있는 도미노를 넘어뜨릴 수 있다면
                    //System.out.printf("%d <- %d\n", i-1, i);
                    preRange = Math.min(preRange,pair[i-1].x - pair[i-1].h);
                    i--;
                }else{
                    return i;
                }
            }
            return 0; 
        }else{ // 오른쪽 도미노 넘어뜨리기
            long preRange = pair[i].x + pair[i].h;
            while(i < N-1){
                if(pair[i+1].x <= preRange){ // 오른쪽에 있는 도미노를 넘어뜨릴 수 있다면
                    //System.out.printf("%d -> %d\n", i, i+1);
                    preRange = Math.max(preRange, pair[i+1].x + pair[i+1].h);
                    i++;
                }else{
                    return i;
                }
            }
            return N-1;
        }
    }
}
