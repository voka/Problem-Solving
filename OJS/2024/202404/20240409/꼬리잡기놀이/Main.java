import java.io.*;
import java.util.*;
public class Main {
    static int n,m,k,team = 0, ans = 0;
    static boolean[] turn; // 반대로 방향을 바꿨는지 확인
    static Point[][] next, back; // 길 저장 
    static Point[] head,tail;
    static int[][] map, temp, teamMap, countMap, temp2, temp3;// 지도
    static int[] member;
    static boolean[][] visited;
    static int[] dx = {1,-1,0,0}, dy = {0,0,1,-1}; // 아래, 위, 오른쪽, 왼쪽
    public static void main(String[] args) throws IOException{

        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        turn = new boolean[m];
        member = new int[m];
        next = new Point[n][n];
        back = new Point[n][n];
        map = new int[n][n];
        teamMap = new int[n][n];
        countMap = new int[n][n];
        head = new Point[m];
        tail = new Point[m];
        visited = new boolean[n][n];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
                next[i][j] = new Point(-1,-1);
                back[i][j] = new Point(-1,-1);
            }
        }
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(map[i][j] == 1 && !visited[i][j]){
                    bfs(i,j);
                }
            }
        }
        //printArr(teamMap);
        //printArr(countMap);
        //move();
        //reverse(1);
        for(int t=0;t<k;++t){
            //System.out.printf("T ==> %d\n", t);
            move();
            throwBall(t);
            //printArr(map);
        }
        // System.out.println("NEXT");
        // printPointArr(next);
        // System.out.println("BACK");
        // printPointArr(back);
        System.out.println(ans);

        

    }
    static void throwBall(int t){
        t = t % (n*4);
        if(t < n){
            for(int i=0;i<n;++i){
                int cx = t;
                int cy = i;
                if(countMap[cx][cy] > 0){
                    catchBall(cx,cy);
                    break;
                }
            }
        }
        else if(t < 2*n){
            for(int i=n-1;i>=0;--i){
                int cx = i;
                int cy = t - n;
                if(countMap[cx][cy] > 0){
                    catchBall(cx,cy);
                    break;
                }
            }
        }
        else if(t < 3*n){
            for(int i=n-1;i>=0;--i){
                int cx = n - 1 - (t - 2*n);
                int cy = i;
                if(countMap[cx][cy] > 0){
                    catchBall(cx,cy);
                    break;
                }
            }
        }
        else{
            for(int i=0;i<n;++i){
                int cx = i;
                int cy = n - 1 - (t - 3*n);
                if(countMap[cx][cy] > 0){
                    catchBall(cx,cy);
                    break;
                }
            }
        }

    }
    static void catchBall(int x , int y){
        int team = teamMap[x][y]-1;
        if(!turn[team]){
            ans += countMap[x][y] * countMap[x][y];
        }else{
            int re = member[team] - countMap[x][y] + 1;
            ans += re * re;
        }
        reverse(team);
    }
    static void printTeam(){
        for(int i=0;i<m;++i){
            System.out.printf("head : (%d,%d) , tail : (%d,%d)\n", head[i].x,head[i].y, tail[i].x, tail[i].y);
        }
        System.out.println();
    }
    static void printArr(int[][] arr){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ",arr[i][j]);
            }
            System.out.println();
        }
    }
    static void move(){
        temp = new int[n][n];
        temp2 = new int[n][n];
        temp3 = new int[n][n];
        visited = new boolean[n][n];
        for(int i=0;i<m;++i){
            int tx = tail[i].x;
            int ty = tail[i].y;
            int size = member[i];
            for(int j=0;j<size;++j){
                if(!turn[i]){
                    int nx = next[tx][ty].x;
                    int ny = next[tx][ty].y;
                    temp[nx][ny] = map[tx][ty];
                    temp2[nx][ny] = teamMap[tx][ty];
                    temp3[nx][ny] = countMap[tx][ty];
                    if(j == 0) {
                        tail[i].x = nx;
                        tail[i].y = ny;
                    }
                    if(j == size-1){
                        head[i].x = nx;
                        head[i].y = ny;
                    }
                    tx = nx;
                    ty = ny;
                }else{
                    int nx = back[tx][ty].x;
                    int ny = back[tx][ty].y;
                    temp[nx][ny] = map[tx][ty];
                    temp2[nx][ny] = teamMap[tx][ty];
                    temp3[nx][ny] = countMap[tx][ty];
                    if(j == 0) {
                        tail[i].x = nx;
                        tail[i].y = ny;
                    }
                    if(j == size-1){
                        head[i].x = nx;
                        head[i].y = ny;
                    }
                    tx = nx;
                    ty = ny;
                } 
            }
        }
        copy();
    }
    static void copy(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                map[i][j] = temp[i][j];
                teamMap[i][j] = temp2[i][j];
                countMap[i][j] = temp3[i][j];
            }
        }
    }
    static void reverse(int i){
        int tempX = head[i].x;
        int tempY = head[i].y;
        // System.out.printf("%d,%d\n", head[i].x, head[i].y);
        // System.out.printf("%d,%d\n", tail[i].x, tail[i].y);
        head[i].x = tail[i].x;
        head[i].y = tail[i].y;
        tail[i].x = tempX;
        tail[i].y = tempY;
        turn[i] = !turn[i];
        // System.out.printf("%d,%d\n", head[i].x, head[i].y);
        // System.out.printf("%d,%d\n", tail[i].x, tail[i].y);
        map[head[i].x][head[i].y] = 1;
        map[tail[i].x][tail[i].y] = 3;
        
    }
    static void bfs(int x, int y){
        Queue<Point> que = new ArrayDeque<>();
        que.add(new Point(x,y));
        int lx = -1, ly = -1;
        while(!que.isEmpty()){
            Point c = que.poll();
            lx = c.x;
            ly = c.y;
            if(map[c.x][c.y] <= 3) {
                member[team]++; // 인원 수 증가
                teamMap[c.x][c.y] = team+1;
                countMap[c.x][c.y] = member[team];
            }
            if(map[c.x][c.y] == 3) tail[team] = new Point(c.x,c.y);
            for(int i=0;i<4;++i){
                int nx = c.x + dx[i];
                int ny = c.y + dy[i];
                if(checkRange(nx,ny) && !visited[nx][ny] && (map[c.x][c.y]+1 == map[nx][ny] || (map[nx][ny] == map[c.x][c.y] && (map[nx][ny] == 2 || map[nx][ny] == 4)))){
                    back[c.x][c.y] = new Point(nx,ny);
                    next[nx][ny] = new Point(c.x,c.y);
                    visited[nx][ny] = true;
                    que.add(new Point(nx,ny));
                }    
            }
        }
        next[x][y] = new Point(lx,ly);
        back[lx][ly] = new Point(x,y);
        head[team] = new Point(x,y);
        team++;
        
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<n) && (y<n);
    }
    static void printPointArr(Point[][] arr){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("(%d, %d) ", arr[i][j].x ,arr[i][j].y);
            }
            System.out.println();
        }
    }
}
class Point{
    int x; 
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}