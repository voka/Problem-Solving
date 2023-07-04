package DisJointSet;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
public class _3197_SwanLake {
    static int R, C;
    static int[][] swan;
    static int idx, Swan1, Swan2;
    static int[] dx = {1, 0, 0, -1};
    static int[] dy = {0, -1, 1, 0};
    static int[][] visited;
    static int[][] count;
    static int[] parent;
    static char[][] lake;
    static Queue<int[]> queue = new LinkedList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        lake = new char[R+1][C+1];
        parent = new int[R*C+1];
        visited = new int[R+1][C+1];
        count=new int [R+1][C+1];
        swan = new int[2][2];
        for(int i=1;i<=R*C;i++){
            parent[i] = i;
        }
        for(int i=1;i<=R;i++){
            String tmp = br.readLine();
            for(int j=1;j<=C;j++){
                char t = tmp.charAt(j-1);
                lake[i][j] = t;
                if(t == '.'){

                    queue.add(new int[]{i,j});

                }
                else if(t == 'L'){
                    queue.add(new int[]{i,j});
                    swan[idx][0] = i;
                    swan[idx][1] = j;
                    idx++;
                }
            }
        }
        Swan1 =(swan[0][0]-1)*C + swan[0][1];
        Swan2 =(swan[1][0]-1)*C + swan[1][1];

        bfs();

        
    }
    public static void bfs(){
        while(!queue.isEmpty()){
            int[] t = queue.poll();
            int row = t[0];
            int col = t[1];

            visited[row][col] = 0;
            for(int i=0;i<4;i++){
                int nrow = row + dy[i];
                int ncol = col + dx[i];
                if( nrow >=1 && ncol >=1 && nrow <=R && ncol <=C){
                    if(visited[nrow][ncol] == 0){
                        if(lake[nrow][ncol] =='X'){
                            queue.add(new int[]{nrow, ncol});
                            count[nrow][ncol] = count[row][col] + 1;
                            lake[nrow][ncol] = '.';
                            visited[nrow][ncol] = 1;
                        }
                        else{
                            union( (row-1)*C + col,(nrow-1)*C + ncol);
                            if(find(Swan1) == find(Swan2)){
                                System.out.println(count[row][col]);
                                return;
                            }
                        }
                    }

                }
            }
        }
    }
    public static int find(int x){
        if(parent[x] == x)
            return x;
        else{
            return parent[x] = find(parent[x]);
        }
    }
    public static void union(int x, int y){
        x = find(x);
        y = find(y);

        if( x != y){
            if(x < y) parent[y] = x;
            else parent[x] = y;
        }
    }
}
