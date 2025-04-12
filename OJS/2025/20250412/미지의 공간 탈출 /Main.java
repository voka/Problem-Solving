import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Point{
    int x; 
    int y;
    int t;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Point(int x, int y, int t){
        this.x = x;
        this.y = y;
        this.t = t;
    }
}

public class Main {
    static int N, M, F;
    static int[] fr,fc,fd,fv,fs;
    //타임머신 위치, 시간의 벽중 탈출구가 존재하는 방향
    static int tx,ty,td;
    //미지의 공간 출구-> 탈출구
    static int sx,sy,ex,ey;
    static int[][] map, time2DMap;
    static int[][][] timeMap;
    static boolean[][] checkMap;
    
    //시간의 벽 시작 위치 및 시간의벽과 미지의 공간 사이 이어지는 하나의 좌표
    static int tsx, tsy, tex, tey;
    // 상하좌우
    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        F = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        boolean check = false;
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                int c = Integer.parseInt(st.nextToken());
                if(c == 4){
                    ex = i;
                    ey = j;
                }else if (!check && c == 3){
                    tsx = i;
                    tsy = j;
                    check = true;
                }
                map[i][j] = c;
            }
        }
        timeMap = new int[5][M][M];
        for(int t=0;t<5;++t){
            for(int i=0;i<M;++i){
                st = new StringTokenizer(br.readLine());
                for(int j=0;j<M;++j){
                    int c = Integer.parseInt(st.nextToken());
                    timeMap[t][i][j] = c;
                }
            }
        }
        fr = new int[F];
        fc = new int[F];
        fd = new int[F];
        fv = new int[F];
        fs = new int[F];
        checkMap = new boolean[N][N];
        for(int i=0;i<F;++i){
            st = new StringTokenizer(br.readLine());
            fr[i] = Integer.parseInt(st.nextToken());
            fc[i] = Integer.parseInt(st.nextToken());
            fd[i] = Integer.parseInt(st.nextToken());
            fv[i] = Integer.parseInt(st.nextToken());
            checkMap[fr[i]][fc[i]] = true; // 기본적으로 시작할때 있는지 확인
        }
        findTimeOutPoint();
        if(checkMap[tex][tey]){// 탈출가능한 유일한 장소에 이상현상이 있는 경우
            System.out.println(-1);
            return;
        }
        int turn = findTimePath();
        if(turn == -1){ // 시간의 벽에서 탈출할 수 없다면
            System.out.println(-1);
            return;
        }
        int MAX = Integer.MAX_VALUE;
        boolean[][] visit = new boolean[N][N];
        int[][] cost = new int[N][N];
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                cost[i][j] = MAX;
            }
        }
        for(int i=tsx;i<tsx+M;++i){
            for(int j=tsy;j<tsy+M;++j){
                visit[i][j] = true; //시간의 벽 공간은 이미 지나온 공간임
            }
        }
        //System.out.println("본격 길찾기 시작");
        Queue<Point> q = new ArrayDeque<>();
        int t = 0;
        for(t = 1; t<=turn+1;++t){
            spread(t);// turn+1 수 동안 발생 시킨 이상현상 지도 그리기
        }
        // 현재 시점 기준 탈출 가능한 유일한 곳에 만약 이상현상이 있다면 탈출 불가 판정
        if(checkMap[tex][tey]){
            System.out.println(-1);
            return;
        }
        visit[tex][tey] = true;
        cost[tex][tey] = t;
        q.add(new Point(tex,tey,t+1));// (tex,tey)까지 오는데 turn+1 시간이 걸렸다
        boolean out = false;
        while(!q.isEmpty()){
            if(out) break;
            // 여기서 부터 길찾기 시작 
            Point p = q.poll();
            if(t < p.t){ // 새로운 턴 시작할때만
                t = p.t;
                spread(t); // 확산 시키기
            }
            for(int i=0;i<4;++i){
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if(!checkRange(nx, ny)) continue; // 범위 밖
                if(checkMap[nx][ny]) continue; // 이상현상 있는 곳
                if(visit[nx][ny]) continue; // 이미 방문한적 있는 곳
                if(map[nx][ny] == 1) continue; // 장애물이 있는 곳
                cost[nx][ny] = t-1;
                visit[nx][ny] = true;
                if(nx == ex && ny == ey){
                    out = true;
                    break;
                }
                q.add(new Point(nx, ny, t+1));
            }
        }
        // 디버그 출력용
        // for(int i=0;i<N;++i){
        //     for(int j=0;j<N;++j){
        //         System.out.printf("%d ",cost[i][j] != MAX ? cost[i][j] : -1);
        //     }
        //     System.out.println();
        // }
        if(cost[ex][ey] == MAX){
            System.out.println(-1);
        }else{
            System.out.println(cost[ex][ey]);
        }
        

    }    
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<N) && (y<N);
    }
    static void spread(int t){
        //System.out.printf("%d번째 턴\n",t);
        int[] tdx = {0,0,1,-1}, tdy = {1,-1,0,0};
        for(int i=0;i<F;++i){
            //System.out.printf("%d번 확산 상태:%d\n", i,fs[i]);
            if(fs[i] == -1) continue;// 확산 중지된 놈일 경우
            if(t % fv[i] != 0) continue; // 이번 턴이 v로 나누어 떨어지지 않는경우
            int nx = fr[i] + tdx[fd[i]];
            int ny = fc[i] + tdy[fd[i]];
            if(!checkRange(nx, ny) || map[nx][ny] == 1 || (nx == ex && ny == ey)){
                fs[i] = -1;
                continue;
            }
            fr[i] = nx;
            fc[i] = ny;
            //이상 현상 확산
            checkMap[nx][ny] = true;
        }
    

        // for(int i=0;i<N;++i){
        //     for(int j=0;j<N;++j){
        //         System.out.printf("%b ",checkMap[i][j]);
        //     }
        //     System.out.println();
        // }
    }
    static void findTimeOutPoint(){
        Queue<Point> q = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];
        q.add(new Point(tsx,tsy));
        visit[tsx][tsy] = true;
        boolean check = false;
        while(!q.isEmpty()){
            Point p = q.poll();
            if(check) break;
            for(int i=0;i<4;++i){
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if(visit[nx][ny]) continue;
                if(map[nx][ny] == 1) continue;
                if(map[nx][ny] == 0){
                    check = true;
                    tex = nx;
                    tey = ny;
                    td = i;
                    break;
                }
                visit[nx][ny] = true;
                q.add(new Point(nx, ny));
            }
            
        }
        
        // System.out.printf("out : (%d,%d), dir : %d\n", tex, tey, td);

    }
    static int findTimePath(){
        time2DMap = makeTime2DMap();
        //시간의 벽 출구 지정
        int tox=-1,toy=-1;
        if(td == 0){ // 위쪽 벽면
            tox = 0;
            toy = M + tey - tsy;
        }else if(td == 1){ // 아래쪽 벽면
            tox = 3*M-1;
            toy = M + tey - tsy;
        }else if(td == 2){ // 왼쪽 벽면
            toy = 0;
            tox = M + tex - tsx;
        }else{ // 오른쪽 벽면
            toy = 3*M-1; 
            tox = M + tex - tsx;
        }

        // System.out.printf("타임머신 위치 (%d,%d)\n",tx,ty);
        // System.out.printf("탈출구 위치 (%d,%d)\n", tox,toy);

        //중간에 이어지는 좌표 만들기
        int[][] nextX = new int[M*3][M*3];
        int[][] nextY = new int[M*3][M*3];
        // 윗면 (x -> 0 ~ M,  y -> M, 2*M - 1)
        for(int x=0;x<M;++x){
            //왼쪽
            nextX[x][M] = M;
            nextY[x][M] = x;

            nextX[M][x] = x;
            nextY[M][x] = M;

            //오른쪽
            nextX[x][2*M-1] = M;
            nextY[x][2*M-1] = 3*M-1-x;

            nextX[M][3*M-1-x] = x;
            nextY[M][3*M-1-x] = 2*M-1;
        }
        // 아랫면 (x -> 2*M ~ 3*M, y -> M, 2*M -1)
        for(int x=2*M;x<3*M;++x){
            // 왼쪽
            nextX[x][M] = 2*M-1;
            nextY[x][M] = 3*M - x - 1;

            nextX[2*M-1][3*M-x-1] = x;
            nextY[2*M-1][3*M-x-1] = M;

            // 오른쪽
            nextX[x][2*M-1] = 2*M-1;
            nextY[x][2*M-1] = x;

            nextX[2*M-1][x] = x;
            nextY[2*M-1][x] = 2*M-1;
        }
        /// 디버그 출력용
        // for(int i=0;i<3*M;++i){
        //     for(int j=0;j<3*M;++j){
        //         System.out.printf("(%d,%d) -> (%d,%d) ",i,j,nextX[i][j],nextY[i][j]);
        //     }
        //     System.out.println();
        // }
        int MAX = Integer.MAX_VALUE;
        boolean[][] visit = new boolean[M*3][M*3];
        int[][] cost = new int[M*3][M*3];
        for(int i=0;i<M*3;++i){
            for(int j=0;j<M*3;++j){
                cost[i][j] = MAX;
            }
        }
        visit[tx][ty] = true;
        Queue<Point> q = new ArrayDeque<>();
        q.add(new Point(tx, ty));
        cost[tx][ty] = 0;
        
        boolean out = false;
        while(!q.isEmpty()){
            Point p = q.poll();
            if(out) break;
            for(int i=0;i<4;++i){
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if(!timeCheckRange(nx, ny)) continue;
                if(time2DMap[nx][ny] == -1){
                    nx = nextX[p.x][p.y];
                    ny = nextY[p.x][p.y];
                }
                if(time2DMap[nx][ny] == 1) continue;
                if(visit[nx][ny]) continue;
                cost[nx][ny] = cost[p.x][p.y]+1;
                visit[nx][ny] = true;
                q.add(new Point(nx,ny));
                if(nx == tox && ny == toy){
                    out = true;
                    break;
                }
            }
        }
        // 디버그 출력용
        // for(int i=0;i<3*M;++i){
        //     for(int j=0;j<3*M;++j){
        //         System.out.printf("%d ",cost[i][j] != MAX ? cost[i][j] : -1);
        //     }
        //     System.out.println();
        // }
        if(!out){
            return -1;
        }else{
            return cost[tox][toy];
        }
        

    }
    static boolean timeCheckRange(int x, int y){
        return (x>=0) && (y>=0) && (x<3*M) && (y<3*M); 
    }
    static int[][] makeTime2DMap(){
        int[][] result = new int[M*3][M*3];
        for(int i=0;i<3*M;++i){
            for(int j=0;j<3*M;++j){
                result[i][j] = -1; // 여기는 지나가도 턴을 쓰지 않는다.
            }
        }
        // 윗면 먼저 옮기기
        for(int i=M;i<2*M;++i){
            for(int j=M;j<2*M;++j){
                result[i][j] = timeMap[4][i-M][j-M];
                if(result[i][j] == 2){
                    tx = i;
                    ty = j;
                }
            }
        }
        // 아래쪽 옮기기
        for(int i=2*M;i<3*M;++i){
            for(int j=M;j<2*M;++j){
                result[i][j] = timeMap[2][i-2*M][j-M];

            }
        }
        // 위쪽 옮기기
        for(int i=0;i<M;++i){
            for(int j=M;j<2*M;++j){
                result[i][j] = timeMap[3][M-i-1][2*M-j-1];

            }
        }
        //오른쪽 옮기기
        for(int i=M;i<2*M;++i){
            for(int j=2*M;j<3*M;++j){
                result[i][j] = timeMap[0][j-2*M][2*M-i-1];
            }
        }
        //왼쪽 옮기기
        for(int i=M;i<2*M;++i){
            for(int j=0;j<M;++j){
                result[i][j] = timeMap[1][M-j-1][i-M];
            }
        }
        /// 디버그 출력용
        // for(int i=0;i<3*M;++i){
        //     for(int j=0;j<3*M;++j){
        //         System.out.printf("%d ",result[i][j]);
        //     }
        //     System.out.println();
        // }

        return result;
    }
}
