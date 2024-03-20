import java.io.*;
import java.util.*;
class Point{
    int x; 
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int N;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static int[][] map;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        map = new int[N][N];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                map[i][j] = Integer.parseInt(st.nextToken()); 
            }
        }
        boolean[][] check = new boolean[N][N];
        List<Integer> ans = new ArrayList<>();
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                if(map[i][j] == 1 && !check[i][j]){
                    int size = bfs(i,j,check);
                    ans.add(size);
                }
            }
        }
        Collections.sort(ans);
        StringBuilder sb = new StringBuilder();
        sb.append(ans.size()).append('\n');
        for(int s : ans){
            sb.append(s).append('\n');
        }
        System.out.println(sb.toString());
    }
    static int bfs(int x, int y, boolean[][] visited){
        int cnt = 1;
        visited[x][y] = true;
        Queue<Point> que = new ArrayDeque<>();
        que.add(new Point(x, y));
        while(!que.isEmpty()){
            Point cur = que.poll();
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx,ny) && map[nx][ny] == 1 && !visited[nx][ny]){
                    visited[nx][ny] = true;
                    que.add(new Point(nx,ny));
                    cnt++;
                } 
            }
        }
        return cnt;
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<N) && (y<N);
    }
}