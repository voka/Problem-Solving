import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
class TPair{
    Pair pair;
    int score;
    public TPair(){
        this.score = 0;
        this.pair = new Pair(0, 0);
    }
    public TPair(int x, int y, int score){
        this.score = score;
        this.pair = new Pair(x, y);
    }
    public void print(){
        System.out.printf("(%d,%d), score : %d\n", pair.x,pair.y,score);
    }
}
class Pair implements Comparable<Pair>{
    int x;
    int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.y > o.y) return 1;
        else if (this.y < o.y) return -1;
        else if(this.x < o.x) return 1;
        else if(this.x > o.x) return -1;
        return 0;
    }

    public void print(){
        System.out.printf("(%d,%d)\n", x,y);
    }

}
public class Main {
    static int K,M,wallIdx = 0;
    static int[][] board,temp;
    static int[] wall;
    static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
    static boolean[][] visited;
    static List<Pair> visit;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[5][5];
        temp = new int[5][5];
        wall = new int[M];
        for(int i=0;i<5;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<5;++j){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<M;++i){
            wall[i] = Integer.parseInt(st.nextToken());
        } 
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<K;++i){
            int score = solve();
            if(score == 0) break;
            sb.append(score).append(' ');
        }
        sb.append('\n'); 
        System.out.println(sb.toString());
    }
    static void printArr(int[][] arr){
        System.out.println();
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                System.out.printf("%d ", arr[i][j]);
            }
            System.out.println();
        }

    }
    static int solve(){
        int curScore = 0;
        initTemp();
        TPair t1 = new TPair(),t2 = new TPair(),t3 = new TPair();
        for(int j=1;j<=3;++j){
            for(int i=1;i<=3;++i){
                // 90도 회전
                initTemp();
                turn90(i, j);
                int cur = getScore();
                if(t1.score < cur){
                    t1.pair = new Pair(i, j);
                    t1.score = cur;
                }
                initTemp();
                turn180(i, j);
                cur = getScore();
                if(t2.score < cur){
                    t2.pair = new Pair(i, j);
                    t2.score = cur;
                }
                initTemp();
                turn270(i, j);
                cur = getScore();
                if(t3.score < cur){
                    t3.pair = new Pair(i, j);
                    t3.score = cur;
                }
            }
        }
        //t1.print();
        //t2.print();
        //t3.print();
        if(t1.score == 0 && t2.score == 0 && t3.score == 0){ // 더 이상 유물을 발견할 수 없을 경우
            return curScore;
        }
        //1. 90 도 회전한 경우가 가장 큰 점수를 획득 하거나 같은경우
        if(t1.score >= t2.score && t1.score >= t3.score){
            // 실제 board를 회전한 board로 변경 후 타일 제거
            initTemp();
            turn90(t1.pair.x, t1.pair.y);
            copyTemp();
            curScore += t1.score;
        }
        // 2. 180 도 회전한 경우가 90도 회전한 점수보다 크고, 270도 회전한 것 보다 점수가 같거나 클때
        if(t2.score > t1.score && t2.score >= t3.score){
            initTemp();
            turn180(t2.pair.x, t2.pair.y);
            copyTemp();
            curScore += t2.score;
        }
        // 3 270도 회전한 것이 점수가 가장 클때
        if(t3.score > t1.score && t3.score > t2.score){
            initTemp();
            turn270(t3.pair.x, t3.pair.y);
            copyTemp();
            curScore += t3.score;

        }
        List<Pair> deleted = deleteTile();
        //printArr(board);
        if(!fillTile(deleted)) return curScore;
        //printArr(board);

        // 유물 연쇄
        while (true) {
            initTemp();
            int s = getScore();
            if(s >= 3){
                curScore += s;
                deleted = deleteTile();
                //printArr(board);
                if(!fillTile(deleted)) return curScore;
            }else break;
        }
        return curScore;
    }
    static boolean fillTile(List<Pair> delList){

        Collections.sort(delList);
        for(Pair pair : delList){
            board[pair.x][pair.y] = wall[wallIdx++];
            //pair.print();
        }
        if(wallIdx >= M) return false;

        return true;
    }
    static List<Pair> deleteTile(){
        List<Pair> re = new ArrayList<>();
        visited = new boolean[5][5];
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                if(!visited[i][j]){
                    visited[i][j] = true;
                    int cur = bfs(i, j, visited, true);
                    if(cur >= 3){
                        for(Pair pair : visit){
                            re.add(pair);
                        }
                    }
                }
            }
        }
        for(Pair pair : re){
            board[pair.x][pair.y] = 0;
        }
        return re;
    }
    static void copyTemp(){
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                board[i][j] = temp[i][j]; 
            }
        }
    }
    static void initTemp(){
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                temp[i][j] = board[i][j]; 
            }
        }
    }
    static void turn90(int x, int y){ // 중심좌표
        temp[x-1][y-1] = board[x+1][y-1];
        temp[x-1][y] = board[x][y-1];
        temp[x-1][y+1] = board[x-1][y-1];
        ///
        temp[x][y+1] = board[x-1][y];
        temp[x+1][y+1] = board[x-1][y+1];
        temp[x+1][y] = board[x][y+1];
        ///
        temp[x+1][y-1] = board[x+1][y+1];
        temp[x][y-1] = board[x+1][y];
        
    }
    static void turn180(int x, int y){ // 중심좌표
        temp[x-1][y-1] = board[x+1][y+1];
        temp[x-1][y] = board[x+1][y];
        temp[x-1][y+1] = board[x+1][y-1];
        ///
        temp[x][y+1] = board[x][y-1];
        temp[x+1][y+1] = board[x-1][y-1];
        temp[x+1][y] = board[x-1][y];
        ///
        temp[x+1][y-1] = board[x-1][y+1];
        temp[x][y-1] = board[x][y+1];
    }

    static void turn270(int x, int y){ // 중심좌표
        temp[x-1][y-1] = board[x-1][y+1];
        temp[x-1][y] = board[x][y+1];
        temp[x-1][y+1] = board[x+1][y+1];
        ///
        temp[x][y+1] = board[x+1][y];
        temp[x+1][y+1] = board[x+1][y-1];
        temp[x+1][y] = board[x][y-1];
        ///
        temp[x+1][y-1] = board[x-1][y-1];
        temp[x][y-1] = board[x-1][y];
    }
    static int getScore(){
        visited = new boolean[5][5];
        int score = 0;
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                if(!visited[i][j]){
                    visited[i][j] = true;
                    int cur = bfs(i, j, visited,false);
                    if(cur >= 3){
                        score+=cur;
                    }
                }
            }
        }
        return score;
    }
    static boolean rangeCheck(int x, int y){
        return (x>=0) && (y>=0) && (x<5) && (y<5);
    }
    static int bfs(int x, int y, boolean[][] visited, boolean check){
        Queue<Pair> que = new ArrayDeque<>();
        int get = 1;
        que.add(new Pair(x, y));
        if(check) visit = new ArrayList<>();
        while(!que.isEmpty()){
            Pair cur = que.poll();
            if(check) visit.add(cur);
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(!rangeCheck(nx, ny)) continue;
                if(visited[nx][ny]) continue;
                if(temp[nx][ny] != temp[cur.x][cur.y]) continue;
                que.add(new Pair(nx, ny));
                visited[nx][ny] = true;
                get++;
                //System.out.printf("(%d,%d)\n",nx,ny);
            }
        }
        return get;
    }
    
}
