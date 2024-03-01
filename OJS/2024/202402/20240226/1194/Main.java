import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
class Pair{
    int x;
    int y;
    int key;
    int cnt;
    public Pair(int x, int y, int key, int cnt){
        this.x = x;
        this.y = y;
        this.key = key;
        this.cnt = cnt;
    }
}
public class Main {
    static int N, M, sx, sy, eCount = 0;
    static int[][] map;
    static int[][][] dp;
    static int[][] keyMap;
    static boolean[][] visited;
    static int[] dx = {1,0,-1,0}, dy = {0,-1,0,1};
    static int[] ex, ey;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int maxKey = 1<<6;
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        dp = new int[N][M][maxKey + 1]; // 111111 -> 키가 6개 다 있다는 소리
        map = new int[N][M];
        keyMap = new int[N][M];
        visited = new boolean[N][M];
        ex = new int[N * M];
        ey = new int[N * M];
        for(int i=0;i<N;++i){
            String s = br.readLine();
            for(int j=0;j<M;++j){
                char cur = s.charAt(j);
                if(cur == '#'){
                    map[i][j] = -1;
                }else if(cur == '0'){
                    sx = i;
                    sy = j;
                    map[i][j] = 10;
                }else if((cur - 'a') >= 0 && (cur - 'a') <= 6){
                    int k = (cur - 'a') + 1;
                    keyMap[i][j] = k;
                }else if((cur - 'A') >= 0 && (cur - 'A') <= 6){
                    int door = (cur - 'A') + 1;
                    map[i][j] = door;
                }else if(cur == '1'){
                    map[i][j] = 11;
                    ex[eCount] = i;
                    ey[eCount] = j;
                    eCount++;
                    
                }
                for(int k=0;k<=maxKey;++k){
                    dp[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }
        //printMap();
        // System.out.println((1<<7) - 1);
        // System.out.println(((1<<7) - 1) & 1<<4);
        // System.out.println((1<<6 | 1 << 5));
        // int keyValue = (1<<6) | (1<<5) | (1<<2);
        // System.out.println((keyValue & 1<<1) > 0);

        //bfs
        dp[sx][sy][0] = 0;
        Queue<Pair> que = new LinkedList<>();
        que.add(new Pair(sx, sy, 0, 0));
        while(!que.isEmpty()){
            Pair cur = que.poll();
            //if(dp[cur.x][cur.y][cur.key] < cur.cnt) continue;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny) && map[nx][ny] != -1){
                    //key가 있는 경우
                    if(keyMap[nx][ny] != 0){
                        int key = keyMap[nx][ny];
                        int binaryKey = 1<<(key-1);
                        int nextKey = binaryKey | cur.key;
                        if(dp[nx][ny][nextKey] > cur.cnt + 1){
                            dp[nx][ny][nextKey] = cur.cnt + 1;
                            que.add(new Pair(nx, ny, nextKey, cur.cnt + 1));
                        }   
                        // dp[nx][ny][nextKey] = Math.min(dp[nx][ny][nextKey],cur.cnt + 1);
                        // que.add(new Pair(nx, ny, nextKey, cur.cnt + 1));
                    }
                    //문이 있는 경우
                    else if(map[nx][ny] >= 1 && map[nx][ny] <= 7){
                        int binaryDoor = 1 << (map[nx][ny] - 1);
                        if((binaryDoor & cur.key) != 0){// 문에 맞는 열쇠를 가지고 있다면
                            if(dp[nx][ny][cur.key] > cur.cnt + 1){
                                dp[nx][ny][cur.key] = cur.cnt + 1;
                                que.add(new Pair(nx, ny, cur.key, cur.cnt + 1));
                            }   
                            // dp[nx][ny][cur.key] = Math.min(dp[nx][ny][cur.key], cur.cnt + 1);
                            // que.add(new Pair(nx, ny, cur.key, cur.cnt + 1));
                        }
                    }
                    //빈 칸이거나 출구인 경우
                    else{
                        if(dp[nx][ny][cur.key] > cur.cnt + 1){
                            dp[nx][ny][cur.key] = cur.cnt + 1;
                            que.add(new Pair(nx, ny, cur.key, cur.cnt + 1));
                        }   
                        // dp[nx][ny][cur.key] = Math.min(dp[nx][ny][cur.key], cur.cnt + 1);
                        // que.add(new Pair(nx, ny, cur.key, cur.cnt + 1));
                    }
                }
            }

        }
        int ans = Integer.MAX_VALUE;
        for(int i=0;i<eCount;++i){
            for(int j=0;j<=maxKey;++j){
                ans = Math.min(ans,dp[ex[i]][ey[i]][j]);
            }
        }
        if(ans != Integer.MAX_VALUE) System.out.println(ans);
        else System.out.println(-1);

    }   
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x < N) && (y < M);
    }
    static void printMap(){
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    } 
}
