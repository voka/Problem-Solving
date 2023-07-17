package MinimumSpanningTree;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Comparator;
public class _17472_MakeBridge {
    static int n, m, islandNum=1;
    static int[] dx = {1, 0, 0, -1};
    static int[] dy = {0, 1, -1, 0};
    static int[] root;

    static int[][] map, visited;

    static PriorityQueue<int[]> pqueue = new PriorityQueue<>(new Comparator<int[]>(){
        @Override
        public int compare(int[] o1, int[] o2){return o1[2] - o2[2];}
    });


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n+1][m+1];
        visited = new int[n+1][m+1];

        for(int i=1;i<=n;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=m;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(map[i][j] == 1 && visited[i][j] == 0){
                    bfs(new int[]{i,j});
                    islandNum++;
                }
            }
        }

        root = new int[islandNum];
        for(int i=0;i<islandNum;i++){
            root[i]=i;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(map[i][j] != 0)
                    find_bridge(new int[]{i,j});
            }
        }
        int answer = 0;

        while(!pqueue.isEmpty()){
            int[] tmp = pqueue.poll();
            if(union(tmp[0],tmp[1])){
                answer += tmp[2];
            }

        }
        int count = 0;
        for(int i=1;i<islandNum;i++){
            if(find(i) == 1) count++;
        }
        if( count == islandNum-1)
            System.out.println(answer);
        else
            System.out.println(-1);


    }
    public static int find(int x){
        if(root[x] == x)
            return x;
        else
            return find(root[x]);
    }

    public static boolean union(int x, int y){
        x = find(x);
        y = find(y);

        if( x != y){
            if( x < y) root[y] = x;
            else root[x] = y;

            return true;
        }
        return false;
    }
    public static void find_bridge(int[] start){
        int cIslandNumber = map[start[0]][start[1]];

        for(int i=0;i<4;i++){
            int cy = start[0] + dy[i];
            int cx = start[1] + dx[i];
            int count=0;
            while(cy >=1 && cx >= 1 && cy <= n && cx <= m) {
                if(map[cy][cx] == cIslandNumber)
                    break;

                if(map[cy][cx] != 0){
                    if(count >=2){
                        pqueue.add(new int[]{cIslandNumber,map[cy][cx],count});
                    }
                    break;
                }
                count++;
                cy += dy[i];
                cx += dx[i];
            }
        }
    }
    public static void bfs(int[] start){
        Stack<int[]> stack = new Stack<>();
        stack.push(start);
        map[start[0]][start[1]]=islandNum;
        while(!stack.isEmpty()){
            int[] tmp = stack.pop();
            int cy = tmp[0];
            int cx = tmp[1];
            if(visited[cy][cx] == 1) continue;
            visited[cy][cx] = 1;
            for(int i=0;i<4;i++){
                int ny = cy+dy[i];
                int nx = cx+dx[i];
                if(nx >= 1 && ny >= 1 && nx <= m && ny <= n){
                    if(map[ny][nx] == 1 && visited[ny][nx] == 0){
                        stack.push(new int[]{ny, nx});
                        map[ny][nx] = islandNum;
                    }
                }
            }
        }
    }
}
