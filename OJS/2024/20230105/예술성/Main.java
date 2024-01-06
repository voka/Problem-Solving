import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

class Point{
    int x;
    int y; 
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}

class Result{
    int originNum;
    int cnt;
    Map<Integer,Integer> groups;
    public Result(int originNum){
        this.originNum = originNum;
        this.cnt = 1;
        this.groups = new HashMap<>();
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("groups num : ").append(cnt).append('\n');
        for(int key : groups.keySet()){
            sb.append("key : ").append(key).append(" encounter : ").append(groups.get(key)).append('\n');
        }
        sb.append('\n');
        return sb.toString();
    }
}
public class Main {
    static int n;
    static int[][] grid;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static List<Result> myResults;
    static boolean checkRange(Point p){
        return (p.x >= 0) && (p.x < n) && (p.y >= 0) && (p.y < n);
    }
    static boolean checkValue(Point cur, Point next){
        return grid[cur.y][cur.x] == grid[next.y][next.x];
    }
    static Result bfs(Point start, boolean[][] visited, int[][] board){
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        Result result = new Result(grid[start.y][start.x]);
        while(!queue.isEmpty()){
            Point cur = queue.poll();
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                Point next = new Point(nx, ny);
                if(checkRange(next) && !visited[ny][nx]){ // 범위 안에 있고, 방문한 적이 없는 경우
                    if(board[ny][nx] == board[cur.y][cur.x]){ // 값이 같은 경우
                        result.cnt++; // 같은 그룹으로 생각한다.
                        queue.add(next);
                        visited[ny][nx] = true;
                    }else{
                        int nextV = board[ny][nx];
                        if(result.groups.containsKey(nextV)){
                            int cnt = result.groups.get(nextV);
                            result.groups.put(nextV, cnt+1);
                        }else{
                            result.groups.put(nextV, 1);
                        }
                    }
                }
            }
        }
        return result;
    }
    
    static void makeBoardbfs(Point start, boolean[][] visited, int[][] board, int groupNum){
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        while(!queue.isEmpty()){
            Point cur = queue.poll();
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                Point next = new Point(nx, ny);
                if(checkRange(next) && !visited[ny][nx]){ // 범위 안에 있고, 방문한 적이 없는 경우
                    if(checkValue(cur, next)){ // 값이 같은 경우
                        // 같은 그룹으로 생각한다.
                        queue.add(next);
                        visited[ny][nx] = true;
                        board[ny][nx] = groupNum;
                    }
                }
            }
        }
    }
    static int[][] makeGroupBoard(){
        int groupNum = 0;
        int[][] myNewBoard = new int[n][n];
        boolean[][] visited = new boolean[n][n];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(!visited[i][j]){ 
                    myNewBoard[i][j] = groupNum;
                    visited[i][j] = true;
                    makeBoardbfs(new Point(j,i),visited,myNewBoard,groupNum);
                    groupNum++;
                }
            }
        }
        //printArray(myNewBoard);
        return myNewBoard;

        
    }
    static void printArray(int[][] map){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    static long getScore(){

        int[][] curBoard = makeGroupBoard();
        
        boolean[][] visited = new boolean[n][n];

        Map<Integer,Result> myGroupingResult = new HashMap<>();

        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(!visited[i][j]){ 
                    int curV = curBoard[i][j];
                    visited[i][j] = true;
                    Result result = bfs(new Point(j,i),visited, curBoard);
                    //System.out.println(result.toString());
                    myGroupingResult.put(curV,result);
                }
            }
        }
        long score = 0;
        for(int key: myGroupingResult.keySet()){
            for(int gkey : myGroupingResult.get(key).groups.keySet()){
                int aNum = myGroupingResult.get(key).cnt;
                int bNum = myGroupingResult.get(gkey).cnt;
                int aOri = myGroupingResult.get(key).originNum;
                int bOri = myGroupingResult.get(gkey).originNum;
                int abEncounter = myGroupingResult.get(key).groups.get(gkey);
                score += (aNum + bNum) * (aOri) * (bOri) * (abEncounter);
            }
        }
        return score;
    }
    static void printArr(int[] arr){
        System.out.println();
        for(int i=0;i<arr.length;++i){
            System.out.printf("%d ", arr[i]);
        }
        System.out.println();
    }
    static void turnRectangle(int sx, int sy, int ex, int ey){
        //System.out.printf("%d, %d, %d, %d\n", sx,sy,ex,ey);
        if(ex - sx < 1) return;
        while(ex - sx > 0){
            // 위쪽 -> 오른쪽
            int[] right = new int [ey - sy + 1];
            int[] bottom = new int [ex - sx + 1];
            int[] left = new int [ey - sy + 1];
            int rid = 0, cx = ex;
            for(int i=ey;i>=sy;--i){
                right[rid++] = grid[i][ex];
            }

            for(int i=sx;i<=ex;++i){
                bottom[i - sx] = grid[ey][i];
            }
            for(int i=sy;i<=ey;++i){
                left[i-sy] = grid[i][sx];
            }
            // printArr(right);
            for(int i=ey;i>=sy;--i){
                grid[i][ex] = grid[sy][cx--];
            }
            //printArray(grid);
            for(int i=sx;i<=ex;++i){
                grid[ey][i] = right[i - sx];
            }
            //printArr(bottom);

            //printArray(grid);
            for(int i=sy;i<=ey;++i){
                grid[i][sx] = bottom[i-sy];
            }
            // printArr(left);

            //printArray(grid);
            int lid = 0;
            for(int i=ex;i>=sx;--i){
                grid[sy][i] = left[lid++];
            }

            //printArray(grid);
            sx += 1; 
            sy += 1;
            ex -= 1;
            ey -= 1;

        }

    }
    static void turnCross(int m){
        int[] yMidPoints = new int[n];
        for(int i=0;i<n;++i){
            yMidPoints[i] = grid[i][m];
            grid[i][m] = grid[m][n - 1 - i];
        }
        for(int i=0;i<n;++i){
            grid[m][i] = yMidPoints[i];
        }
    }
    static void turnGrid(){
        int m = (n-1) / 2;
        turnCross(m);
        turnRectangle(0, 0, m-1, m-1); // 왼쪽 위
        turnRectangle(m+1, 0, n-1, m-1); // 오른쪽 위
        turnRectangle(0, m+1, m-1, n-1); // 왼쪽 아래
        turnRectangle(m+1, m+1, n-1, n-1); // 오른쪽 아래
        
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];
        StringTokenizer st;
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine()," ");
            for(int j=0;j<n;++j){
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //System.out.println();
        long answer1 = getScore();
        //System.out.println(answer1);
        turnGrid();
        //printArray(grid);
        long answer2 = getScore();
        //System.out.println(answer2);
        turnGrid();
        //printArray(grid);
        long answer3 = getScore();
        //System.out.println(answer3);
        turnGrid();
        long answer4 = getScore();
        //System.out.println(answer4);
        System.out.println(answer1 + answer2 + answer3 + answer4);

    }
    
}
/**
 * 
 * 
 * 
5
1 2 2 3 3
3 4 2 3 3
2 2 1 3 1
2 2 1 1 1
2 2 1 1 1
 */
