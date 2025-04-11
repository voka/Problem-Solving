import java.io.*;
import java.util.*;

class Pair{
    int x;
    int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}

public class Main {
    static int N,M;
    static int sr,sc,er,ec, mr, mc, mSeeDir, attack, move;
    static int[] ar, ac, as; // r, s, 상태 (정상, 돌, 사라짐 );
    static int[][] map;

    // 이동 우선순위
    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    static int[] sdx = {0,0,-1,1}, sdy = {-1,1,0,0};


    // 메두사 최단 이동경로 저장
    static int[][] nextX, nextY;

    //전사 지도
    static boolean[][] sMap;
    static boolean[][][] mSeeMap; 
    static boolean[][][] sGoard; 

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        sr = Integer.parseInt(st.nextToken());
        sc = Integer.parseInt(st.nextToken());
        er = Integer.parseInt(st.nextToken());
        ec = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        ar = new int[M+1];
        ac = new int[M+1];
        as = new int[M+1];
        sMap = new boolean[N][N];
        for(int i=1;i<=M;++i){
            ar[i] = Integer.parseInt(st.nextToken());
            ac[i] = Integer.parseInt(st.nextToken());
        }
        map = new int[N][N];
        nextX = new int[N][N];
        nextY = new int[N][N];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        mr = sr; 
        mc = sc;
        int turn = 1;
        if(checkPath()){
            while(true){
                //System.out.printf("\n%d 번째 턴\n",turn++);
                mMove();
                //System.out.printf("메두사 위치 : (%d, %d)\n",mr,mc);
                if(mr == er && mc == ec) break;
                makeSMap();
                int stone = mSee(); // 돌이 된 전사 수
                aMove();
                System.out.printf("%d %d %d\n",move,stone,attack);
                heal();// 돌이 된 전사들 풀어주기

            }
            System.out.println(0);
        }else{
            System.out.println(-1);
        }
    }
    static void aMove(){
        attack = 0;
        move = 0;
        for(int i=1;i<=M;++i){
            if(as[i] == 1 || as[i] == -1) continue; // 돌이거나 사라졌다면 움직일 수 없음;
            //System.out.printf("%d 번 전사 : (%d,%d)\n",i, ar[i],ac[i]);
            // 첫번째 이동
            int minDis = getDistance(ar[i], ac[i], mr, mc);// 초기 거리
            int d = -1;
            int anx = -1, any = -1;
            for(int j=0;j<4;++j){
                int nx = ar[i] + dx[j];
                int ny = ac[i] + dy[j];
                if(!checkRange(nx, ny)) continue; // 격자밖으로는 나가지 못함
                if(mSeeMap[mSeeDir][nx][ny] && !sGoard[mSeeDir][nx][ny]) continue; // 메두사 시야 범위 안이면 이동 불가
                int dis = getDistance(nx, ny, mr, mc);
                if(minDis > dis){ // 이전에 찾았던 최단 거리보다 더 작을 경우에만 위치 갱신
                    minDis = dis;
                    d = j;
                    anx = nx;
                    any = ny;
                }
            }
            if(d == -1) continue; // 이동할 곳을 찾지 못했다면 두번째 이동 시행 x
            // 실제 첫번째 이동
            //System.out.printf("1st 이동 :(%d,%d) -> (%d,%d)\n",ar[i],ac[i],anx,any);
            ar[i] = anx;
            ac[i] = any;
            move++;
            if(minDis == 0) { // 메두사와 만났다면 공격하고 중단
                as[i] = -1;
                attack++; 
                continue;
            }
           
            // 두번째 이동
            d = -1;
            for(int j=0;j<4;++j){
                int nx = ar[i] + sdx[j];
                int ny = ac[i] + sdy[j];
                if(!checkRange(nx, ny)) continue; // 격자밖으로는 나가지 못함
                if(mSeeMap[mSeeDir][nx][ny] && !sGoard[mSeeDir][nx][ny]) continue; // 메두사 시야 범위 안이면 이동 불가
                int dis = getDistance(nx, ny, mr, mc);
                //System.out.printf("2nd -> minDis : %d, dis : %d\n",minDis, dis);
                if(minDis > dis){ // 이전에 찾았던 최단 거리보다 더 작을 경우에만 위치 갱신
                    minDis = dis;
                    d = j;
                    anx = nx;
                    any = ny;
                }
            }
            if(d == -1) continue; // 이동할 곳을 찾지 못했다면 종료
            // 실제 두번째 이동
            //System.out.printf("2nd 이동 :(%d,%d) -> (%d,%d)\n",ar[i],ac[i],anx,any);
            ar[i] = anx;
            ac[i] = any;
            move++;
            if(minDis == 0) { // 메두사와 만났다면 공격
                as[i] = -1;
                attack++; 
            }


        }

    }
    static int getDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
    static void heal(){
        for(int i=1;i<=M;++i){
            if(as[i] == 1) as[i] = 0;
        }
    }
    static void makeSMap(){
        sMap = new boolean[N][N];
        for(int i=1;i<=M;++i){
            if(as[i] == -1) continue;
            int x = ar[i];
            int y = ac[i];
            
            //System.out.printf("%d: %d,%d\n",i,x,y);
            sMap[x][y] = true;
        }
    }
    static int mSee(){
        mSeeMap = new boolean[4][N][N];
        sGoard = new boolean[4][N][N];
        // 메두사 시선
        int d;
        //위쪽
        d = 1;
        for(int x=mr-1;x>=0;--x){
            int sy = (mc-d) >= 0 ? mc-d : 0;
            int ey = (mc+d) < N ? mc+d : N-1 ;
            for(int y=sy;y<=ey;++y){
                mSeeMap[0][x][y] = true;
                if(sMap[x][y]){ // 해당 위치에 전사가 있다면
                    // 메두사의 왼쪽 대각선에 위치
                    if(y < mc){
                        int td = 1;
                        for(int tx = x-1; tx >=0 ; --tx){
                            int s = (y - td) >= 0 ? y - td: 0;
                            for(int ty=s;ty<=y;++ty){
                                sGoard[0][tx][ty] = true;
                            }
                            td++;
                        }
                    }else if(y == mc){ // 바로 위쪽에만 위치
                        for(int tx = x-1; tx >=0 ; --tx){
                            sGoard[0][tx][y] = true;
                        }
                    }else{ // 오른쪽 대각선에 위치
                        int td = 1;
                        for(int tx = x-1; tx >=0 ; --tx){
                            int e = (y + td) < N ? y+td: N-1;
                            for(int ty=y;ty<=e;++ty){
                                sGoard[0][tx][ty] = true;
                            }
                            td++;
                        }
                    }

                }
            }
            d++;
        }

        //아래쪽
        d = 1;
        for(int x=mr+1;x<N;++x){
            int sy = (mc-d) >= 0 ? mc-d : 0;
            int ey = (mc+d) < N ? mc+d : N-1 ;
            for(int y=sy;y<=ey;++y){
                mSeeMap[1][x][y] = true;
                if(sMap[x][y]){ // 해당 위치에 전사가 있다면
                    // 메두사의 왼쪽 대각선에 위치
                    if(y < mc){
                        int td = 1;
                        for(int tx = x+1; tx<N ; ++tx){
                            int s = (y - td) >= 0 ? y - td: 0;
                            for(int ty=s;ty<=y;++ty){
                                sGoard[1][tx][ty] = true;
                            }
                            td++;
                        }
                    }else if(y == mc){ // 바로 아래쪽에만 위치
                        for(int tx = x+1; tx<N ; ++tx){
                            sGoard[1][tx][y] = true;
                        }
                    }else{ // 오른쪽 대각선에 위치
                        int td = 1;
                        for(int tx = x+1; tx<N ; ++tx){
                            int e = (y + td) < N ? y+td: N-1;
                            for(int ty=y;ty<=e;++ty){
                                sGoard[1][tx][ty] = true;
                            }
                            td++;
                        }
                    }

                }
            }
            d++;
        }

        //왼쪽
        d = 1;
        for(int y=mc-1;y>=0;--y){
            int sx = (mr-d) >= 0 ? mr-d : 0;
            int ex = (mr+d) < N ? mr+d : N-1 ;
            for(int x=sx;x<=ex;++x){
                mSeeMap[2][x][y] = true;
                if(sMap[x][y]){ // 해당 위치에 전사가 있다면
                    // 메두사의 왼쪽 대각선에 위치
                    if(x < mr){
                        int td = 1;
                        for(int ty = y-1; ty>=0 ; --ty){
                            int s = (x - td) >= 0 ? x - td: 0;
                            for(int tx=s;tx<=x;++tx){
                                sGoard[2][tx][ty] = true;
                            }
                            td++;
                        }
                    }else if(x == mr){ // 바로 왼쪽에만 위치
                        for(int ty = y-1; ty>=0 ; --ty){
                            sGoard[2][x][ty] = true;
                        }
                    }else{ // 오른쪽 대각선에 위치
                        int td = 1;
                        for(int ty = y-1; ty>=0 ; --ty){
                            int e = (x + td) < N ? x+td: N-1;
                            for(int tx=x;tx<=e;++tx){
                                sGoard[2][tx][ty] = true;
                            }
                            td++;
                        }
                    }

                }
            }
            d++;
        }

        // 오른쪽
        d = 1;
        for(int y=mc+1;y<N;++y){
            int sx = (mr-d) >= 0 ? mr-d : 0;
            int ex = (mr+d) < N ? mr+d : N-1 ;
            for(int x=sx;x<=ex;++x){
                mSeeMap[3][x][y] = true;
                if(sMap[x][y]){ // 해당 위치에 전사가 있다면
                    // 메두사의 위 대각선에 위치
                    if(x < mr){
                        int td = 1;
                        for(int ty = y+1; ty<N ; ++ty){
                            int s = (x - td) >= 0 ? x - td: 0;
                            for(int tx=s;tx<=x;++tx){
                                sGoard[3][tx][ty] = true;
                            }
                            td++;
                        }
                    }else if(x == mr){ // 바로 오른쪽에만 위치
                        for(int ty = y+1; ty<N ; ++ty){
                            sGoard[3][x][ty] = true;
                        }
                    }else{ // 아래 대각선에 위치
                        int td = 1;
                        for(int ty = y+1; ty<N ; ++ty){
                            int e = (x + td) < N ? x+td: N-1;
                            for(int tx=x;tx<=e;++tx){
                                sGoard[3][tx][ty] = true;
                            }
                            td++;
                        }
                    }

                }
            }
            d++;
        }
        // System.out.println("메두사 시야");
        // for(int i=0;i<N;++i){
        //     for(int j=0;j<N;++j){
        //         System.out.printf("%b ",mSeeMap[3][i][j] );
        //     }
        //     System.out.println();
        // }
        // System.out.println();
        // System.out.println("전사 가림");
        // for(int i=0;i<N;++i){
        //     for(int j=0;j<N;++j){
        //         System.out.printf("%b ",sGoard[3][i][j]);
        //     }
        //     System.out.println();
        // }
        // System.out.println();

        // 4 방향중 전사를 가장 많이 볼 수 있는 곳 선택
        int maxNum = -1;
        int ans = -1;
        for(int i=0;i<4;++i){
            int cnt = 0;
            for(int id=1;id<=M;++id){
                if(as[id] == -1) continue;// 
                int x = ar[id];
                int y = ac[id];
                if(mSeeMap[i][x][y] && !sGoard[i][x][y]){
                    cnt++;
                }
            }
            if(cnt > maxNum){
                maxNum = cnt;
                ans = i;
            }
        }
        
        // ans 방향으로 봤을때 보이는 전사들을 돌로 만들기
        mSeeDir = ans;
        for(int id=1;id<=M;++id){
            if(as[id] == -1) continue;
            int x = ar[id];
            int y = ac[id];
            if(mSeeMap[ans][x][y] && !sGoard[ans][x][y]){
                as[id] = 1; // 돌
            }
        }
        return maxNum;
    }
    static void mMove(){ // 메두사 이동
        int nx = nextX[mr][mc];
        int ny = nextY[mr][mc];
        mr = nx;
        mc = ny;
        // 전사가 같은 칸에 있는지 확인
        for(int i=1;i<=M;++i){
            if(ar[i] == mr && ac[i] == mc){
                as[i] = -1;// 죽음 상태로 변경 (공격한게 아니라 당한 거임)
            }
        }
    }
    static boolean checkPath(){
        Queue<Pair> q = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];
        int[][] backX = new int[N][N],backY = new int[N][N];
        q.add(new Pair(sr, sc));
        visit[sr][sc] = true;
        while(!q.isEmpty()){
            Pair c = q.poll();
            if(c.x == er && c.y == ec) break;
            for(int i=0;i<4;++i){
                int nx = c.x + dx[i];
                int ny = c.y + dy[i];
                if(!checkRange(nx,ny)) continue;
                if(visit[nx][ny]) continue;
                if(map[nx][ny]==1) continue;
                visit[nx][ny] = true;
                backX[nx][ny] = c.x;
                backY[nx][ny] = c.y;
                q.add(new Pair(nx, ny));
            }
        }
        if(!visit[er][ec]) return false;

        int x = er, y = ec;
        while(!(x == sr && y == sc)){
            int nx = backX[x][y];
            int ny = backY[x][y];
            nextX[nx][ny] = x;
            nextY[nx][ny] = y;
            x = nx;
            y = ny;
        }

        // boolean[][] check = new boolean[N][N];
        // x = sr;
        // y = sc;
        // while(!(x == er && y == ec)){
        //     int nx = nextX[x][y];
        //     int ny = nextY[x][y];
        //     check[nx][ny] = true;
        //     x = nx;
        //     y = ny;
        // }

        // for(int i=0;i<N;++i){
        //     for(int j=0;j<N;++j){
        //         System.out.printf("%b ",check[i][j]);
        //     }
        //     System.out.println();
        // }
        // System.out.println();

        return true;
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<N) && (y<N);
    }
}
/**
 * 
 * 
3 1
0 0 2 2
0 1
0 0 0
0 0 0
0 0 0

9 3
3 0 7 0
2 4 4 4 6 4
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0

 */