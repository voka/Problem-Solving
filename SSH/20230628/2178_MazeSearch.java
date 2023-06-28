package Graph;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
public class _2178_MazeSearch {
    static int n,m;
    static int[] dx={1,0,0,-1};
    static int[] dy={0,1,-1,0};
    static int[][] map;
    static int[][] count;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n+1][m+1];
        count = new int[n+1][m+1];
        for(int i=1;i<=n;i++){
            String str = br.readLine();
            for(int j=1;j<=m;j++){
                map[i][j] = str.charAt(j-1) - '0';
            }
        }
        count[1][1] = 1;
        bfs();
        System.out.println(count[n][m]);

    }
    public static void bfs(){
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{1,1});
        while(!queue.isEmpty()){
            int[] t = queue.poll();
            int row = t[0];
            int col = t[1];
            for(int i=0;i<4;i++){
                int nx = col + dx[i];
                int ny = row + dy[i];
                if(ny >=1 && nx >= 1 && ny<=n && nx<=m) {
                    if (map[ny][nx] == 1 && count[ny][nx] == 0) {
                        count[ny][nx] = count[row][col] + 1;
                        queue.add(new int[]{ny, nx});
                    }
                }
            }
        }
    }
}
