import java.io.*;
import java.util.*;
public class Main {
    static int MAX_MONSTER_NUM = 1000001;
    static int m, t, fx, fy;
    static int CUR_MONSTER = 0;// 이번 턴에 사는 몬스터 
    static int CUR_DIE_MONSTER = 0;// 이번 턴에 죽는 몬스터 
    static int CUR_COPY_MONSTER = 0;// 이번 턴에 복제되는 몬스터 수
    static int MONSTER = 0;// 현재 살아남는 몬스터 수
    static int[][] monsterMap = new int[4][4];//각 칸에 있는 몬스터 수
    static int[][] moveMap = new int[4][4];//이동이 끝난 뒤 각 칸에 있는 몬스터 수
    static int[][] dieMap = new int[4][4];// 시체가 처음 생기면 -> 2, 한 턴 종료할 때마다 -1씩/ 값이 0이면 시체가 없는 칸
    static int[] monsterDir, monsterX, monsterY;
    static int[] moveDir, moveX, moveY;
    static int[] mdx = {-1,-1,0,1,1,1,0,-1}, mdy = {0,-1,-1,-1,0,1,1,1}; // 상에서 왼쪽으로 45씩 
    static int[] fdx = {-1,0,1,0}, fdy = {0,-1,0,1}; //상, 좌, 하, 우 방 향
    static int[] fVisitX, fVisitY;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init();
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        fx = Integer.parseInt(st.nextToken())-1;
        fy = Integer.parseInt(st.nextToken())-1;
        MONSTER = m;
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            monsterX[i] = Integer.parseInt(st.nextToken()) -1;
            monsterY[i] = Integer.parseInt(st.nextToken())-1;
            monsterDir[i] = Integer.parseInt(st.nextToken())-1;
            monsterMap[monsterX[i]][monsterY[i]]++;// 몬스터 수 증가
        }
        //printMonster();
        for(int i=0;i<t;++i){
            // 이동했을때 map 초기화
            initMoveMap();
            // 1. 복제 시도
            printDieMap();
            // 2. 몬스터 이동
            moveMonster();
            printMonsterMoveMap();
            // 3. 팩맨 이동
            fMove();
            killMonster();
            // 4. 몬스터 시체 소멸
            dieUpdate();
            // 5. 몬스터 복제 완성
            System.out.printf("ROUND : %d END MONSTER : %d\n", i ,MONSTER);
            printMonster();
        }
        System.out.println(MONSTER);

    }
    static void initMoveMap(){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                moveMap[i][j] = 0;
            }
        }
    }
    static void killMonster(){
        int cnt = 0;
        for(int i=0;i<MONSTER;++i){
            int x = moveX[i];
            int y = moveY[i];
            // 몬스터가 살아 남았다면
            if(!monsterInfVisit(x,y)){
                System.out.printf("alive Monster x : %d, y : %d\n", x, y);
                monsterX[MONSTER + cnt] = x;
                monsterY[MONSTER + cnt] = y;
                monsterDir[MONSTER + cnt] = moveDir[i];
                cnt++;// 몬스터 수 증가
            }
        }
        MONSTER += cnt;
    }
    static boolean monsterInfVisit(int x, int y){
        for(int i=0;i<3;++i){
            if((fVisitX[i] == x) && (fVisitY[i] == y)) return true;
        }
        return false;
    }
    static boolean checkRange(int x, int y){
        return (x >=0) && (y>=0) && (x <4) && (y < 4);
    }
    static void moveMonster(){
        boolean move = false;
        for(int i=0;i<MONSTER;++i){
            int x = monsterX[i];
            int y = monsterY[i];
            int dir = monsterDir[i];
            for(int j=0;j<8;++j){ // 8방향 중 한 곳으로 이동
                int nDir = (j+dir)%8;
                int nx = x + mdx[nDir];
                int ny = y + mdy[nDir];
                if(checkRange(nx, ny) && dieMap[nx][ny] == 0 && isNotSame(nx, ny, fx, fy)){
                    move = true; // 이동에 성공
                    moveX[i] = nx;
                    moveY[i] = ny;
                    moveDir[i] = nDir;
                    moveMap[nx][ny]++;
                    break;
                }
            }
            if(!move){// 이동에 실패
                moveX[i] = x;
                moveY[i] = y;
                moveDir[i] = dir;
                moveMap[x][y]++;
            }
        }
    }
    static void fMove(){
        int MAX_EAT_MONSTER = -1;
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                for(int k=0;k<4;++k){
                    // i: 첫번째 이동 방향, j: 두번째 이동방향, k: 세번째 이동방향
                    int x1 = fx + fdx[i];
                    int y1 = fy + fdy[i];
                    int x2 = x1 + fdx[j];
                    int y2 = y1 + fdy[j];
                    int x3 = x2 + fdx[k];
                    int y3 = y2 + fdy[k];
                    // 가장 몬스터를 많이 먹을 수 있는 경로 탐색 -> 같은 지점 두번 방문 X
                    if(checkRange(x1, y1) && checkRange(x2, y2) && checkRange(x3, y3)){
                        int cur = moveMap[x1][y1] + moveMap[x2][y2] + moveMap[x3][y3];
                        if(!isNotSame(x1,y1,x2,y2)) cur -= moveMap[x1][y1];
                        if(!isNotSame(x1,y1,x3,y3)) cur -= moveMap[x1][y1];
                        if(!isNotSame(x2,y2,x3,y3)) cur -= moveMap[x2][y2];
                        if(MAX_EAT_MONSTER < cur){
                            MAX_EAT_MONSTER = cur;
                            fVisitX[0] = x1;
                            fVisitX[1] = x2;
                            fVisitX[2] = x3;
                            fVisitY[0] = y1;
                            fVisitY[1] = y2;
                            fVisitY[2] = y3;
                            
                        }
                    }
                }
            }
        }
        // 팩맨 정보 업데이트
        fx = fVisitX[2];
        fy = fVisitY[2];
        // 현재 턴에 사라질 몬스터 수 검색
        CUR_DIE_MONSTER = MAX_EAT_MONSTER;
        // 몬스터 시체 생성
        //System.out.print("f root => ");
        for(int i=0;i<3;++i){
            int x = fVisitX[i];
            int y = fVisitY[i];
            if(moveMap[x][y] > 0) dieMap[x][y] = 3;
            System.out.printf("(%d, %d) => ", x,y);
        }
        System.out.println();
    }
    static void dieUpdate(){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                if(dieMap[i][j]>0) dieMap[i][j] -= 1;
            }
        }
    }
    static boolean isNotSame(int x, int y, int a, int b){
        return !((x == a) && (y == b));
    }
    static void init(){
        monsterDir = new int[MAX_MONSTER_NUM];
        monsterX = new int[MAX_MONSTER_NUM];
        monsterY = new int[MAX_MONSTER_NUM];
        moveDir = new int[MAX_MONSTER_NUM];
        moveX = new int[MAX_MONSTER_NUM];
        moveY = new int[MAX_MONSTER_NUM];
        fVisitX = new int[3];
        fVisitY = new int[3];
    }
    static void printMonster(){
        System.out.println("MONSTER");
        int[][] temp = new int[4][4];
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                for(int k=0;k<MONSTER;++k){
                    if(!isNotSame(i, j, monsterX[k], monsterY[k])){
                        //System.out.printf("%d, %d\n", monsterX[k], monsterY[k]);
                        temp[i][j]++;
                    }
                }
                System.out.printf("%d ",temp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void printMonsterMap(){
        
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                System.out.printf("%d ", monsterMap[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void printMonsterMoveMap(){
        System.out.println("MOVE");
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                System.out.printf("%d ", moveMap[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void printDieMap(){
        System.out.println("DIE");
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                System.out.printf("%d ", dieMap[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
/*
 살아있는 몬스터의 수가 100만이 넘어가지 않는다. -> 100만 몬스터 x 64 방향 -> 6400만 < 1억  -> 완전탐색 해도 된다.
 * 
 * 
 1. 이동할 방향이 없는 몬스터가 정말로 이동하지 않는지
 2. 팩맨이 같은 수의 몬스터를 잡아 먹을 수 있을 때, 상 - 하 - 좌 - 우의 우선순우위로 움직이는 지
 3. 시체가 2턴 동안 남아 있는지
 4. 팩맨이 알은 섭취하지 않는지
 * 
 */
