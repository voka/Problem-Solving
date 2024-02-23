import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n,m;
    static int diceX = 0, diceY = 0, diceDir = 1;
    static int dice = 1, diceDown = 2, diceRight = 3, diceLeft = 4, diceUp = 5, diceBack = 6;
    static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
    static int[] parents;
    static int[] score;
    static int[][] map;
    static boolean[][] visited;
    static int findP(int x){
        if(parents[x] != x){
            parents[x] = findP(parents[x]) ;
        }
        return parents[x];
    }
    static void unionP(int a, int b){
        a = parents[a];
        b = parents[b];
        if( a < b){
            parents[b] = a;
        }else{
            parents[a] = b;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        visited = new boolean[n][n];
        map = new int[n][n];
        score = new int[n*n];
        parents = new int[n*n];
        for(int i=0;i<n*n;++i){
            parents[i] = i;
        }
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 각 칸의 점수 계산
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(!visited[i][j]){
                    bfs(i, j);
                }
            }
        }
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                findP(changeOne(i, j));// 부모 업데이트
                //System.out.printf("%d ", score[parents[changeOne(i, j)]]);
                //System.out.printf("%b ",visited[i][j]);
                //System.out.printf("%d ", parents[changeOne(i, j)]);
            }
        }
        //System.out.printf("%d, %d\n",dice, diceDir );
        int answer = 0;
        for(int i=0;i<m;++i){
            int nx = diceX + dx[diceDir];
            int ny = diceY + dy[diceDir];
            //System.out.printf("%d, %d\n", nx,ny);
            if(!checkRange(nx, ny)){ // 반대 방향으로 바꾸기
                diceDir = (diceDir % 2 == 0) ? 2 - diceDir : 4 - diceDir; 
                nx = diceX + dx[diceDir];
                ny = diceY + dy[diceDir];
            }
            //System.out.printf("%d, %d\n", nx,ny);
            //주사위 굴리기
            if(diceDir == 1){ // 오른쪽 굴리기
                int temp = dice;
                dice = diceLeft;
                diceLeft = diceBack;
                diceBack = diceRight;
                diceRight = temp;
            }else if(diceDir == 0){ // 위쪽으로 굴리기
                int temp = dice;
                dice = diceDown;
                diceDown = diceBack;
                diceBack = diceUp;
                diceUp = temp;
            }else if(diceDir == 2){ // 아래쪽으로 굴리기
                int temp = dice; 
                dice = diceUp;
                diceUp = diceBack;
                diceBack = diceDown;
                diceDown = temp;
            }else{ //왼쪽으로 굴리는 경우
                int temp = dice;
                dice = diceRight;
                diceRight = diceBack;
                diceBack = diceLeft;
                diceLeft = temp;
            }
            answer += score[parents[changeOne(nx, ny)]];
            //System.out.printf("%d, %d, %d, %d\n",dice, diceDir, diceBack, map[nx][ny]);
            if(diceBack > map[nx][ny]){
                diceDir = (diceDir + 1) % 4;
            }else if(diceBack < map[nx][ny]){
                diceDir = (diceDir == 0) ? 3 : diceDir - 1;
            }
            //System.out.printf("%d, %d\n",dice, diceDir );
            diceX = nx; 
            diceY = ny;
        }
        System.out.println(answer);
    }
    static int changeOne(int x, int y){
        return x * n + y;
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x < n) && (y < n);
    }
    static void bfs(int x, int y){
        int minValue = n*n;
        int cnt = 0;
        int val = map[x][y];
        visited[x][y] = true;
        Queue<Pair> que = new LinkedList<>();
        que.add(new Pair(x, y));
        while(!que.isEmpty()){
            Pair cur = que.poll();
            int cid = changeOne(cur.x,cur.y);
            minValue = Math.min(minValue, cid);
            cnt++;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny) && !visited[nx][ny] && val == map[nx][ny]){
                    visited[nx][ny] = true;
                    int nid = changeOne(nx,ny);
                    unionP(cid, nid);
                    que.add(new Pair(nx, ny));
                }
            }
        }
        score[minValue] = cnt * val;
    }
}
class Pair{
    int x; 
    int y; 
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}