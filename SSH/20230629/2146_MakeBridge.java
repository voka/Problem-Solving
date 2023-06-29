package Graph;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
public class _2146_MakeBridge {
    static int n, distance;
    static int groupNum = -1;
    static int[] dx = {1, 0, 0, -1};
    static int[] dy = {0, 1, -1, 0};
    static int[][] map;
    static int[][] count;
    static int[][] check;
    static Queue<int[]> queue = new LinkedList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        count = new int[n][n];
        check = new int[n][n];
        distance = n*2;
        for(int i=0;i<n;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;j++){
                int tmp = Integer.parseInt(st.nextToken());
                map[i][j] = tmp;
            }
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(map[i][j] == 1){
                    if(check[i][j] == 0){
                        check[i][j] = 1;
                        map[i][j] = groupNum;
                        groupIsland(i,j);
                        groupNum -=1;
                    }
                }
            }
        }

        check = new int[n][n];


        for(int i=-1;i>groupNum;i--){
            bfs(i);
            check = new int[n][n];
            count = new int[n][n];

        }


        System.out.println(distance);

    }
    public static void groupIsland(int row, int col){
        Queue<int[]> queueIsland = new LinkedList<>();
        queueIsland.add(new int[]{row, col});
        while(!queueIsland.isEmpty()){
            int[] t = queueIsland.poll();
            int y = t[0];
            int x = t[1];

            for(int i=0;i<4;i++){
                int ny = y + dy[i];
                int nx = x + dx[i];
                if(ny>=0 && nx >=0 && ny<n && nx<n){
                    if(map[ny][nx] != 0 && check[ny][nx] == 0){
                        queueIsland.add(new int[]{ny,nx});
                        check[ny][nx] = 1;
                        map[ny][nx] = groupNum;
                    }
                }
            }
        }
    }
    public static void bfs(int gNum){
        Queue<int[]> iQueue = new LinkedList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(map[i][j] == gNum){
                    iQueue.add(new int[]{i,j});
                    check[i][j] = 1;
                }
            }
        }
        while(!iQueue.isEmpty()){
            int[] t = iQueue.poll();
            int row = t[0];
            int col = t[1];
            if(count[row][col] > distance)
                continue;
            for(int i=0;i<4;i++){
                int ny = row + dy[i];
                int nx = col + dx[i];
                if(ny>=0 && nx >= 0 && ny<n && nx <n){
                    if(check[ny][nx] == 0){
                        if(map[ny][nx] !=0 ){
                            distance = Math.min(count[row][col], distance);
                            continue;
                        }

                        count[ny][nx] = count[row][col] + 1;
                        check[ny][nx] = 1;
                        iQueue.add(new int[]{ny,nx});
                    }
                }
            }
        }

    }
}
