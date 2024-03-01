import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair{
    int a;
    int b;
    public Pair(int a, int b){
        this.a = a;
        this.b = b;
    }
    
}
public class Main {
    static int N, T;
    static int[][] dist;
    static long[][] dp;
    static Pair[] place;
    static PriorityQueue<Pair> pq = new PriorityQueue<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        place = new Pair[N];
        dist = new int[N][N];
        dp = new long[T+1][N+1];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            place[i] = new Pair(c, e);
            pq.add(new Pair(c, e));
        }
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                dist[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //Arrays.sort(place);
        for(int t=1;t<=T;++t){
            for(int i=0;i<N;++i){
                if(dp[t-1][i] >= place[i].a){ // 기존 사냥터에서 계속 사냥
                    dp[t][i] = Math.max(dp[t-1][i] + place[i].b, dp[t][i]);
                }
                for(int j=0;j<N;++j){ // 새로운 사냥터로 이동하기
                    int pre = t - dist[j][i]; // 이전에 특정 장소에서 올 수 있는 시간
                    if(i == j || pre < 0) continue; // 그 시간이 0보다 작거나 같은 공간이면 다시 반복
                    if(dp[pre][j] >= place[j].a){ // 이전 장소에서 얻은 경험치가 현재 사냥터의 기준 경험치 보다 높다면
                        dp[t][i] = Math.max(dp[pre][j], dp[t][i]); // j -> i 사냥터로 옮겨서 얻는 경험치와 이때까지 얻은 경험치를 비교해 더 큰것을 저장한다. 
                    } 
                }
            }
        }
        long ans = 0;
        // for(int t=1;t<=T;++t){
        //     for(int i=0;i<N;++i){
        //         System.out.printf("%d ",dp[t][i]);
        //     }
        //     System.out.println();
        // }
        for(int i=0;i<N;++i){ // 새로운 사냥터로 이동하기
            ans = Math.max(ans,dp[T][i]);
        }
        System.out.println(ans);
    }
}
