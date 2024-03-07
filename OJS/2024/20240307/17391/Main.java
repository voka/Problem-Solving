import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair{
    int x; 
    int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int N,M, _MAX = Integer.MAX_VALUE;
    static int[] dx = {1,0}, dy = {0,1};
    static int[][] map, dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        dp = new int[N][M];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = _MAX;
            }
        }
        dp[0][0] = 0;
        boolean[][] visited = new boolean[N][M];
        visited[0][0] = true;
        Queue<Pair> que = new LinkedList<>();
        que.add(new Pair(0, 0));
        while(!que.isEmpty()){
            Pair cur = que.poll();
            for(int i=0;i<2;++i){
                for(int j=1;j<=map[cur.x][cur.y];++j){
                    int nx = cur.x + dx[i] * j;
                    int ny = cur.y + dy[i] * j;
                    if(checkRange(nx, ny) && !visited[nx][ny]){
                        visited[nx][ny] = true;
                        dp[nx][ny] = Math.min(dp[nx][ny], dp[cur.x][cur.y] + 1);
                        que.add(new Pair(nx, ny));
                    }
                }
            }
        }
        System.out.println(dp[N-1][M-1]);
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<N) && (y<M);
    }
    static boolean isSame(int x, int y, int a, int b){
        return (x==a) && (y==b);
    }
}
