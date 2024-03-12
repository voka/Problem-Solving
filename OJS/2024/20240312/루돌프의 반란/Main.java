import java.util.*;
import java.io.*;

class Triple implements Comparable<Triple>{
    int id;
    int r; 
    int c;
    int dis; 
    public Triple(int id, int r, int c, int dis){
        this.id = id;
        this.r = r;
        this.c = c;
        this.dis = dis;
    }
    @Override
    public int compareTo(Triple o){
        if(this.dis > o.dis) return 1;
        else if(this.dis < o.dis) return -1;
        if(this.r < o.r) return 1;
        else if(this.r > o.r ) return -1;
        if(this.c < o.c) return 1;
        else if(this.c > o.c )return -1;
        return 0;
    }

}
public class Main {
    static int N,M,P,C,D;
    static int rx,ry,rd, rsi;// 루돌프 위치, 루돌프가 이동한 방향, 루돌프가 돌진한 산타 번호
    static int[] sx,sy,sd;// 산타 위치, 산타 방향
    static int[] score;// 산타의 점수
    static boolean[] out;// 산타의 탈락 여부
    static int[] stun;// 산타의 기절
    static int alive;// 탈락하지 않은 산타 수
    static int[] dx = {-1,0,1,0,-1,1,1,-1}, dy = {0,1,0,-1,1,1,-1,-1};
    static PriorityQueue<Triple> pq = new PriorityQueue<>(); // 루돌프가 돌진할 산타 뽑을때 사용
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        alive = P;
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        sx = new int[P+1];
        sy = new int[P+1];
        sd = new int[P+1];
        stun = new int[P+1];
        score = new int[P+1];
        out = new boolean[P+1]; 
        st = new StringTokenizer(br.readLine());
        rx = Integer.parseInt(st.nextToken());
        ry = Integer.parseInt(st.nextToken());
        for(int i=1;i<=P;++i){
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            sx[id] = Integer.parseInt(st.nextToken());
            sy[id] = Integer.parseInt(st.nextToken());
        }
        for(int i=0;i<M;++i){
            solve(i);
            if(alive == 0) break;
        }
        for(int i=1;i<=P;++i){
            sb.append(score[i]).append(" ");
        }
        System.out.println(sb.toString());
    }
    static int cal(int x1, int y1, int x2, int y2){
        return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
    }
    static void solve(int k){
        //printMap();
        //System.out.println("MOVE R");
        moveR(k);
        //printMap();
        //System.out.println("MOVE S");
        moveS(k);
        //printMap();
        addPoint();
        //System.out.printf("생존 -> %d\n",alive);
        //printScore();
    }
    static boolean checkRange(int x, int y){
        return (x>=1) && (y>=1) && (x<=N) && (y<=N);
    }
    static void moveR(int t){
        pq.clear();
        for(int i=1;i<=P;++i){
            if(out[i]) continue;
            pq.add(new Triple(i,sx[i],sy[i],cal(rx,ry,sx[i],sy[i])));
        }
        Triple s = pq.poll(); // 돌진할 산타
        
        //System.out.printf("돌진할 산타 : %d\n",s.id);
        int minDis = Integer.MAX_VALUE;
        int nextRx= -1, nextRy = -1;
        int dir = -1;
        for(int i=0;i<8;++i){
            // 가장 가까워 지는 방향으로 돌진
            int nx = rx + dx[i];
            int ny = ry + dy[i];
            if(!checkRange(nx,ny)) continue; 
            int cur = cal(nx,ny,s.r, s.c);
            if(minDis > cur){
                dir = i;
                minDis = cur;
                nextRx = nx;
                nextRy = ny;
            }
        }
        // 루돌프 좌표 및 움직인 방향 업데이트
        rsi = s.id;
        rx = nextRx;
        ry = nextRy;
        rd = dir;
        if(minDis == 0){ // 충돌했다면 
            //산타 기절시키기
            stun[s.id] = t+2; // 어짜피 산타의 움직임도 루돌프와 최대한 가까워지는 것이기 때문에 안움직여도 됨.
            score[s.id] += C;
            //산타 밀어내기
            int nsx = sx[rsi] + dx[rd] * C;
            int nsy = sy[rsi] + dy[rd] * C;
            // 범위 밖으로 나갔다면
            if(!checkRange(nsx,nsy)) {
                out[rsi] = true;
                alive--;
            }else{
                // 범위 안에 있고, 다른 산타와 충돌했다면
                int nid = findS(rsi,nsx,nsy);
                sx[rsi] = nsx; 
                sy[rsi] = nsy;
                if(nid != -1) chain(rd, nid);
            }
        }

    }
    static boolean isThereS(int id, int x, int y){

        for(int i=1;i<=P;++i){
            if(id == i || out[i]) continue;
            if(isSame(sx[i],sy[i],x,y)) return true;
        }
        return false;
    }
    static int findS(int id, int x, int y){

        for(int i=1;i<=P;++i){
            if(id == i || out[i]) continue;
            if(isSame(sx[i],sy[i],x,y)) return i;
        }
        return -1;
    }
    static void moveS(int t){
        for(int i=1;i<=P;++i){
            if(stun[i] > t) continue;// 기절한 상태
            if(out[i]) continue;// 탈락한 상태
            int curDis = cal(rx,ry,sx[i],sy[i]);// 산타 - 루돌프의 이동전 거리
            int minDis = curDis;
            int mx=sx[i];
            int my=sy[i];
            int md=-1;
            for(int d=0;d<4;++d){
                int nx = sx[i] + dx[d];
                int ny = sy[i] + dy[d];
                if(!checkRange(nx,ny)) continue;
                if(isThereS(i,nx,ny)) continue; // 다른 산타가 가려는 칸에 있는지 확인
                int nextDis = cal(rx,ry,nx,ny);

                if(nextDis < curDis){ // 거리가 좁혀질 경우에만 움직인다.   
                    if(minDis > nextDis) {
                        minDis = nextDis;
                        mx = nx;
                        my = ny;
                        md = d;
                    }
                }
            }
            //System.out.printf("id : %d, (%d, %d) -> (%d, %d), dir : %d, curDis : %d, nextDis : %d\n",i,  sx[i]-1,sy[i]-1,mx-1,my-1,md,curDis,minDis);
            if(md != -1){ // 가까워 질 수 있는 방법이 있는 경우 그 방향으로 1칸 전진
                sx[i] = mx;
                sy[i] = my;
                sd[i] = md;
            }
            if(minDis == 0){ // 충돌한 경우
                stun[i] = t+2;// 기절
                score[i] += D;// 점수 증가
                int nd = (sd[i]%2==0) ? 2 - sd[i] : 4 - sd[i];// 산타가 전진한 반대방향 
                int nsx = sx[i] + dx[nd]*D;
                int nsy = sy[i] + dy[nd]*D;
                if(!checkRange(nsx,nsy)){
                    out[i] = true;
                    alive--;
                }else{
                    // 범위 안에 있고, 다른 산타와 충돌했다면
                    int nid = findS(i,nsx,nsy);
                    sx[i] = nsx; 
                    sy[i] = nsy;
                    if(nid != -1) chain(nd, nid);
                }
            } 
        }
    }
    static boolean isSame(int x1, int y1, int x2, int y2){
        return (x1==x2) && (y1==y2);
    }
    
    static void chain(int d, int id){
        int nsx = sx[id] + dx[d];
        int nsy = sy[id] + dy[d];
        if(!checkRange(nsx,nsy)){
            out[id] = true;
            alive--;
            return;
        }
        int nid = findS(id, nsx,nsy);
        sx[id] = nsx;
        sy[id] = nsy;
        if(nid!=-1) chain(d,nid);
    }


    static void addPoint(){
        for(int i=1;i<=P;++i){
            if(!out[i]) score[i]++;
        }
    }
    static void printMap(){
        System.out.printf("point : (%d, %d) , d : %d\n",rx-1, ry-1, rd);
        int[][] temp = new int[N][N];
        for(int i=1;i<=P;++i)   {
            if(!out[i]){ // 탈락되지 않은 산타들만 출력
                temp[sx[i]-1][sy[i]-1] = i;
            }
        }
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                System.out.printf("%d ",temp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void printScore(){
        for(int i=1;i<=P;++i){
            System.out.printf("%d ", score[i]);
        }
        System.out.println();
    }
}
