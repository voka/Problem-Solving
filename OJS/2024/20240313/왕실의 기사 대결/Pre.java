//https://www.codetree.ai/training-field/frequent-problems/problems/royal-knight-duel/description?page=1&pageSize=20

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/**
 * 체스판 (빈칸, 함정, 벽)
 * (r,c) -> (y,x)
 * 기사는 각각 초기 체력 k 를 가지고 있다.
 * 기사는 방패를 들고 있으며 r,c를 좌측 상단으로 w,h 크기의 직사각형 형태를 띄고 있다. 
 * 명령으로 인한 움직임 
 * - 이동하려는 곳에 다른 기사가 있다면 그 기사도 밀려나게 된다. (연쇄)
 * - 하지만 마지막 기사가 이동하려는 방향의 끝에 벽에 있다면 모든 기사는 이동하지 않는다.
 * - 체스판 밖도 벽으로 간주한다.
 * 대결 
 * - 기사에 의해 밀려나게 되면 밀려나게 된 곳(이동한 곳) 기준으로 w,h 직사각형 크기 안에 있는 함정 수 만큼 피해를 받는다.
 * - 피해를 받은 만큼 체력이 감소
 * - 체력이 <= 0 이 되면 체스판에서 사라짐
 * - 밀쳐진 위치에 함정이 없으면 그 기사는 피해를 입지 않음
 * 
 * 각 기사들은 기준좌표 (r,c)를 가짐.
 * 기준좌표에서 오른쪽으로 c 만큼, 아래쪽으로 r 만큼 이 한 기사가 차지하는 영역임
 * 밀려나는 방향의 맨 끝 쪽 부분만 검사해도 됨. 
 * (ex) 오른쪽으로 밀려나는 경우에는 (y: r ~ r+h 만큼 x: c + w + 1에에 벽 or 기사가 있는지 확인))
 * 한 명령에서 모든 기사가 벽에 부딪히지 않고 도달할 수 있다면 명령 수행
 * (이때! 밖으로 나가는 기사가 있는 경우는 괜찮음)
 * 
 * 작성할 함수
 * 1. 밀려난 기사의 범위에 폭탄 개수를 세는 함수
 * 2. 방향의 끝을 검사하는 함수
 * 3. 
 */

class Knight{
    int k; // 생명력
    int idx; // 기사 번호
    int x;
    int y;
    int w;
    int h;
    int damage;
    boolean alive = true; // 체스판에 남아 있고, 체력이 0 보다 큰지
    public Knight(int idx, int x, int y, int w,int h, int k,int damage){
        this.idx = idx;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.k = k; 
        this.damage = damage;
    }
    public String toString(){
        return String.format("idx : %d, hp : %d, x : %d, y : %d, w : %d, h : %d, damage : %d", idx+1,k,x,y,w,h,damage);
    }
}
class MovedKnight{
    int idx;// 이동하는 기사 번호
    int nx;// 움직인 x 좌표
    int ny;// 움직인 y 좌표
    boolean canNot; // 움직일 수 없는지
    public MovedKnight(int idx, int nx, int ny, boolean canNot){
        this.idx = idx;
        this.nx = nx;
        this.ny = ny; 
        this.canNot = canNot;
    }
}
public class Pre {
    static int L,N,Q,answer; // L : 격자크기, N : 기사 수, Q : 명령 수 , answer : 정답
    static List<Knight> knights = new ArrayList<>(); // 기사 정보를 담을 배열
    static int[][] map; // 벽 위치 정보를 담을 2차원 배열
    static boolean[] deadNights;
    //위쪽, 오른쪽, 아래쪽, 왼쪽
    static int[] dx = {0,1,0,-1};
    static int[] dy = {-1,0,1,0};

    public static boolean canMoveAll(List<MovedKnight> movedKnights){
        for(int i=movedKnights.size()-1;i >= 0;--i){
            if(movedKnights.get(i).canNot){
                return false; 
            }
        }

        return true;
    }
    public static boolean validRange(int x){
        return (0<=x) && (x<L);
    }
    public static boolean canNotMove(int sx,int sy, int ex,int ey){
        if(validRange(sx) && validRange(sy) && validRange(ex) && validRange(ey)){
            for(int i=sy;i<=ey;++i){
                for(int j=sx;j<=ex;++j){
                    if(map[i][j] == 2){
                        return true;
                    }
                }
            }
            return false;
        }// 체스판 밖으로 나가진다면
        return true;
    }

