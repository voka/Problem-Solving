import java.io.*;
import java.util.*;
public class P9376 {
    static int H,W,h1x,h1y,h2x,h2y;
    static int _MAX = 10001;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static int[][] map, dp1, dp2, dp0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for(int tc=0;tc<T;++tc) {
            h1x = -1;
            h2x = -1;
            st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            map = new int[H+2][W+2];
            dp0 = new int[H+2][W+2];
            dp1 = new int[H+2][W+2];
            dp2 = new int[H+2][W+2];
            for(int i=1;i<=H;++i) {
                String s = br.readLine().trim();
                for(int j=1;j<=W;++j) {
                    if(s.charAt(j-1) == '*') {
                        map[i][j] = -1;
                    }else if(s.charAt(j-1) == '#') {
                        map[i][j] = 1;
                    }
                    else if(s.charAt(j-1) == '$') {
                        if(h1x == -1) {
                            h1x = i;
                            h1y = j;
                        }else {
                            h2x = i;
                            h2y = j;
                        }
                    }

                }
            }
            for(int i=0;i<H+2;++i){

                Arrays.fill(dp0[i], _MAX);
                Arrays.fill(dp1[i], _MAX);
                Arrays.fill(dp2[i], _MAX);
            }
            bfs(dp0,0,0);
            bfs(dp1,h1x,h1y);
            bfs(dp2,h2x,h2y);
            int answer = _MAX;
            
            for(int i=1;i<=H;++i){
                for(int j=1;j<=W;++j){
                    if(map[i][j] == -1) continue; 
                    if(dp0[i][j] == _MAX || dp1[i][j] == _MAX || dp2[i][j] == _MAX) continue;
                    int next = dp0[i][j] + dp1[i][j] + dp2[i][j];
                    if(map[i][j] == 1) next -= 2;
                    answer = Math.min(answer,next);
                }
            }

            sb.append(answer).append('\n');

        }
        System.out.println(sb.toString());


    }
    static void bfs(int[][] dp, int sx, int sy) {
        int val = sx*(W+2) + sy;
        Deque<Integer> q = new ArrayDeque<>();
        dp[sx][sy] = 0;
        q.add(val);
        while(!q.isEmpty()) {
            int v = q.pollFirst();
            int x = v / (W+2);
            int y = v % (W+2);
            for(int i=0;i<4;++i) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if(!checkRange(nx,ny)) continue;
                if(map[nx][ny] == -1) continue;
                if(map[nx][ny] == 1) {
                    if(dp[nx][ny] > dp[x][y] + 1) {
                        dp[nx][ny] = dp[x][y] + 1;
                        q.addLast(nx*(W+2) + ny);
                    }
                }else {
                    if(dp[nx][ny] > dp[x][y]) {
                        dp[nx][ny] = dp[x][y];
                        q.addFirst(nx*(W+2) + ny);
                    }

                }
            }
        }
    }
    static boolean checkRange(int x, int y) {
        return (x>=0) && (y>=0) && (x<H+2) && (y<W+2);
    }
}
