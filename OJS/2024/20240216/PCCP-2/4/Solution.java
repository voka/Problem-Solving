import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
class Triple{
    int x; 
    int y; 
    boolean use;
    
    public Triple(int x, int y, boolean use){
        this.x = x;
        this.y = y;
        this.use = use;
    }
}
class Solution {
    static int N,M;
    static int[] dx = {0,1,0,-1}, dy = {1,0,-1,0};
    static int[][] dp,board;
    public int solution(int n, int m, int[][] hole) {
        N = n;
        M = m;
        if(n == 1 && m == 1) return 1;
        boolean[][][] visited = new boolean[m][n][2];
        int[][][] dp = new int[m][n][2];
        board = new int[m][n];
        for(int i=0;i<hole.length;++i){
            int x = hole[i][0]-1;
            int y = hole[i][1]-1;
            board[y][x] = -1;
        }
        for(int i=0;i<m;++i){
            for(int j=0;j<n;++j){
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);   
            }
        }
        //printArray(board);
        visited[0][0][0] = true;
        dp[0][0][0] = 0;
        Queue<Triple> que = new LinkedList<>();
        que.add(new Triple(0,0,false));
        while(!que.isEmpty()){
            Triple cur = que.poll();
            int x = cur.x;
            int y = cur.y;
            int use = (cur.use) ? 1 : 0;
            for(int i=0;i<4;++i){
                int nx = x + dx[i];
                int ny = y + dy[i];
                if(checkRange(nx, ny) && !visited[nx][ny][use]){
                    if(board[nx][ny] != -1){
                        visited[nx][ny][use] = true;
                        dp[nx][ny][use] = Math.min(dp[nx][ny][use], dp[x][y][use] + 1);
                        que.add(new Triple(nx, ny, cur.use));
                    }
                    if(!cur.use){//마법의 신발 사용할 수 있다면
                        int nnx = nx + dx[i];
                        int nny = ny + dy[i];
                        if(checkRange(nnx,nny) && board[nnx][nny] != -1 && !visited[nnx][nny][1]){
                            //System.out.printf("%d, %d => %d, %d\n", nx, ny, nnx, nny );
                            dp[nnx][nny][1] = Math.min(dp[nnx][nny][1], dp[x][y][0] + 1);
                            visited[nnx][nny][1] = true;
                            que.add(new Triple(nnx, nny, !cur.use));
                        }
                    }
    
                }
            }
        }
        //printArray(dp);
        int answer = (dp[M-1][N-1][1] == Integer.MAX_VALUE) ? -1 : dp[M-1][N-1][1];
        return answer;
    }
    public boolean checkRange(int x, int y){
        return (x >= 0) && (y>=0) && (x < M) && (y <N);
    }
    public void printArray(int[][] map){
        for(int i=0;i<M;++i){
            for(int j=0;j<N;++j){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
}