    public static int countTrap(int y, int x, int w, int h){
        int cnt = 0;
        for(int i=y;i<=y+h;++i){
            for(int j=x;j<=x+w;++j){
                if(map[i][j] == 1){
                    cnt++;
                } 
            }
        }

        return cnt;
    }
    public static void printKnight(int i){
        System.out.println(knights.get(i).toString());
    }
    public static void printAllKnightToMap(){
        int[][] temp = new int[L][L];
        for(Knight knight : knights){
            if(deadNights[knight.idx]){
                System.out.println("Dead: "+ (knight.idx+1));
                continue;
            }
            System.out.printf("idx: %d, W x H : %d\n", (knight.idx+1),(knight.w+1) * (knight.h+1));
            int sx,sy,ex,ey; 
            sx = knight.x;
            sy = knight.y;
            ex = knight.x + knight.w;
            ey = knight.y + knight.h;
            for(int i=sy;i<=ey;++i){
                for(int j=sx;j<=ex;++j){
                    temp[i][j] = knight.idx+1;
                }
            }

        }
        for(int i=0;i<L;++i){
            for(int j=0;j<L;++j){
                System.out.printf("%d ",temp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void move(int idx, int d){
        boolean[] isMoved = new boolean[N];
        List<MovedKnight> movedKnights = new ArrayList<>(); // 움직임 정보를 담을 배열
        Queue<Integer> checkList = new LinkedList<>(); // 연쇄 작용을 위해 확인해야 하는 기사 Queue
        checkList.add(idx);
        while(checkList.size() > 0){
            int curInfo = checkList.poll();
            //printKnight(curInfo);
            isMoved[curInfo] = true;
            Knight curKnight = knights.get(curInfo);
            // 탐색 범위
            int sx = 0,sy = 0,ex = 0,ey = 0;
            switch (d) {
                case 0: // 위쪽 탐색  -> 현재 기사의 y 좌표 - 1 (검사 시작 y 좌표 == 검사 종료 y 좌표)
                    sx = curKnight.x;
                    ex = curKnight.x + curKnight.w;
                    sy = curKnight.y + dy[d];
                    ey = sy;
                    break;
                case 1: // 오른쪽 탐색 -> 현재 기사의 x 좌표 + 1 + w(검사 시작 x 좌표 == 검사 종료 x 좌표)
                    sx = curKnight.x + curKnight.w + dx[d];
                    ex = sx;
                    sy = curKnight.y;
                    ey = curKnight.y + curKnight.h;
                    break;
                case 2: // 아래쪽 탐색 -> 현재 기사의 y 좌표 + 1 + h (검사 시작 y 좌표 == 검사 종료 y 좌표)
                    sx = curKnight.x;
                    ex = curKnight.x + curKnight.w;
                    sy = curKnight.y + curKnight.h + dy[d];
                    ey = sy;
                    break;
                case 3: // 왼쪽 탐색 -> 현재 기사의 x 좌표 - 1 (검사 시작 x 좌표 == 검사 종료 x 좌표)
                    sx = curKnight.x + dx[d];
                    ex = sx;
                    sy = curKnight.y;
                    ey = curKnight.y + curKnight.h;
                    break;
                default:
                    break;
            }
            //System.out.println(sx + ", " + sy + ", "+ ex + ", "+ ey + ", ");
            if(canNotMove(sx, sy, ex, ey)){  // 현재 이동한 부분이 벽에 닿아 움직일 수 없다면 
                movedKnights.add(new MovedKnight(curInfo, curKnight.x + dx[d], curKnight.y + dy[d], true));
                //System.out.println("Can Not Move!!!  ==>  " + (curInfo + 1));
                break;
            }
            for(int i=0;i<N;++i){
                // 움직였던 기사 or 죽은 기사는 검사할 필요 X
                if(isMoved[i] || deadNights[i]){
                    continue;
                }
                Knight test = knights.get(i);
                switch (d) {
                    case 0:
                    // 위쪽인 경우 -> sx <= test.x <= ex || sx <= test.x + test.w <= ex || test.x <= sx <= test.x + test.w || test.x <= sx <= test.x + test.w and y + h == sy; 검사하는 기사의 아래쪽 부분만 검사
                        if((((sx <= test.x) && (test.x<= ex)) || ((sx <= test.x + test.w) && (test.x + test.w <= ex)) || ((test.x <= sx) && ( sx <= test.x + test.w )) || ((test.x <= ex) && ( ex <= test.x + test.w ))) && (test.y + test.h == sy)){
                            checkList.add(i);
                        }
                        break;
                    case 1: 
                    // 오른쪽인 경우 -> sy <= test.y <= ey or sy <= test.y <= ey || test.y <= sy <= test.y + test.h ||  test.y <= ey <= test.y + test.h and x == sx; 검사하는 기사의 왼쪽만 검사
                        if((((sy <= test.y) && ( test.y <= ey))||((sy <= test.y+test.h) && ( test.y + test.h <= ey)) || ((test.y <= sy) && (sy <= test.y + test.h)) || ((test.y <= ey) && (ey <= test.y + test.h))) && (test.x == sx)){
                            checkList.add(i);
                        }
                        break;
                    case 2: 
                    // 아래쪽인 경우 -> sx <= test.x <= ex || sx <= test.x + test.w <= ex || test.x <= sx <= test.x + test.w || test.x <= sx <= test.x + test.w  and y == ey; 검사하는 기사의 위쪽만 검사
                        if((((sx <= test.x) && (test.x<= ex)) || ((sx <= test.x + test.w) && (test.x + test.w <= ex)) || ((test.x <= sx) && ( sx <= test.x + test.w )) || ((test.x <= ex) && ( ex <= test.x + test.w ))) && (test.y == ey)){
                            checkList.add(i);
                        }
                        break;
                    case 3: 
                    // 왼쪽 경우 ->sy <= test.y <= ey or sy <= test.y <= ey || test.y <= sy <= test.y + test.h ||  test.y <= ey <= test.y + test.h and x + w == sx; 검사하는 기사의 오른쪽만 검사
                        if((((sy <= test.y) && ( test.y <= ey))||((sy <= test.y+test.h) && ( test.y + test.h <= ey)) || ((test.y <= sy) && (sy <= test.y + test.h)) || ((test.y <= ey) && (ey <= test.y + test.h))) && (test.x + test.w == sx)){
                            checkList.add(i);
                        }
                        break;
                    default:
                        break;
                }
            }
            movedKnights.add(new MovedKnight(curInfo, curKnight.x + dx[d], curKnight.y + dy[d], false));
        }
        // List에 들어간 모든 기사가 움직일 수 있는 경우
        if(canMoveAll(movedKnights)){
            //System.out.println("can move");
            for(MovedKnight movedKnight : movedKnights){
                Knight cur = knights.get(movedKnight.idx);
                //System.out.printf("%d, ",cur.idx + 1);
                int damage = cur.damage;
                if(idx != cur.idx){
                    int curDamage = countTrap(movedKnight.ny, movedKnight.nx, cur.w, cur.h);
                    damage += curDamage;
                    //System.out.print("현재: " + curDamage +  ", 누적 : " + damage);
                    if(cur.k - damage <= 0) deadNights[cur.idx] = true;
                }
                knights.set(cur.idx, new Knight(cur.idx, movedKnight.nx, movedKnight.ny, cur.w, cur.h, cur.k, damage));
                //System.out.println();
                //printKnight(cur.idx);
            }
        }
        //printAllKnightToMap();
        
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] LNQ = br.readLine().split(" ");
        answer = 0;
        L = Integer.parseInt(LNQ[0]);
        N = Integer.parseInt(LNQ[1]);
        Q = Integer.parseInt(LNQ[2]);
        map = new int[L][L];
        deadNights = new boolean[N];
        for(int i=0;i<L;++i){
            String[] temp = br.readLine().split(" ");
            for(int j=0;j<L;++j){
                map[i][j] = Integer.parseInt(temp[j]);
            }
        }
        for(int i=0;i<N;++i){
            String[] KnightInfo = br.readLine().split(" ");
            int r,c,h,w,k;
            r = Integer.parseInt(KnightInfo[0]);
            c = Integer.parseInt(KnightInfo[1]);
            h = Integer.parseInt(KnightInfo[2]);
            w = Integer.parseInt(KnightInfo[3]);
            k = Integer.parseInt(KnightInfo[4]);
            knights.add(new Knight(i,c-1,r-1,w-1,h-1,k,0));
        }
        for(int i = 0; i<Q;++i){
            
            String[] command = br.readLine().split(" ");
            int idx,d;
            idx = Integer.parseInt(command[0]);
            d = Integer.parseInt(command[1]);
            //System.out.println((i+1) + ": " + (idx) + "번째 기사, d: " + d);
            if(deadNights[idx-1]) continue; // 죽은 기사는 명령을 수행하지 않는다.
            move(idx-1,d);
            //printKnight();
        }
        for(int i=0;i<N;++i){
            if(deadNights[i]){
                continue;
            }
            //죽지 않은 기사들에 대해서
            answer += knights.get(i).damage;
        }
        //printAllKnightToMap();
        System.out.println(answer);
    }
    
}