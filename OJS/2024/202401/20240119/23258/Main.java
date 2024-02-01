import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair>{
    int x; 
    long cost;
    int food;

    public Pair(int x, long cost){
        this.x = x;
        this.cost = cost; 
    }
    public Pair(int x, long cost, int food){
        this.x = x;
        this.cost = cost; 
        this.food = food;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.cost > o.cost){
            return 1;
        }else if(this.cost < o.cost){
            return -1;
        }
        return 0;
    }
    

}

public class Main {
    static int N, Q;
    static int MAX = 170324 * 300 + 1 ;
    static int[][][] dist;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        dist = new int[N+1][N+1][N+1]; // dist[i][j][k] -> i -> j 까지 가는 도중 k만큼의 이슬을 먹은 최단 거리
        
        for(int i=1;i<=N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=N;++j){
                int cur = Integer.parseInt(st.nextToken());
                if(cur == 0){
                    if(i != j) {
                        dist[i][j][0] = MAX; // i!=j인데 입력값이 0이라면 끊어진 길
                    }
                }else{ 
                    dist[i][j][0] = cur;
                }
                // j -> j 까지 가는 경로는 모두 0
                dist[i][j][j] = 0;       
            }
        }
        // 핵심 : 2^1 + 2^2 + 2^3 + ... + 2^(c-1) < 2^c -> c - 1 이하의 이슬은 여러번 먹어도 아무 상관이 없다.
        // k 만큼의 이슬을 먹으면 잠들때, k-1 이하의 이슬들을 먹고 갈 수 있는 최단 거리를 구하면 된다.
        for(int k = 1;k<=N;++k){
            for(int i = 1;i<=N;++i){
                for(int j = 1;j<=N;++j){
                    // 
                    dist[i][j][k] = Math.min(dist[i][j][k-1], dist[i][k][k-1] + dist[k][j][k-1]); 
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for(int q = 0; q < Q;++q){
            st = new StringTokenizer(br.readLine());
            int C = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            // C - 1 만큼의 이슬을 먹어야지  s->e 이동 가능
            if(dist[s][e][C-1] == MAX) sb.append(-1).append('\n');
            else sb.append(dist[s][e][C-1]).append('\n');
        }
        System.out.print(sb.toString());
    }
}
