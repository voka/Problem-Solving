import java.io.*;
import java.util.*;
public class Main {
    static int n,m,k;
    static int[] px, py, cDir;
    static boolean[] out;
    static boolean[][] visit;
    static int[][] map;
    static int[][][] check, nextCheck;
    static int[][][] dir;
    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    static int alive;
    public static void main(String[] args) throws IOException{
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        out = new boolean[m];
        px = new int[m];
        py = new int[m];
        dir = new int[m][4][4];
        map = new int[n][n];
        check = new int[n][n][2];// 땅에 계약한 정보와 남은 
        cDir = new int[m];
        alive = m;
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                int c = Integer.parseInt(st.nextToken())-1;
                map[i][j] = c;
                check[i][j][0] = c; // -1 or pIndex
                if(c >= 0){
                    check[i][j][1] = k;
                    px[c] = i;
                    py[c] = j;
                }
            }
        }
        st = new StringTokenizer(br.readLine());
        for(int j=0;j<m;++j){
            cDir[j] = Integer.parseInt(st.nextToken())-1;
            //System.out.printf("i = %d ,(%d, %d) / dir => %d\n", j,px[j],py[j],cDir[j]);

        }
        for(int i=0;i<m;++i){
            for(int j=0;j<4;++j){
                st = new StringTokenizer(br.readLine());
                for(int q=0;q<4;++q){
                    dir[i][j][q] = Integer.parseInt(st.nextToken())-1;
                }
            }
        }
        //printStatusMap();
        int t = 0;
        int MaxCount = 1000;
        while(alive >= 2 && t < MaxCount){
            init();
            move();
            minus();
            //System.out.printf("T : %d\n", t);
            //printStatusMap();
            t++;
        }
        int ans = t < 1000 ? t : -1; 
        System.out.println(ans);
    
    }
    static void minus(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(!visit[i][j] && check[i][j][0] >= 0){
                    check[i][j][1]--;
                    if(check[i][j][1] == 0){
                        check[i][j][0] = -1; 
                    }
                }
            }
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<n) && (y<n);
    }
    static boolean canGo(int x, int y){
        return check[x][y][0] == -1;
    }
    static void init(){
        visit = new boolean[n][n];
        nextCheck = new int[n][n][2];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                nextCheck[i][j][0] = check[i][j][0];
                nextCheck[i][j][1] = check[i][j][1];
                
            }
        }
    }
    static void copyValue(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                for(int q=0;q<2;++q){
                    check[i][j][q] = nextCheck[i][j][q];
                }
            }
        }
    }
    static void move(){
        for(int i=0;i<m;++i){
            if(out[i]) continue;
            int cx = px[i];
            int cy = py[i];
            int cd = cDir[i];
            boolean flag = false;
            for(int d=0;d<4;++d){
                int pd = dir[i][cd][d];
                int nx = cx + dx[pd];
                int ny = cy + dy[pd];
                if(checkRange(nx,ny) && canGo(nx,ny)){
                    //System.out.printf("i = %d ,(%d, %d) -> (%d, %d) / dir => %d\n", i,cx,cy,nx,ny,pd);
                    visit[nx][ny] = true;
                    if(nextCheck[nx][ny][0]>=0){
                        //이미 방문한 플레이어가 있다면
                        out[i] = true; // 늦게 방문한 플레이어 번호가 작으므로 out 시킨다.
                        flag = true;
                        alive--;
                        break;
                    }
                    // 빈땅 차지하기
                    nextCheck[nx][ny][0] = i;
                    nextCheck[nx][ny][1] = k;
                    // 위치 변경
                    px[i] = nx;
                    py[i] = ny; 

                    // 방향 변경
                    cDir[i] = pd;
                    flag = true;
                    break;
                }
            }
            if(flag) continue;
            for(int d=0;d<4;++d){
                int pd = dir[i][cd][d];
                int nx = cx + dx[pd];
                int ny = cy + dy[pd];
                if(checkRange(nx,ny) && isMy(i, nx,ny)){
                    //System.out.printf("i = %d ,(%d, %d) -> (%d, %d) / dir => %d\n", i,cx,cy,nx,ny,pd);
                    
                    visit[nx][ny] = true;
                    // 내땅 다시 차지하기
                    nextCheck[nx][ny][1] = k;
                    // 위치 변경
                    px[i] = nx;
                    py[i] = ny;
                    //방향 변경
                    cDir[i] = pd;
                    break;
                }
            }
        }
        copyValue();
    }
    static boolean isMy(int id, int x, int y){
        return (check[x][y][0] > -1) && (check[x][y][0] == id); 
    }
    static void printMap(){
        
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ", map[i][j]+1);
            }
            System.out.println();
        }
    }
    static void printStatusMap(){
        
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("(%d,%d) ", check[i][j][0] + 1, check[i][j][1]);
            }
            System.out.println();
        }
    }
       
}