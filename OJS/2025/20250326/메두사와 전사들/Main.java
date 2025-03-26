import java.io.*;
import java.util.*;

class Pair{
    int x;
    int y;
    int c;
    public Pair(int x, int y, int c){
        this.x = x;
        this.y = y;
        this.c = c;
    }
}
public class Main {
    static int N,M,mx,my,md,sx,sy,ex,ey,ms; 
    static int[] ax,ay,as;
    static int[][] smap, map, cost;
    static int[][][] vmap, svmap;

    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    static int[] sdx = {0,0,-1,1}, sdy = {-1,1,0,0};

    static int aWalk, aEnd, aStone; 
    static boolean end = false;
    static Stack<Pair> stack;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        // 전사관련
        ax = new int[M];
        ay = new int[M];
        as = new int[M];
        // 도로 관련
        smap = new int[N][N];
        map = new int[N][N];
        cost = new int[N][N];
        
        stack = new Stack<>();

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken());
        sy = Integer.parseInt(st.nextToken());
        ex = Integer.parseInt(st.nextToken());
        ey = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        mx = sx; my = sy;
        for(int i=0;i<M;++i){
            ax[i] = Integer.parseInt(st.nextToken());
            ay[i] = Integer.parseInt(st.nextToken());
            as[i] = 1; // 정상 상태
        }

        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // Please write your code here.
        if(!canGo()){
            System.out.println(-1);
        }else{ // 시뮬 시작
            //int t = 1;
            while(!end){
                //System.out.printf("\n%d번째 턴\n",t);
                aWalk = 0; // 이번 턴에 모든 전사가 이동한 거리의 합
                aStone = 0;
                aEnd = 0; // 이번 턴에 메두사를 공격한 전사의 수
                // 1. 메두사 이동 (이동방향 정하기 -> 이동하기)
                if(mMove()) break; // 메두사가 공원에 도착했다면 시뮬 종료
                // 2. 메두사 시선
                mSee();
                // 3,4. 전사들의 이동 및 공격
                aMove();
                // 출력
                System.out.printf("%d %d %d\n", aWalk, aStone, aEnd);    
                // 돌이된 전사 돌 해제
                for(int i=0;i<M;++i){
                    if(as[i] == 0) as[i] = 1;
                }
                //if(true) break;
                //printMmap(); 
                //t++;
            }
            System.out.println(0);

        }
    }
    static boolean mMove(){
        //System.out.printf("메두사 이동 : (%d,%d) -> (%d,%d)\n\n",mx,my,p.x,p.y);

        for(int i=0;i<4;++i){
            int nx = mx + dx[i];
            int ny = my + dy[i];
            if(checkRange(nx, ny) && (cost[nx][ny] < cost[mx][my])){
                mx = nx;
                my = ny;
                md = i;
                break;
            }
        }

        // 목적지에 도착했다면 탐색 종료
        if(mx == ex && my == ey) return true;
        // 메두사 위치와 동일한 전사 있는지 탐색
        for(int i=0;i<M;++i){
            if(getDis(mx,my,ax[i],ay[i]) == 0){
                as[i] = -1; // 동일한 위치라면 죽이기
            }
        }
        return false;
    }

    static void makeMSeeMap(int dir){

        int x = mx;
        int y = my;
        int diff = 1;

        while(checkRange(x, y)){
            x += dx[dir];
            y += dy[dir];
            if(dir < 2){ // 상, 하 방향
                int cy = y - diff;
                for(int i=0;i<diff*2 + 1;++i){
                    if(checkRange(x, cy)){
                        vmap[dir][x][cy] = 1;
                    }
                    cy++;
                }
                diff++;
            }else{ // 좌, 우 방향
                int cx = x - diff;
                for(int i=0;i<diff*2 + 1;++i){
                    if(checkRange(cx, y)){
                        vmap[dir][cx][y] = 1;
                    }
                    cx++;
                }
                diff++;
            }
        }
    }
    static void makeSSeeMap(int dir){
        for(int i=0;i<M;++i){ // 이미 사라진 전사를 제외하고
            if(as[i] == -1) continue;
            if(vmap[dir][ax[i]][ay[i]] == 1){
                int cx = ax[i]; 
                int cy = ay[i];
                // 위치 관계 파악
                if(cx == mx){
                    while(checkRange(cx, cy)){
                        cy += dy[dir];
                        if(checkRange(cx, cy)){
                            svmap[dir][cx][cy] = 1;
                        }
                    }
                }else if(cy == my){
                    while(checkRange(cx, cy)){
                        cx += dx[dir];
                        if(checkRange(cx, cy)){
                            svmap[dir][cx][cy] = 1;
                        }
                    }
                }
                // 같은 x축, y축 위에 존재하지 않는다면
                // 1. 왼쪽 대각선 위
                if(cx < mx && cy < my){
                    if(dir == 0){ // 위쪽 방향이면
                        int diff = 2;
                        while(checkRange(cx, cy)){
                            cx += dx[dir];
                            int y = cy;
                            for(int j=0;j<diff;++j){
                                if(checkRange(cx, y)){
                                    svmap[dir][cx][y] = 1;
                                }
                                y--;
                            }
                            diff++;
                        }

                    }else{// 왼쪽 방향이면
                        int diff = 2;
                        while(checkRange(cx, cy)){
                            cy += dy[dir];
                            int x = cx;
                            for(int j=0;j<diff;++j){
                                if(checkRange(x, cy)){
                                    svmap[dir][x][cy] = 1;
                                }
                                x--;
                            }
                            diff++;
                        }
                    }
                }
                // 2. 오른쪽 대각선 위
                if(cx < mx && cy > my){
                    if(dir == 0){ // 위쪽 방향이면
                        int diff = 2;
                        while(checkRange(cx, cy)){
                            cx += dx[dir];
                            int y = cy;
                            for(int j=0;j<diff;++j){
                                if(checkRange(cx, y)){
                                    svmap[dir][cx][y] = 1;
                                }
                                y++;
                            }
                            diff++;
                        }

                    }else{// 오흔쪽 방향이면
                        int diff = 2;
                        while(checkRange(cx, cy)){
                            cy += dy[dir];
                            int x = cx;
                            for(int j=0;j<diff;++j){
                                if(checkRange(x, cy)){
                                    svmap[dir][x][cy] = 1;
                                }
                                x--;
                            }
                            diff++;
                        }
                    }
                }
                // 3. 오른쪽 대각선 아래
                if(cx > mx && cy > my){
                    if(dir == 1){ // 아래쪽 방향이면
                        int diff = 2;
                        while(checkRange(cx, cy)){
                            cx += dx[dir];
                            int y = cy;
                            for(int j=0;j<diff;++j){
                                if(checkRange(cx, y)){
                                    svmap[dir][cx][y] = 1;
                                }
                                y++;
                            }
                            diff++;
                        }

                    }else{// 오흔쪽 방향이면
                        int diff = 2;
                        while(checkRange(cx, cy)){
                            cy += dy[dir];
                            int x = cx;
                            for(int j=0;j<diff;++j){
                                if(checkRange(x, cy)){
                                    svmap[dir][x][cy] = 1;
                                }
                                x++;
                            }
                            diff++;
                        }
                    }
                }
                // 4. 왼쪽 대각선 아래
                if(cx > mx && cy < my){
                    if(dir == 1){ // 아래쪽 방향이면
                        int diff = 2;
                        while(checkRange(cx, cy)){
                            cx += dx[dir];
                            int y = cy;
                            for(int j=0;j<diff;++j){
                                if(checkRange(cx, y)){
                                    svmap[dir][cx][y] = 1;
                                }
                                y--;
                            }
                            diff++;
                        }

                    }else{// 왼쪽 방향이면
                        int diff = 2;
                        while(checkRange(cx, cy)){
                            cy += dy[dir];
                            int x = cx;
                            for(int j=0;j<diff;++j){
                                if(checkRange(x, cy)){
                                    svmap[dir][x][cy] = 1;
                                }
                                x++;
                            }
                            diff++;
                        }
                    }
                }
            }

        }
    }
    static void print2DWithDir(int dir, int[][][] arr){

        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                System.out.printf("%d ", arr[dir][i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void mSee(){

        vmap = new int[4][N][N];
        svmap = new int[4][N][N];
        
        // 1. 상, 하, 좌, 우 방향으로 볼때 어느 방향이 전사가 많은지 확인하기
        int max = -1;
        ms = 0;
        for(int i=0;i<4;++i){
            makeMSeeMap(i);
            makeSSeeMap(i);
            int cur = 0;
            for(int j=0;j<M;++j){
                if(as[j] == -1) continue;
                int x = ax[j];
                int y = ay[j];
                if(vmap[i][x][y] == 1 && svmap[i][x][y] != 1){
                    cur++;
                }
            }
            if(cur > max){
                max = cur;
                ms = i;
            }
        }
        // 위에서 정해진 방향으로 바라보며 전사 돌로 만들기
        for(int j=0;j<M;++j){
            if(as[j] == -1) continue;
            int x = ax[j];
            int y = ay[j];
            if(vmap[ms][x][y] == 1 && svmap[ms][x][y] != 1){
                as[j] = 0;
            }
        }


        //System.out.println("메두사의 시선");
        //print2DWithDir(ms, vmap);
        //print2DWithDir(ms, svmap);
        //상
        // makeMSeeMap(0);
        // print2DWithDir(0,vmap);
        // makeSSeeMap(0);
        // print2DWithDir(0, svmap);

        // 하
        // makeMSeeMap(1);
        // print2DWithDir(1,vmap);
        // makeSSeeMap(1);
        // print2DWithDir(1, svmap);

        // // 좌
        // makeMSeeMap(2);
        // print2DWithDir(2,vmap);
        // makeSSeeMap(2);
        // print2DWithDir(2, svmap);

        // 우
        // makeMSeeMap(3);
        // print2DWithDir(3,vmap);
        // makeSSeeMap(3);
        // print2DWithDir(3, svmap);

    }
    static void printMmap(){
        System.out.println("전사의 위치");
        int[][] tmap = new int[N][N];
        for(int i=0;i<M;++i){
            if(as[i] == -1) continue;
            tmap[ax[i]][ay[i]] = i+1;
        }

        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                System.out.printf("%d ",tmap[i][j]);
            }
            System.out.println();
        }
    }
    static void aMove(){
        int minDis,re,nx,ny,dis;
        for(int i=0;i<M;++i){
            if(as[i] == -1) continue;
            if(as[i] == 0) aStone++;
            if(as[i] == 1){ // 전사가 정상이라면
                // 1번 이동
                minDis = getDis(ax[i], ay[i], mx, my); // 현재 위치 부터 메두사 까지의 거리
                re = -1;
                boolean meet = false;
                for(int j=0;j<4;++j){
                    nx = ax[i] + dx[j];
                    ny = ay[i] + dy[j];
                    if(!checkRange(nx,ny)) continue; // 격자밖
                    if(vmap[ms][nx][ny] == 1 && svmap[ms][nx][ny] != 1) continue; // 메두사의 시선 
                    dis = getDis(nx,ny,mx,my);
                    if(dis == 0){ // 메두사와 만났다면
                        meet = true;
                        re = j;
                        break;
                    }
                    if(minDis > dis){
                        minDis = dis;
                        re = j;
                    }
                }
                if(re != -1){
                    //System.out.printf("%d번 전사의 1번 이동 : (%d,%d) -> ", i+1, ax[i],ay[i]);
                    ax[i] = ax[i] + dx[re];
                    ay[i] = ay[i] + dy[re];
                    aWalk++;
                    //System.out.printf("(%d,%d)\n", ax[i],ay[i]);
                }
                if(!meet && re != -1){ // 만나지 않았고, 1번 이동을 했다면 2번 이동
                    minDis = getDis(ax[i], ay[i], mx, my); // 현재 위치 부터 메두사 까지의 거리
                    re = -1;
                    for(int j=0;j<4;++j){
                        nx = ax[i] + sdx[j];
                        ny = ay[i] + sdy[j];
                        if(!checkRange(nx,ny)) continue; // 격자밖
                        if(vmap[ms][nx][ny] == 1 && svmap[ms][nx][ny] != 1) continue; // 메두사의 시선 
                        dis = getDis(nx,ny,mx,my);
                        if(dis == 0){ // 메두사와 만났다면
                            meet = true;
                            re = j;
                            break;
                        }
                        if(minDis > dis){
                            minDis = dis;
                            re = j;
                        }
                    }
                    if(re != -1){
                        //System.out.printf("2번 이동 : (%d,%d) -> ", ax[i],ay[i]);
                        ax[i] = ax[i] + sdx[re];
                        ay[i] = ay[i] + sdy[re];
                        aWalk++;
                        //System.out.printf("(%d,%d)\n", ax[i],ay[i]);
                    }
                }
                if(meet) {// 만났다면 
                    aEnd++; //메두사 공격 전사 수 증가
                    as[i] = -1; // 전사 죽음
                }
            }
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x < N) && (y<N);
    }
    static boolean canGo(){
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                if(map[i][j] == 1) cost[i][j] = Integer.MAX_VALUE;
                else cost[i][j] = -1;
            }
        }
        cost[ex][ey] = 0;
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(ex,ey,0));
        while(!q.isEmpty()){
            Pair cur = q.poll();
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx,ny) && cost[nx][ny] == -1){
                    cost[nx][ny] = cost[cur.x][cur.y]+1;
                    q.add(new Pair(nx,ny,-1));
                }
            }
        }
        if(cost[sx][sy] != -1){
            // 최단 경로 설정하기
            // int cnt = cost[ex][ey];
            // int cx = ex;
            // int cy = ey;
            // int cd = -1;
            // while(cnt > 0){
            //     cnt--;
            //     for(int i=3;i>=0;--i){
            //         int nx = cx + dx[i];
            //         int ny = cy + dy[i];
            //         if(checkRange(nx, ny) && cost[nx][ny] == cnt){
            //             if(i < 2){
            //                 cd = 1-i;
            //             }else if(i == 2){
            //                 cd = 3;
            //             }else{
            //                 cd = 2;
            //             }
            //             stack.add(new Pair(cx, cy, cd));
            //             cx = nx;
            //             cy = ny;
            //             break;
            //         }
            //     }

            // }
            return true;
        }else return false;
    }
    static int getDis(int x1, int y1, int x2, int y2){
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}

