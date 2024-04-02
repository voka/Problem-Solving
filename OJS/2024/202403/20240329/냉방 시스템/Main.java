import java.io.*;
import java.util.*;
// https://www.codetree.ai/training-field/frequent-problems/problems/cooling-system/description?page=1&pageSize=20 
class Point{
    int x; 
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static List<Point> room = new ArrayList<>();
    static int[] ddx = {1,1,-1,-1}, ddy = {1,-1,1,-1}, dx = {-1,0,1,0}, dy = {0,-1,0,1};
    static int[][] map, score, nextScore;
    static boolean[][] visited;
    static int n,m,k;
    static boolean [][][] check; // 0 : 위, 1 : 왼쪽, 2 : 아래, 3: 오른쪽
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        score = new int[n][n];
        check = new boolean[n][n][4];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                int cur = Integer.parseInt(st.nextToken());
                map[i][j] = cur;
                if(cur == 1){
                    room.add(new Point(i,j));
                }
            }
        }
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            int s = Integer.parseInt(st.nextToken());
            check[x][y][s] = true;
            if(s == 0){ // 위쪽에 있는 경우 위칸의 아래쪽 벽에도 표시
                check[x-1][y][2] = true;
            }else if(s == 1){ // 왼쪽에 있는 경우 왼쪽칸의 오른쪽에도 표시
                check[x][y-1][3] = true;
            }
        }
        //printCheck();
        int ans = -1;
        for(int t = 0;t<100;++t){
            // System.out.printf("%d분\n", t);
            spreadAll();
            // System.out.println("시원한 바람 전파");
            // printArr(score);
            mix();
            // System.out.println("섞기");
            // printArr(score);
            minus(); 
            // System.out.println("외각지역 감소");
            // printArr(score);
            if(complete()) {
                ans = t+1;
                break;
            }
        }
        //printArr(score);
        System.out.println(ans);
    }
    static void spread(int x, int y, int d, int[][][] checkWind, int size){
        if(size == 0) return;
        if(visited[x][y]) return;
        visited[x][y] = true;
        if(d == 0){// 위쪽으로 바람이 불때
            checkWind[x][y][d] += size;
            if(checkRange(x-1,y-1) && (!check[x][y-1][0] && !check[x][y-1][3])) spread(x-1,y-1,d,checkWind,size-1);
            if(checkRange(x-1,y) && (!check[x-1][y][2])) spread(x-1,y,d,checkWind,size-1);
            if(checkRange(x-1,y+1) && (!check[x][y+1][0] && !check[x][y+1][1])) spread(x-1,y+1,d,checkWind,size-1);
        }else if(d == 1){ // 왼쪽으로
            checkWind[x][y][d] += size;    
            if(checkRange(x-1,y-1) && (!check[x-1][y][1] && !check[x-1][y][3])) spread(x-1,y-1,d,checkWind,size-1);
            if(checkRange(x,y-1) && (!check[x][y-1][3])) spread(x,y-1,d,checkWind,size-1);
            if(checkRange(x+1,y-1) && (!check[x+1][y][1] && !check[x+1][y][0])) spread(x+1,y-1,d,checkWind,size-1);
        }
        else if(d == 2){ // 아래쪽으로 바람이 불때
            checkWind[x][y][d] += size;
            if(checkRange(x+1,y-1) && (!check[x][y-1][2] && !check[x][y-1][3])) spread(x+1,y-1,d,checkWind,size-1);
            if(checkRange(x+1,y) && (!check[x+1][y][0])) spread(x+1,y,d,checkWind,size-1);
            if(checkRange(x+1,y+1) && (!check[x][y+1][2] && !check[x][y+1][1])) spread(x+1,y+1,d,checkWind,size-1);
        }
        else if(d == 3){ // 오른쪽으로
            checkWind[x][y][d] += size;
            if(checkRange(x-1,y+1) && (!check[x-1][y][2] && !check[x-1][y][3])) spread(x-1,y+1,d,checkWind,size-1);
            if(checkRange(x,y+1) && (!check[x][y+1][1])) spread(x,y+1,d,checkWind,size-1);
            if(checkRange(x+1,y+1) && (!check[x+1][y][0] && !check[x+1][y][3])) spread(x+1,y+1,d,checkWind,size-1);
        }
    }
    // 벽 => 위 : 0, 왼쪽 : 1, 아래 : 2, 오른 : 3
    static void spreadAll(){
        int[][][] checkWind = new int[n][n][4];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                visited = new boolean[n][n];
                if(map[i][j] == 2){ // 왼
                    if(checkRange(i,j-1) && !check[i][j-1][3]) spread(i,j-1,1,checkWind,5);
                }else if(map[i][j] == 3){ // 위
                    if(checkRange(i-1,j) && !check[i-1][j][2]) spread(i-1,j,0,checkWind,5);
                }else if(map[i][j] == 4){ // 오른
                    if(checkRange(i,j+1) && !check[i][j+1][1]) spread(i,j+1,3,checkWind,5);
                }else if(map[i][j] == 5){ // 아래
                    if(checkRange(i+1,j) && !check[i+1][j][0]) spread(i+1,j,2,checkWind,5);
                }
            }
        }
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                for(int d=0;d<4;++d){
                    score[i][j] += checkWind[i][j][d];
                }
            }   
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<n) && (y<n); 
    }
    static void mix(){
        nextScore = new int[n][n];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                int minusVal = 0;
                for(int d=0;d<4;++d){
                    int nx = i + dx[d];
                    int ny = j + dy[d];
                    if(checkRange(nx,ny) && !check[i][j][d]){
                        int gap = score[i][j] - score[nx][ny];
                        if(gap < 4) continue; 
                        int addVal = gap/4;
                        minusVal += addVal;
                        nextScore[nx][ny] += addVal; 
                    }
                }
                nextScore[i][j] += score[i][j] - minusVal;
            }
        }
        score = nextScore;
    }
    static void minus(){
        // 각 꼭지점 감소
        score[0][0] = Math.max(0,score[0][0]-1);
        score[n-1][0] = Math.max(0,score[n-1][0]-1);
        score[n-1][n-1] = Math.max(0,score[n-1][n-1]-1);
        score[0][n-1] = Math.max(0,score[0][n-1]-1);
        // 벽과 맞닿아있는 칸들 감소
        for(int i=1;i<n-1;++i){
            score[i][0] = Math.max(0,score[i][0]-1);
            score[0][i] = Math.max(0,score[0][i]-1);
            score[i][n-1] = Math.max(0,score[i][n-1]-1);
            score[n-1][i] = Math.max(0,score[n-1][i]-1);
        }
    }
    static void printArr(int[][] arr){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ", arr[i][j]);
            }
            System.out.println();
        }
    }
    static void printCheck(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.print("(");
                for(int d=0;d<4;++d){
                    System.out.printf("%b ,", check[i][j][d]);
                }
                System.out.print(")");
            }
            System.out.println();
        }
    }
    static boolean complete(){
        for(Point p : room){
            if(score[p.x][p.y] < k) return false;
        }
        return true;
    }
}

/**
 * 
 * 
 * 
 * 
 
5 4 2
0 0 5 0 0
4 0 1 1 1
0 0 1 1 0
0 0 2 0 0
0 3 0 0 0
3 2 0
3 2 1
3 3 1
4 2 0

 */