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
    public int compareTo(Pair o){
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
    static int N,M, x1,y1,x2,y2;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static int[][] board, dist;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        x1 = Integer.parseInt(st.nextToken())-1;
        y1 = Integer.parseInt(st.nextToken())-1;
        x2 = Integer.parseInt(st.nextToken())-1;
        y2 = Integer.parseInt(st.nextToken())-1;
        board = new int[N][M];
        dist = new int[N][M];
        for(int i=0;i<N;++i){
            String temp = br.readLine();
            for(int j=0;j<M;++j){
                if(temp.charAt(j) == '1'){
                    board[i][j] = 1;
                }else{
                    board[i][j] = 0;
                }
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        findShortestPath();
        System.out.println(dist[x2][y2] + 1);


    }
    static boolean checkRange(int x, int y){
        return (x >= 0) && (y>= 0) && (x<N) && (y <M);
    }
    static void findShortestPath(){
        dist[x1][y1] = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>(); 
        pq.add(new Pair(x1, y1, 0));
        while(!pq.isEmpty()){
            Pair cur = pq.poll();
            if(dist[cur.x][cur.y] < cur.cost) continue;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny) && dist[nx][ny] > cur.cost + board[nx][ny]){
                    dist[nx][ny] = cur.cost + board[nx][ny];
                    pq.add(new Pair(nx, ny, dist[nx][ny]));
                }
            }
        }
    }
}
