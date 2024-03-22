import java.io.*;
import java.util.*;
public class Main {
    static int[][] map;
    static int[] dx = {1,0,-1,0}
    static int[] dy = {0,1,0,-1};
    static int n;
    public static void main(String[] args) throws IOException {
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        map = new int[n+1][n+1];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        boolean[][] visited = new boolean[n+1][n+1]; 
        int ans = Integer.MAX_VALUE;
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(!visited[i][j]){
                    int size = bfs(i,j,visited);
                    ans = Math.min(size,ans);
                }
            }
        }
        System.out.println(ans);
    }
    static int bfs(int x, int y, boolean[][] visited){
        int size = 0;
        visited[x][y] = true;
        Queue<Point> que = new ArrayDeque<>();
        que.add(new Point(x, y));
        while(!que.isEmpty()){
            Point cur = que.poll();
            size++;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                System.out.printf("%d, %d\n",nx, ny );
                System.out.println(checkRange(nx,ny ));
                if(checkRange(nx,ny)){
                    if(!visited[nx][ny] && map[cur.x][cur.y] == map[nx][ny]){
                        visited[nx][ny] = true;
                        que.add(new Point(nx,ny));
                    }
                }
            }
        }
        return size;
    }
    static boolean checkRange(int x, int y){
        return(x>=0) && (y>=0) && (x<n) && (y<n);
    }
}
class Point{
    int x; 
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}