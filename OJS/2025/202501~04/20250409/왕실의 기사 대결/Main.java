import java.io.*;
import java.util.*;


public class Main {
    static int L,N,Q;
    static int[] r,c,h,w,k,dmg;
    static boolean[] status, visit;

    // 상하좌우
    static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
    static int[][] map, kMap, checkKMap;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        r = new int[N+1];
        c = new int[N+1];
        h = new int[N+1];
        w = new int[N+1];
        k = new int[N+1];
        dmg = new int[N+1];
        map = new int[L+1][L+1];
        for(int i=1;i<=L;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=L;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=1;i<=N;++i){
            st = new StringTokenizer(br.readLine());
            r[i] = Integer.parseInt(st.nextToken());
            c[i] = Integer.parseInt(st.nextToken());
            h[i] = Integer.parseInt(st.nextToken());
            w[i] = Integer.parseInt(st.nextToken());
            k[i] = Integer.parseInt(st.nextToken());
        }
        //System.out.println("시작 전");
        //printKMap();
        for(int q=1;q<=Q;++q){
            checkKMap = makeKMap();
            status = new boolean[N+1];
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            //System.out.printf("%d번째 턴 %d , %d => \n",q, id, d);
            if(k[id] - dmg[id] <= 0) continue;// 사라진 기사 제외
            if(kMoveCheck(id, d)){
                // 모든 이동이 가능할 경우
                kMove(d);
                status[id] = false; // 명령 받은 기사는 데미지 입지 않는다.
                checkTrap(); // 함정 확인하기
                //printKMap();
                //printDmg();
            }
        }
        int ans = 0;
        for(int i=1;i<=N;++i){
            if(k[i] - dmg[i] > 0 ) ans += dmg[i]; 
        }
        System.out.println(ans);
    }
    static void printDmg(){
        System.out.println("데미지");
        for(int i=1;i<=N;++i){
            System.out.printf("%d ", dmg[i]);
        }
        System.out.println();
    }
    static boolean kMoveCheck(int id, int d){
        if(status[id]) return true;// 이미 움직인 기사라면 skip;
        //System.out.printf("%d번 \n", id);
        if(k[id] - dmg[id] <= 0) return false;
        int sx = r[id];
        int sy = c[id];
        int ex = sx + h[id] - 1;
        int ey = sy + w[id] - 1;
        if(d == 0 || d == 2){
            int x = (d == 0) ? sx + dx[d] : ex + dx[d]; //검사할려는 부분 
            //System.out.printf("검사 범위 : (%d, %d ) ~ (%d, %d)\n", x,sy,x,ey);
            for(int y = sy; y<=ey;++y){
                if(!checkRange(x, y) || map[x][y] == 2) return false; // 이 부분이 벽에 부딪히면 명령 수행 불가능
                if(checkKMap[x][y] != 0){
                    int nId = checkKMap[x][y];
                    if(!kMoveCheck(nId, d)) return false; // 다음 기사가 벽에 박히면 수행 불가
                }
            }
        }else{
            int y = (d == 3) ? sy + dy[d] : ey + dy[d]; //검사할려는 부분 
            //System.out.printf("검사 범위 : (%d, %d ) ~ (%d, %d)\n", sx,y,ex,y);
            for(int x = sx; x<=ex;++x){
                if(!checkRange(x, y) || map[x][y] == 2) return false; // 이 부분이 벽에 부딪히면 명령 수행 불가능
                if(checkKMap[x][y] != 0){
                    int nId = checkKMap[x][y];
                    if(!kMoveCheck(nId, d)) return false; // 다음 기사가 벽에 박히면 수행 불가
                }
            }
        }
        //모든 이동이 끝나고 좌표 변경하기
        status[id] = true;
        //System.out.printf("%d번 종료\n",id);
        return true;
    }
    static boolean checkRange(int x,int y){
        return (x>=1) && (y>=1) && (x<=L) && (y<=L);
    }
    static void kMove(int d){
        for(int i=1;i<=N;++i){
            if(!status[i]) continue;// 움직이지 않았다면 가만히 둔다
            r[i] = r[i] + dx[d];
            c[i] = c[i] + dy[d];
        }
    }

    static void checkTrap(){
        for(int i=1;i<=N;++i){
            if(!status[i]) continue; // 밀리지 않은 기사 제외
            if(k[i] - dmg[i] <= 0) continue;
            int sx = r[i];
            int sy = c[i];
            int ex = sx + h[i];
            int ey = sy + w[i];
            for(int x=sx; x<ex; ++x){
                for(int y=sy; y<ey; ++y){
                    if(map[x][y] == 1) dmg[i]++; // 함정이 있다면 데미지 추가
                }
            }
        }
    }
    static int[][] makeKMap(){
        int[][] temp = new int[L+1][L+1];
        for(int i=1;i<=N;++i){
            if(k[i] - dmg[i] <= 0) continue;
            int sx = r[i];
            int sy = c[i];
            int ex = sx + h[i];
            int ey = sy + w[i];
            for(int x=sx; x<ex; ++x){
                for(int y=sy; y<ey; ++y){
                    temp[x][y] = i; 
                }
            }
            
        }
        return temp;
    }
    static void printKMap(){
        int[][] m = makeKMap();
        for(int i=1;i<=L;++i){
            for(int j=1;j<=L;++j){
                System.out.printf("%d ",m[i][j]);
            }
            System.out.println();
        }
    }
}
