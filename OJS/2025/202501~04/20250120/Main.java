/*
 * 기출 문제 / 삼성 SW 역량테스트 2024 하반기 오전 1번 문제 / 미지의 공간 탈출
 * 
 * 1. 3차원, 2차원 지도의 장애물 입력 확인 (0: 빈공간, 1:장애물)
 * 2. 시간 이상 현상 확산 
 * 3. 길찾기
 * 
 */
import java.io.*;
import java.util.*;
class My implements Comparable<My>{
    int x; int y; int t;
    public My(int x, int y, int t){
        this.x = x;
        this.y = y;
        this.t = t;
    }
    @Override
    public int compareTo(My o) {
        if(this.t < o.t) return -1;
        return 1;
    }
    public void print(){
        System.out.printf("(%d, %d) ", x, y);
    }

}
public class Main {
    static int WALL = 1,START = 2, TIMEZONE = 3,OUT = 4, ELE = 10, TIMESTOP = 7, PASS = 8; // 상수
    static int N,M,F,LAST;
    static int tsx,tsy,sx,sy,tx,ty; // 타임머신 위치, 탈출구 위치
    static int[] dy = {1,-1,0,0}, dx = {0,0,1,-1}; // 2차원 평면 길찾기
    static int[][] map, nextX, nextY;
    static int[][] tdmap, tempMap;
    /* 
    정육면체 전개도 생각해서 

    00  11  00
    11  11  11
    00  11  00
    
    위에서 11 되어 있는 부분만 입력받고 00 있는 부분을 지나갈때는 시간 안지나게 하는거지

    */ 
    static int[] r,c,d,v;
    static boolean[] dead; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        F = Integer.parseInt(st.nextToken());
        tempMap = new int[N][N];
        tdmap = new int[3*M][3*M];
        map = new int[N + 2*M][N + 2*M]; nextX = new int[N + 2*M][N + 2*M]; nextY = new int[N + 2*M][N + 2*M];

