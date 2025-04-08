import java.util.*;
import java.io.*;

public class Main {
    static int N,M,P,C,D,DIE;
    static int rx,ry;
    static int[] sx,sy,score,status;
    // 상, 우 , 하 , 좌
    static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
    static int[] rdx = {-1,0,1,0,-1,-1,1,1}, rdy = {0,1,0,-1,-1,1,-1,1};
    static int[][] map;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        DIE = 0;
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        rx = Integer.parseInt(st.nextToken());
        ry = Integer.parseInt(st.nextToken());
        sx = new int[P+1];
        sy = new int[P+1];
        score = new int[P+1];
        status = new int[P+1];
        map = new int[N+1][N+1];
        for(int i=1;i<=P;++i){
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            sx[id] = Integer.parseInt(st.nextToken()); 
            sy[id] = Integer.parseInt(st.nextToken());
            map[sx[id]][sy[id]] = id;
        }
        for(int t=1;t<=M;++t){
            if(DIE == P) break; // 산타가 모두 탈락했다면 즉시 종료 
            rMove();
            //printMap();
            sMove();
            //printMap();
            roundEnd();
        }
        for(int i=1;i<=P;++i){
            System.out.printf("%d ",score[i]);
        }
    }
    static void printMap(){
        int[][] temp = new int[N+1][N+1];
        for(int i=1;i<=N;++i){
            for(int j=1;j<=N;++j){
                temp[i][j] = map[i][j];
            }
        }
        temp[rx][ry] = -1;
        for(int i=1;i<=N;++i){
            for(int j=1;j<=N;++j){
                System.out.printf("%d ",temp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static int getDis(int x, int y, int r, int c){
        return (x - r)*(x - r) + (y - c)*(y - c);
    }
    static void rMove(){
        int minDis = 2 * N * N;
        int maxR = -1;
        int maxC = -1;
        int dir = -1;
        int targetId = -1;
        // 움직이기 전 가장 가까운 산타 찾기
        for(int p=1;p<=P;++p){
            if(status[p] == -1) continue;
            int x = sx[p];
            int y = sy[p];
            int dis = getDis(rx, ry, x, y);
            if(minDis > dis){
                minDis = dis;
                maxR = sx[p];
                maxC = sy[p];
                targetId = p;
            }else if(minDis == dis){
                if(maxR < sx[p]){
                    maxR = sx[p];
                    maxC = sy[p];
                    targetId = p;
                }else if(maxR == sx[p]){
                    if(maxC < sy[p]){
                        maxC = sy[p];
                        targetId = p;
                    }
                }
            }
        }
        int moveDis = minDis; // 움직였을때 산타와의 거리

        for(int i=0;i<8;++i){
            int nx = rx + rdx[i];
            int ny = ry + rdy[i];
            if(!checkRange(nx, ny)) continue;
            int x = sx[targetId];
            int y = sy[targetId];
            int dis = getDis(nx, ny, x, y);
            if(moveDis > dis){
                moveDis = dis;
                dir = i;
            }

        }
        if(dir != -1){
            rx = rx + rdx[dir];
            ry = ry + rdy[dir];
        }
        if(moveDis == 0){ //만약 가장 가까운 산타와의 거리가 0이면 충돌 발생
            crash(targetId, dir, C, false);
        }
    }
    static void sMove(){
        for(int i=1;i<=P;++i){
            if(status[i] != 0) continue; // 탈락, 기절
            int x = sx[i];
            int y = sy[i];
            int minDis = getDis(x, y, rx, ry);// 원래 루돌프와의 거리 계산
            int dir = -1; 
            //System.out.printf("%d번 산타\n",i);
            //System.out.println(minDis);
            for(int j=0;j<4;++j){
                int nx = x + dx[j];
                int ny = y + dy[j];
                if(!checkRange(nx, ny)) continue; // 격자 밖인 경우
                if(map[nx][ny] != 0) continue; // 산타가 있는경우
                int dis = getDis(nx, ny, rx, ry); // 새롭게 간 루돌프와의 거리
                //System.out.printf("dir : %d, dis : %d\n", j, dis);
                //System.out.printf("산타 이동 위치 : (%d, %d)\n", nx, ny);
                if(minDis > dis){ // 원래 거리보다 좁혀질 경우에만 움직임
                    minDis = dis;
                    dir = j;
                }
            }
            if(dir == -1) continue; // 움직이지 않았다면 다음번 산타 진행
            // 움직인 정보만 수정해주기
            sx[i] = x + dx[dir];
            sy[i] = y + dy[dir];
            map[x][y] = 0; // 기존 자리 없애고
            map[sx[i]][sy[i]] = i; // 새로운 자리로 이동
            if(minDis == 0) crash(i, getOD(dir), D, false); // 충돌했다면 반대 방향으로 산타가 움직이도록 충돌 함수 실행
        }

    }
    static void crash(int id, int dir, int mul, boolean check){ // 상호작용으로 발생한 것만 점수 추가 x
        if(!check) {
            score[id] += mul; // mul 
            status[id] = 2; // 일단 루돌프와의 충돌이니 기절시킥기
        }
        int x = sx[id];
        int y = sy[id];
        map[x][y] = 0; // 원래 있던 자리 비우기
        int nx = x + rdx[dir]*mul;
        int ny = y + rdy[dir]*mul;
        if(!checkRange(nx, ny)){ // 격자 밖으로 밀려났다면 
            status[id] = -1; // 탈락시키기
            DIE++;
        }else{
            sx[id] = nx;
            sy[id] = ny;
            if(map[nx][ny] != 0){ // 다른 산타와 충돌한다면 
                int nid = map[nx][ny];
                if(status[nid] != -1) // 거기에 있는 산타가 탈락하지 않았다면 충돌함수 실행 
                    crash(nid, dir, 1, true);
            }
            map[nx][ny] = id; // 이동한 자리 업데이트
        }

    }
    static void roundEnd(){
        for(int i=1;i<=P;++i){
            //System.out.printf("%d번 산타 상태 : %d\n", i, status[i]);
            if(status[i] != -1){
                score[i]++;
            }
            if(status[i] > 0){
                status[i]--;// 기절 시간 줄이기
            }
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=1) && (y>=1) && (x<=N) && (y<=N);
    }
    // 산타 반대방향 알려주기
    static int getOD(int dir){
        if(dir == 0){ // 상 
            return 2; // 하
        }else if(dir == 1){// 우
            return 3; // 좌
        }else if(dir == 2){
            return 0;
        }else return 1;
    }
}
