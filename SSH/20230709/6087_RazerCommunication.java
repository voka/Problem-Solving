package MinimumSpanningTree;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
public class _6087_RazerCommunication {
    static int w,h;
    static int minMirror;
    // →:0, ↓:1, ←:2, ↑:3
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    static int[][] razer = new int[2][2];
    static char[][] map;
    static int[][] directions;
    static int[][] mirrors;
    static int[][] visited;
    static Queue<int[]> queue = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        minMirror = w*h;

        map = new char[h+1][w+1];
        directions = new int[h+1][w+1];
        visited = new int[h+1][w+1];
        mirrors = new int[h+1][w+1];

        int idx = 0;
        for(int i=1;i<=h;i++){
            String tmp = br.readLine();
            for(int j=1;j<=w;j++){
                map[i][j] = tmp.charAt(j-1);
                if(map[i][j] == 'C'){
                    razer[idx][0] = i;  // razer의 row 좌표
                    razer[idx][1] = j;  // razer의 col 좌표
                    idx++;
                }
            }
        }
        for(int i=0;i<4;i++){
            int start_y = razer[0][0]+dy[i];
            int start_x = razer[0][1]+dx[i];
            if(start_x >=1 && start_y >= 1 && start_x <=w && start_y <=h){
                if(map[start_y][start_x] != '*'){
                    directions = new int[h+1][w+1];
                    visited = new int[h+1][w+1];
                    mirrors = new int[h+1][w+1];
                    directions[razer[0][0]][razer[0][1]] = i;
                    bfs();
                }
            }

        }

        System.out.println(minMirror);

    }
    public static void bfs(){
        queue.add(new int[]{razer[0][0], razer[0][1]});
        visited[razer[0][0]][razer[0][1]] = 1;

        while(!queue.isEmpty()){
            int[] tmp = queue.poll();
            int cy = tmp[0];
            int cx = tmp[1];
            for(int i=0; i<4;i++){
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                int mcount = (directions[cy][cx] == i) ? 0: 1;
                if(nx >=1 && ny >=1 && nx <= w && ny <= h){

                    if(mirrors[ny][nx] > mirrors[cy][cx]+mcount)
                        visited[ny][nx] =0;



                    if(visited[ny][nx] == 1)
                        continue;

                    if(map[ny][nx] == '*') {
                        visited[ny][nx] = 1;
                        continue;
                    }

                    directions[ny][nx] =i;
                    mirrors[ny][nx] = mirrors[cy][cx] + mcount;
                    queue.add(new int[]{ny,nx});
                    visited[ny][nx] = 1;

                    if(map[ny][nx] == 'C'){
                        minMirror = Math.min(minMirror,mirrors[ny][nx]);
                    }
                }
            }
        }

    }
}
