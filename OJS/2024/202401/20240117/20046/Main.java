import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair>{
    int x;
    int y;
    int cost;
    public Pair(int x, int y, int cost){
        this.x = x;
        this.y = y;
        this.cost = cost;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.cost > o.cost){
            return 1;
        }
        else if(this.cost < o.cost){
            return -1; 
        }
        return 0;
    }
    
}
public class Main {
    static int m,n;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static int[][] board, tCost;
    static boolean checkRange(int x, int y){
        return (x>=0) && (y >=0)  && (x < m) && (y < n);
    }

    static void bfs(int x, int y){
        tCost[y][x] = board[y][x];


        PriorityQueue<Pair> queue = new PriorityQueue<>(); // cost가 가장 낮은 부분부터 탐색하도록 해야함.
        queue.add(new Pair(x, y, tCost[0][0]));
        while(!queue.isEmpty()){
            Pair cur = queue.poll();
            int c = cur.cost;
            if(tCost[cur.y][cur.x] < c) continue;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny) && board[ny][nx] != -1){
                    int nc = (board[ny][nx] + c);
                    if(tCost[ny][nx] > nc){
                        tCost[ny][nx] = nc;
                        queue.add(new Pair(nx, ny, tCost[ny][nx]));
                    }
                }
            }
        }

    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new int[n][m];
        tCost = new int[n][m];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<m;++j){
                board[i][j] = Integer.parseInt(st.nextToken());
                tCost[i][j] = Integer.MAX_VALUE;
            }
        }
        if(board[0][0] == -1 || board[n-1][m-1] == -1){
            System.out.println(-1);
            return;
        } 
        bfs(0,0);
        if(tCost[n-1][m-1] == Integer.MAX_VALUE){
            System.out.println(-1);
        }else{
            System.out.println(tCost[n-1][m-1]);
        }

    }
}
