import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m, h, k, tx, ty, td = 0, tcnt = 0, tDis = 1, curDis = 1, tScore = 0, mdie = 0;
    static int[] dx = {0,0,1,-1}, dy = {1,-1,0,0}, tdx = {-1,0,1,0}, tdy = {0,1,0,-1};
    static int[][] board,tree;
    static int[] mx,my,md,imd;
    static boolean[] mStatus;
    static boolean tStatus = false; // 돌아오는 방향인지 확인하는 부분

    static boolean checkRange(int x, int y){
        return (x >= 1) && (y >=1) && (x <= n) && ( y <= n);
    }
    static int getDistance(int x, int y){ // Input 도망자의 위치
        return Math.abs(x - tx) + Math.abs(y - ty); // Output 술래와 도망자의 거리
    }
    static boolean isThereTarget(int x, int y){
        return (tx == x) && (ty == y);
    }
    static void moveM(int i){
        int nx = mx[i] + dx[md[i]];
        int ny = my[i] + dy[md[i]];
        if(checkRange(nx, ny) && isThereTarget(nx, ny)){ // 술래가 움직이려는 칸에 있다면 움직이지 않는다.
            return;
        }
        int k = -1;
        if(md[i] >= 2) k = 5;
        else k = 1;
        int rnx = mx[i] + dx[k - md[i]]; // 0 -> 1 / 1 -> 0
        int rny = my[i] + dy[k - md[i]]; // 3 -> 2 / 2 -> 3 
        //System.out.printf("%d, %d, %d, %d, %d\n\n", i, nx, ny, rnx, rny);
        if(checkRange(nx, ny) && !isThereTarget(nx, ny)){ // 원래 방향대로 움직일 수 있다면
            // 위치 업데이트
            mx[i] = nx;
            my[i] = ny;
        }else if(checkRange(rnx, rny) && !isThereTarget(rnx, rny)){ // 반대편 방향으로 움직일 수 있다면
            // 위치 업데이트
            mx[i] = rnx;
            my[i] = rny;
            // 방향 업데이트
            md[i] = k - md[i];

        }
        
    }
    static int checkThereisM(int x, int y){
        int cnt = 0;
        for(int i=0;i<m;++i){
            if((!mStatus[i]) && (mx[i] == x) && (my[i] == y)){
                mStatus[i] = true;
                cnt++;
                mdie++;
            }
        }
        return cnt;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        board = new int[n+1][n+1];
        tree = new int[n+1][n+1];
        mStatus = new boolean[m+1];
        mx = new int[m+1];
        my = new int[m+1];
        md = new int[m+1];
        imd = new int[m+1];
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            mx[i] = Integer.parseInt(st.nextToken());
            my[i] = Integer.parseInt(st.nextToken());
            imd[i] = Integer.parseInt(st.nextToken());
            if(imd[i] == 1) md[i] = 0; // 오른쪽을 보도록
            else md[i] = 2; // 아래쪽을 보도록
        }
        for(int i=0;i<h;++i){
            st = new StringTokenizer(br.readLine());
            tree[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = 1;
        }
        tx = n/2 + 1;
        ty = n/2 + 1;
        int t = 0;
        //printM();
        while(k>0){
            if(mdie == m) break;
            t++;
            k--;
            //1 . 술래와의 거리가 3이하인 도망자들이 먼저 움직임
            for(int i=0;i<m;++i){
                if(!mStatus[i] && getDistance(mx[i], my[i]) <= 3){
                    moveM(i);
                }
            }
            //2. 술래의 움직임 
            tMove();
            //System.out.printf("time ===> %d\n", t);
            //printBoard();
            // 3. 도망자 잡기
            int curGet = 0; // 이번 턴에 잡은 도망자 수
            for(int i=0;i<3;++i){
                int nx = tx + tdx[td] * i;
                int ny = ty + tdy[td] * i;
                if(checkRange(nx, ny)){
                    if(tree[nx][ny] == 1){ // 나무가 있는 칸이라면
                        continue; // 이번칸은 보이지 않으므로 넘긴다.
                    }
                    curGet += checkThereisM(nx,ny);
                }
            }
            tScore += t * curGet;
            // printM();
            // printBoard();
            // if(t == 10) break;
        }
        System.out.println(tScore);
        

    }
    static void tMove(){
        tx = tx + tdx[td];
        ty = ty + tdy[td];
        tDis--;
        if(isThereTarget(1,1)){ // 1,1에 도착했다면
            td = 2; // 아래쪽으로 방향전환
            tStatus = true;// 돌아오는 방향으로 바꿔주기
            curDis--;
            tDis = curDis;
            tcnt = 0;
            return;
        }
        if(tStatus && isThereTarget(n, n)){ // 돌아오는 방향일때 n,n에 도착했다면
            tDis = curDis;
            td = 0; // 위쪽으로 방향전환
            return;
        }
        if(isThereTarget(n/2 + 1, n/2 + 1)){ // 다시 중앙에 도착했다면
            curDis = 1;
            tDis = curDis;
            td = 0;
            tStatus = false;
            tcnt = 0;
            return;

        }
        if(tDis == 0){ // 가야할 거리까지 다 도달 했다면
            tcnt += 1;
            if(!tStatus){ // 나가는 방향이면
                td = (td + 1) % 4;// 방향 전환    
                if(tcnt == 2){ 
                    curDis++; // 이동 거리증가
                    tcnt = 0;
                }
            }else{ // 돌아오는 방향이면
                td--;
                if(td < 0) td = 3; // 역방향 전환
                if(tcnt == 2){ 
                    curDis--; // 이동 거리감소
                    tcnt = 0;
                }
            }
            tDis = curDis;
        }
        //System.out.printf("%d,%d,%d,%d\n", td, curDis, tDis, tcnt);
    }
    static void printBoard(){
        for(int i=1;i<=n;++i){
            for(int j=1;j<=n;++j){
                if(isThereTarget(i, j)){
                    System.out.print(" 1");
                }else{
                    System.out.print(" 0");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    static void printM(){
        for(int i=1;i<=n;++i){
            for(int j=1;j<=n;++j){
                boolean flag = true;
                for(int k=0;k<m;++k){
                    if((i==mx[k]) && (j == my[k])){
                        System.out.printf("%d,", k+1 );
                        flag = false;
                    }
                }
                if(flag) System.out.print("0, ");
                else System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

/**
 * 
 * 
 * 
7 3 1 73
2 4 1
1 4 2
4 2 1
2 4

 */