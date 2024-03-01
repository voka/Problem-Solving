import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
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
    static int N,M; 
    static int[] parents;
    static int[][][] map; 
    static int[][] check;
    static int cnt = 0;
    static List<Set<Integer>> next = new ArrayList<>();
    static List<Integer> score = new ArrayList<>();
    static int[] dx = {0,-1,0,1}, dy = {-1,0,1,0};// 진행방향 : 서(왼), 북(위), 동(오른), 남(아래))
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        parents = new int[M*N + 1];
        map = new int[M][N][4];
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                int cur = Integer.parseInt(st.nextToken());
                if((cur & 1) != 0){//서쪽
                    map[i][j][0] = 1;
                }
                if((cur & 2) != 0){//북쪽
                    map[i][j][1] = 1;
                }
                if((cur & 4) != 0){//동쪽
                    map[i][j][2] = 1; 
                }
                if((cur & 8) != 0){//남쪽
                    map[i][j][3] = 1;
                }
            }
        }
        check = new int[M][N];
        boolean[][] visited = new boolean[M][N];
        for(int i=0;i<M;++i){
            for(int j=0;j<N;++j){
                if(!visited[i][j]){
                    visited[i][j] = true;
                    next.add(new HashSet<>());
                    check[i][j] = cnt;
                    bfs(new Pair(i, j), visited);
                    cnt++;
                }
            }
        }
        // for(int i=0;i<M;++i){
        //     for(int j=0;j<N;++j){
        //         System.out.printf("%d ", check[i][j]);
        //     }
        //     System.out.println();
        // }
        int countRoom = cnt;
        int maxRoomSize = Integer.MIN_VALUE;
        for(int i=0;i<countRoom;++i){
            maxRoomSize = Math.max(maxRoomSize, score.get(i));
        }
        System.out.println(countRoom);
        System.out.println(maxRoomSize);


        //벽 하나를 제거했을때 방 최대 크기 구하기
        int maxSumRoomSize = Integer.MIN_VALUE;
        visited = new boolean[M][N];
        for(int i=0;i<M;++i){
            for(int j=0;j<N;++j){
                if(!visited[i][j]){
                    visited[i][j] = true;
                    makeNextList(new Pair(i, j), visited);
                }
            }
        }
        for(int i=0;i<countRoom;++i){
            for(int n : next.get(i)){
                //System.out.printf("%d, %d, %d\n", i, n, score.get(i) + score.get(n));
                maxSumRoomSize = Math.max(maxSumRoomSize, score.get(i) + score.get(n));
            }
        }
        System.out.println(maxSumRoomSize);


    }
    static void makeNextList(Pair start, boolean[][] visited){
        Queue<Pair> que = new LinkedList<>();
        que.add(start);
        while(!que.isEmpty()){
            Pair cur = que.poll();
            int curIndex = cur.x * N + cur.y;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                int nextIndex = nx * N + ny;
                if(checkRange(nx, ny) && !visited[nx][ny]){// 가고자 하는 방향에 벽이 없는 경우 진행 가능
                    if(map[cur.x][cur.y][i] != 1){
                        visited[nx][ny]=true;
                        que.add(new Pair(nx, ny));
                    }else{
                        if(parents[curIndex] != parents[nextIndex])
                            next.get(parents[curIndex]).add(parents[nextIndex]); // 옆집 추가
                    }
                }
            }
        }
    }

    static void bfs(Pair start, boolean[][] visited){
        int count = 0;
        Queue<Pair> que = new LinkedList<>();
        que.add(start);
        while(!que.isEmpty()){
            Pair cur = que.poll();
            int curIndex = cur.x * N + cur.y;
            parents[curIndex] = cnt;
            count++;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny) && !visited[nx][ny]){// 가고자 하는 방향에 벽이 없는 경우 진행 가능
                    if(map[cur.x][cur.y][i] != 1){
                        check[nx][ny] = cnt;
                        visited[nx][ny]=true;
                        que.add(new Pair(nx, ny));
                    }
                }
            }
        }
        score.add(count);// 방 크기 저장
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<M) && (y<N); 
    }
}
