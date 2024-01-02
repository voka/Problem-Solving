import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Map
 * - Player Status (모두 다르고) 총이 없는 곳만 플레이어가 존재 
 * - Attack Point 이 공격력도 모두 다름 
 * - 플레이어 숫자
 * 
 * Round
 * - 1. Player가 향하고 있는 방향대로 한칸 전진 
 *      - 격자 밖으러 나가는 경우에는 반대 방향으로 바꾸어 1만큼 이동
 * - 2. 이동한 칸에 총, 플레이어가 있는지 확인
 *      1. 총이 있으면 총을 획득한다.. 
 *      - 이미 가지고 있는 경우 공격력을 비교해 더 좋은 총을 획득한다.
 *      - 나머지 총은 해당 격자에 그대로 둔다.
 *      2. 플레이어가 있으면 두 플레이어는 싸우게 된다 
 *      1. 이기는 조건 
 *          - 1. 초기 능력치 + 총의 공격력
 *          - 2. 초기 능력치
 *      2. 이긴다면 1번 조건의 차이 만큼 포인트 획득 (== 점수)
 * 
 *      3. 진 플레이어는 총을 내려놓고, 원래 향하던 방향으로 한칸 이동 
 *          - 1. 다른 플레이어가 있거나, 격자 범위 밖인 경우
 *              -  오른쪽으로 90씩 회전해 빈칸이 보이는 순간 이동 
 *          - 2. 총이 있다면 
 *              -  가장 공격력이 높은 총을 획득하고 나머지 총들을 격자에 내려놓은 
 *      4. 이긴 플레이어는 승리한 칸에 떨어진 총들 + 원래 들고 있던 총 중 가장 높은 총 획득, 나머지 총들은 해당 격자에 내려 놓는다.
 *      => 한칸에 여러개의 총이 놓아져 있을 수 있다. 
 * 
 */
class Point{
    PriorityQueue<Integer> guns;
    public Point(){
        guns = new PriorityQueue<>(Collections.reverseOrder());
    }
    public int getGuns(){
        if(guns.size() == 0){
            return -1; 
        }
        else return guns.poll();
    }
}

public class Main {
    static Point[][] gunMaps;
    static int[][] playerMaps;
    static int[] score; // 플레이어가 획득한 점수
    static int[] status,px,py; // 플레이어 초기 능력치
    static int[] pDir; // 플레이어 방향;
    static int[] pGun; // 플리이어별 사용하고 있는 총 
    static int n,m,k; // n: 격자 크기, m : 플레이어 수, k: 라운드 수
    static int[] dx = {0,1,0,-1}, dy = {-1,0,1,0}; // ↑, →, ↓, ←
    
    static boolean isRange(int x, int y){
        return ((x >= 0) && (x < n)) && ((y >= 0) && (y < n));
    }