        for(int i=0;i<3*M;++i){
            for(int j=0;j<3*M;++j){
                tdmap[i][j] = TIMESTOP;
            }
        }
        r = new int[F]; c = new int[F]; d = new int[F]; v = new int[F]; dead = new boolean[F];
        boolean timeflag = false;
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                int temp = Integer.parseInt(st.nextToken());
                tempMap[i][j] = temp;
                if(!timeflag && temp == TIMEZONE){
                    tsx = i; tsy = j;
                    timeflag = true;
                }
            }
        }
        for(int k=0;k<5;++k){
            for(int i=0;i<M;++i){
                st = new StringTokenizer(br.readLine());
                for(int j=0;j<M;++j){
                    int temp = Integer.parseInt(st.nextToken());
                    if(k == 0){ // 동쪽
                        tdmap[M + i][2*M + j] = temp;
                        if(i == 0){
                            nextX[M+i+tsx][2*M+j+tsy] = M+i+tsx - j - 1;
                            nextY[M+i+tsx][2*M+j+tsy] = 2*M + tsy - 1;
                            //반대편
                            nextX[M+i+tsx - j - 1][2*M + tsy - 1] = M+i+tsx;
                            nextY[M+i+tsx - j - 1][2*M + tsy - 1] = 2*M+j+tsy;
                        }
                        if(i == M-1){
                            nextX[M+i+tsx][2*M+j+tsy] = M+i+tsx + j + 1;
                            nextY[M+i+tsx][2*M+j+tsy] = 2*M + tsy - 1;
                            //반대편
                            nextX[M+i+tsx + j + 1][2*M + tsy - 1] = M+i+tsx;
                            nextY[M+i+tsx + j + 1][2*M + tsy - 1] = 2*M+j+tsy;
                        }
                    }else if(k == 1){ // 서쪽
                        tdmap[M + i][j] = temp;
                        if(i == 0){
                            nextX[M+i+tsx][j+tsy] = i+tsx + j;
                            nextY[M+i+tsx][j+tsy] = tsy + M;
                            //반대편
                            nextX[i+tsx + j][tsy + M] = M+i+tsx;
                            nextY[i+tsx + j][tsy + M] = j+tsy;
                        }
                        if(i == M-1){
                            nextX[M+i+tsx][j+tsy] = 2*M+i+tsx - j;
                            nextY[M+i+tsx][j+tsy] = tsy + M ;
                            //반대편
                            nextX[2*M+i+tsx - j][tsy + M] = M+i+tsx;
                            nextY[2*M+i+tsx - j][tsy + M] = j+tsy;
                        }
                    }else if(k == 2){ // 남쪽
                        tdmap[2*M + i][M + j] = temp;
                    }else if(k == 3){ // 북쪽
                        tdmap[i][M + j] = temp;
                    }else{
                        tdmap[M + i][M + j] = temp;
                    }
                }
            }
        }

        

        for(int i=0;i<F;++i){
            st = new StringTokenizer(br.readLine());
            r[i] = Integer.parseInt(st.nextToken());
            c[i] = Integer.parseInt(st.nextToken());
            tempMap[r[i]][c[i]] = ELE + i;
            d[i] = Integer.parseInt(st.nextToken());
            v[i] = Integer.parseInt(st.nextToken());
        }

        turnL(M, 2*M, 2*M, 3*M);
        turnR(M, 0, 2*M, M);
        turnL(0,M,M,2*M);
        turnL(0,M,M,2*M);
        for(int i=0;i<N + 2*M;++i){
            for(int j=0;j<N + 2*M;++j){
                if((tsx - 1 <= i) && (i <= tsx + 3*M)&& (tsy -1 <= j) && (j <= tsy + 3*M)){
                    map[i][j] = WALL;
                }
                else map[i][j] = PASS;
            }
        }
        //3dmap => map
        for(int i=tsx;i<tsx+3*M;++i){
            for(int j=tsy;j<tsy+3*M;++j){
                map[i][j] = tdmap[i-tsx][j-tsy];
            }
        }
        //tempMap => map
        //대각선
        for(int i=0;i<tsx;++i){
            for(int j=0;j<tsy;++j){
                map[i][j] = tempMap[i][j];
            }   
        }
        for(int i=tsx + 3*M;i<N + 2*M;++i){
            for(int j=0;j<tsy;++j){
                map[i][j] = tempMap[i - 2*M][j];
                changeELE(i - 2*M,j,i,j);
            }   
        }
        for(int i=0;i<tsx;++i){
            for(int j=tsy + 3*M;j<N + 2*M;++j){
                map[i][j] = tempMap[i][j-2*M];
                changeELE(i,j-2*M,i,j);
            }   
        }
        for(int i=tsx + 3*M;i<N + 2*M;++i){
            for(int j=tsy + 3*M;j<N + 2*M;++j){
                map[i][j] = tempMap[i-2*M][j-2*M];
                changeELE(i-2*M,j-2*M,i,j);
            }   
        }
        //상,하,좌,우
        for(int i=tsx + M;i<tsx + 2*M;++i){
            for(int j=0;j<tsy;++j){
                map[i][j] = tempMap[i-M][j];
                changeELE(i-M,j,i,j);
            }
        }
        for(int i=0;i<tsx;++i){
            for(int j=tsy+M;j<tsy+2*M;++j){
                map[i][j] = tempMap[i][j-M];
                changeELE(i,j-M,i,j);
            }
        }
        for(int i=tsx + M;i<tsx + 2*M;++i){
            for(int j=tsy+3*M;j<N + 2*M;++j){
                map[i][j] = tempMap[i-M][j-2*M];
                changeELE(i-M,j-2*M,i,j);
            }
        }
        for(int i=tsx+3*M;i<N + 2*M;++i){
            for(int j=tsy+M;j<tsy+2*M;++j){
                map[i][j] = tempMap[i-2*M][j-M];
                changeELE(i-2*M,j-M,i,j);
            }
        }
        
        boolean findS = false, findO = false;
        for(int i=0;i<N+2*M;++i){
            for(int j=0;j<N+2*M;++j){
                if(map[i][j] == OUT){
                    tx = i; ty = j;
                    findO = true;
                }else if(map[i][j] == START){
                    sx = i; sy = j;
                    findS = true;
                }
                if(findO && findS) break;
            }
        }
        //printMap();
        // for(int i=0;i<N + 2*M;++i){
        //     for(int j=0;j<N + 2*M;++j){
        //         System.out.printf("(%d,%d), ", nextX[i][j], nextY[i][j]);
        //     }
        //     System.out.println();
        // }
        solve();
        // spread(14);
        // spread(28);
        // spread(42);
        //printMap();
    }
    static void turnL(int sx, int sy, int ex, int ey){
        int R = ex - sx;
        int[][] temp = new int[R][R];
        for(int i=0;i<R;++i){
            for(int j=0;j<R;++j){
                temp[R-j-1][i] = tdmap[sx+i][sy+j];
            }
        }
        for(int i=0;i<R;++i){
            for(int j=0;j<R;++j){
                tdmap[sx+i][sy+j] = temp[i][j];
            }
        }
    }
    static void turnR(int sx, int sy, int ex, int ey){
        int R = ex - sx;
        int[][] temp = new int[R][R];
        for(int i=0;i<R;++i){
            for(int j=0;j<R;++j){
                temp[j][R-i-1] = tdmap[sx+i][sy+j];
            }
        }
        for(int i=0;i<R;++i){
            for(int j=0;j<R;++j){
                tdmap[sx+i][sy+j] = temp[i][j];
            }
        }
    }
    static void changeELE(int x, int y, int nx , int ny){
        if(tempMap[x][y] >= ELE){
            int id = tempMap[x][y] - ELE;
            map[nx][ny] = ELE;
            r[id] = nx;
            c[id] = ny;
        }
    }
    static void print3d(){
        for(int i=0;i<3*M;++i){
            for(int j=0;j<3*M;++j){
                System.out.printf("%d ", tdmap[i][j]);
            }
            System.out.println();
        }
    }
    static void printMap(){
        for(int i=0;i<N+2*M;++i){
            for(int j=0;j<N+2*M;++j){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
    }

    static void solve(){
        int _MAX = 500 * 500;
        boolean[][] visit = new boolean[N+2*M][N+2*M];
        int[][] cost = new int[N+2*M][N+2*M];
        LAST = 0;
        for(int i=0;i<N+2*M;++i){
            for(int j=0;j<N+2*M;++j){
                cost[i][j] = _MAX;
            }
        }
        PriorityQueue<My> pq = new PriorityQueue<>();
        pq.add(new My(sx,sy,0));
        visit[sx][sy] = true;
        cost[sx][sy] = 0;
        while(!pq.isEmpty()){
            My temp = pq.poll();
            for(int i=0;i<4;++i){
                int nx = temp.x + dx[i];
                int ny = temp.y + dy[i];
                if(!check2d(nx, ny)) continue;
                if(map[nx][ny] == TIMESTOP){
                    int tnx = nextX[temp.x][temp.y];
                    int tny = nextY[temp.x][temp.y];
                    if(!check2d(tnx, tny)) continue;
                    nx = tnx; ny = tny;
                }
                if(map[nx][ny] == PASS){
                    while(map[nx][ny] == PASS){
                        nx = nx + dx[i];
                        ny = ny + dy[i];
                    }
                    if(!check2d(nx, ny)) continue;
                }
                if(cost[nx][ny] > temp.t + 1){
                    if(LAST < temp.t + 1){
                        LAST = temp.t + 1;
                        spread(temp.t+1);
                    }
                    cost[nx][ny] = temp.t + 1;
                    pq.add(new My(nx, ny, temp.t + 1));
                }
                
            }
        }
        // for(int i=0;i<N+2*M;++i){
        //     for(int j=0;j<N+2*M;++j){
        //         if(cost[i][j] == _MAX){
        //             System.out.print("00 ");
        //         }
        //         else System.out.printf("%d ", cost[i][j]);
        //     }
        //     System.out.println();
        // }
        if(cost[tx][ty]==_MAX) System.out.println(-1);
        else System.out.println(cost[tx][ty]);

    }
    static boolean check2d(int x, int y){
        return x >= 0 && y >= 0 && x < (N+2*M) && y < (N+2*M) && (map[x][y] != 1) && (map[x][y] != ELE);   
    }
    static void spread(int t){  
        // 기존 이상 현상은 사라지지 않고 지속됨
        // 시간 이상 현상 배열을 돌며 v의 배수일때 d 방향으로 한칸 씩 확장 -> 약간 장애물 처리 
        for(int i=0;i<F;++i){
            if(dead[i]) continue;
            if(t % v[i] == 0){
                int nx = r[i] + dx[d[i]];
                int ny = c[i] + dy[d[i]];
                if(!check2d(nx,ny) || (map[nx][ny] == 4)) {
                    dead[i] = true; // 더이상 진행할 수 없는 이상현상
                    continue;
                }
                if(map[nx][ny] == PASS){
                    while(map[nx][ny] == PASS){
                        nx = nx + dx[d[i]];
                        ny = ny + dy[d[i]];
                    }
                }
                //System.out.printf("t : %d, (%d,%d) : %d -> (%d, %d) : %d \n", t, r[i], c[i], map[r[i]][c[i]], nx,ny, map[nx][ny]);
                if(!check2d(nx,ny) || (map[nx][ny] == 4)) {
                    dead[i] = true; // 더이상 진행할 수 없는 이상현상
                    continue;
                }
                r[i] = nx;
                c[i] = ny;
                map[r[i]][c[i]] = ELE;
            }
        }
    }
}