/**
 * 
 * 
 *
 
최단경로 확인용
5 1
0 0 4 4
2 2 
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0


 * 
// 위쪽 확인용
6 3
5 3 1 2
2 2 3 3 2 4
0 0 0 0 1 0
0 1 0 0 1 1
1 1 0 0 0 0
0 0 1 0 1 1
0 0 0 0 0 0
0 0 0 0 1 1

// 아래쪽 확인용
6 3
0 3 1 2
2 2 3 3 2 4
0 0 0 0 1 0
0 1 0 0 1 1
1 1 0 0 0 0
0 0 1 0 1 1
0 0 0 0 0 0
0 0 0 0 1 1

// 오른쪽 확인용
7 3
3 0 1 2
3 2 2 3 4 3
0 0 0 0 1 0 0
0 1 0 0 1 1 0
1 1 0 0 0 0 0
0 0 1 0 1 1 0
0 0 0 0 0 0 0
0 0 0 0 1 1 0
0 0 0 0 0 0 0

// 왼쪽 확인용
7 3
4 6 1 2
3 2 2 3 4 3
0 0 0 0 1 0 0
0 1 0 0 1 1 0
1 1 0 0 0 0 0
0 0 1 0 1 1 0
0 0 0 0 0 0 0
0 0 0 0 1 1 0
0 0 0 0 0 0 0

 */