    /** Player가 향하고 있는 방향대로 한칸 전진 
     *  - 격자 밖으러 나가는 경우에는 반대 방향으로 바꾸어 1만큼 이동
     * 이동한 칸에 총, 플레이어가 있는지 확인
     */
    static void go(int num){ // num : player 번호
        int cDir = pDir[num];
        int cx = px[num], cy = py[num];
        int nx = cx + dx[cDir], ny = cy + dy[cDir];
        if(!isRange(nx,ny)){ // 격자 밖으로 나갔다면
            // 좌표 바꾸기
            nx = cx + -1 * dx[cDir];
            ny = cy + -1 * dy[cDir];
            // 방향 바꿔주기
            if(cDir % 2 == 0) pDir[num] = (2 - cDir); // 0 ->2 , 2 -> 0
            else pDir[num] = (4 - cDir); // 1 -> 3 , 3 -> 1;
            cDir = pDir[num];
        }
        // 좌표 바꿔주기
        px[num] = nx; 
        py[num] = ny; 
        // player 맵 최신화
        playerMaps[cy][cx] = 0;
        if(playerMaps[ny][nx] == 0){ // 플레이어가 없는 곳이라면 -> 총만 교환
            changeGun(num);
        }
        else{ //player A, B fight
            int a = num, b = playerMaps[ny][nx];
            fight(a, b);
        }
    }
    static void changeGun(int num){
        int x = px[num], y = py[num];
        playerMaps[y][x] = num;
        int gun = gunMaps[y][x].getGuns(); // 그 좌표에 있는 가장 공격력이 높은 총 획득하기. 
        if(gun != -1){// 총이 있을 경우에만
            // 자기 총과 비교
            int curGun = pGun[num];
            if(curGun == 0){ // 총을 가지고 있지 않다면
                pGun[num] = gun; // 총 획득
            }
            else{ // 가지고 있다면 원래 총과 비교
                if(curGun > gun){
                    gunMaps[y][x].guns.offer(gun);
                }
                else{
                    gunMaps[y][x].guns.offer(curGun);
                    pGun[num] = gun; // 현재 총 내려놓고 새 총 획득
                }
            }

        }
    }
    /** fight A, B Player
     * @param a : A Plater num 
     * @param b : B Player num
     */
    static void fight(int a, int b){
        int aS = status[a], bS = status[b]; // 각 플레이어의 초기 능력치
        int aT = aS + pGun[a], bT = bS + pGun[b]; // 각 플레이어의 전투력
        if(aT == bT){ // 총합 능력치가 같은 경우 -> 더해지는 점수가 없음 
            if(aS > bS){ // a의 초기 능력치가 큰 경우 a는 제자리에, b는 움직임
                losePlayerMove(b);
                changeGun(a);
            }else{ // b의 초기 능력치가 큰 경우 b 는 제자리에, a는 움직임
                losePlayerMove(a);
                changeGun(b);
            }
        }else{
            if(aT > bT){
                losePlayerMove(b);
                changeGun(a);
                score[a] += (aT - bT);
            }else{
                losePlayerMove(a);
                changeGun(b);
                score[b] += (bT - aT);
            }

        }
    }
    /**
     * @param num : lose player number 
     */
    static void losePlayerMove(int num){
        // 총을 가지고 있다면 내려 놓는다.
        int curGun = pGun[num];
        int x = px[num], y = py[num]; 
        if(curGun != 0){
            gunMaps[y][x].guns.offer(curGun);
            pGun[num] = 0;
        }
        // if(num == 3){
        //     System.out.println(curGun);
        //     printAll();
        // }
        // 오른쪽으로 90도씩 플레이어가 보이지 않을때 까지 회전하며
        // 4번만 
        int iDir = pDir[num]; // 초기 방향
        for(int i=0;i<4;++i){
            int nDir = (iDir + i) % 4; // 다음 방향 
            int nx = x + dx[nDir], ny = y + dy[nDir];
            if(isRange(nx, ny) && playerMaps[ny][nx] == 0){
                pDir[num] = nDir;
                px[num] = nx;
                py[num] = ny;
                changeGun(num);
                break;
            } 
        }

    }
    static void process(){
        for(int i=1;i<=m;++i){
            //System.out.printf("\nMOVE : %d \n\n", i);
            go(i);
            //printAll();
        }
    }
    static int StoI(String i){
        return Integer.parseInt(i);
    }
    static void printAll(){
        System.out.println("GUN MAPS ===============");
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                int gun = gunMaps[i][j].getGuns();
                System.out.printf("%d ",gun);
                if(gun != -1) gunMaps[i][j].guns.offer(gun);
            }
            System.out.println();
        }
        System.out.println("\nPlayer MAPS ===============");
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ",playerMaps[i][j]);
            }
            System.out.println();
        }
        System.out.println("\nPlayer GUNS ===============");
        for(int i=1;i<=m;++i){
            System.out.printf("%d ", pGun[i]);
        }
        System.out.println("\nSCORE ===============");
        for(int i=1;i<=m;++i){
            System.out.printf("%d ",score[i]);
        }
        System.out.println("\nDIR ===============");
        for(int i=1;i<=m;++i){
            System.out.printf("%d ",pDir[i]);
        }
        System.out.println();
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        n = StoI(temp[0]);
        m = StoI(temp[1]);
        k = StoI(temp[2]);
        gunMaps = new Point[n][n];
        playerMaps = new int[n][n];
        score = new int[m+1];
        status = new int[m+1];
        px = new int[m+1];
        py = new int[m+1];
        pDir = new int[m+1];
        pGun = new int[m+1];
        for(int i=0;i<n;++i){
            temp = br.readLine().split(" ");
            for(int j=0;j<n;++j){
                int cur = StoI(temp[j]);
                gunMaps[i][j] = new Point();
                if(cur != 0){
                    gunMaps[i][j].guns.add(cur);
                }
            }
        }
        for(int i=0;i<m;++i){
            temp = br.readLine().split(" ");
            int y = StoI(temp[0]) - 1, x = StoI(temp[1]) - 1;
            int d = StoI(temp[2]), s = StoI(temp[3]);
            playerMaps[y][x] = i+1;
            px[i+1] = x;
            py[i+1] = y; 
            pDir[i+1] = d;
            status[i+1] = s;
        }
        //printAll();
        for(int i=0;i<k;++i){
            //System.out.printf("==============ROUND %d================\n\n", i+1);
            process();
        }
        for(int i=1;i<=m;++i){
            System.out.printf("%d ",score[i]);
        }
    }
    
}
/***
 * 
 * 5 4 3
1 2 0 1 2
1 0 3 3 1
1 3 0 2 3
2 1 2 4 5
0 1 3 2 0
1 3 2 3
2 2 1 5
3 3 2 2
5 1 3 4
 * 
 */