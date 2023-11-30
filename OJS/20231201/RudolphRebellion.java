import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class RudolphRebellion {
    static int N,M,P,C,D,round = 1,maxDis;
    static boolean[] dead; // 탈락한 산타
    static int[][] myMap; // 산타맵
    static int[] faint;
    static int[] r,c; // 산타의 r,c 좌표들
    static int[] score; // 산타의 점수
    //방향 순서 -> 상,우,하, 좌
    static int[] dr = {-1,0,1,0}, dc = {0,1,0,-1};
    static int Rr,Rc,Rdir = 0; // 루돌프 좌표, 마지막으로 이동한 방향
    // 루돌프의 방향 순서  -> r좌표가 큰순 , c좌표가 큰순
    static int[] rdr = {1,1,1,0,0,-1,-1,-1}, rdc = {1,0,-1,1,-1,1,0,-1};
    public static void printScores(){
        System.out.print("점수 : ");
        for(int j=1;j<=P;++j){
            System.out.printf("%d ",score[j]);
        }
        System.out.println();
    }

    public static void printFaints(){
        System.out.print("기절 : ");
        for(int j=1;j<=P;++j){
            System.out.printf("%d ",faint[j]);
        }
        System.out.println();
    }
    public static void printMap(){
        int [][]newMap = new int[N+1][N+1];

        for(int i=1;i<=N;++i){
            for(int j=1;j<=N;++j){
                newMap[i][j] = myMap[i][j];
            }
        }
        newMap[Rr][Rc] = -1;
        for(int i=1;i<=N;++i){
            for(int j=1;j<=N;++j){
                System.out.printf("%d ",newMap[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public static int distance(int r1, int c1, int r2, int c2){
        return (r1 - r2) * (r1 - r2) + (c1 - c2) * (c1 - c2);
    }
    public static boolean validRange(int rr, int cc){
        return (1 <= rr)  && (rr <= N)  && (1<=cc) && (cc <= N);
    }
    public static boolean canMove(int j){
        return round > faint[j];
    }
    // 상호작용
    public static void chainCrash(int i, int cdr, int cdc ){ // 산타번호, 변화량
       //System.out.printf("%d : %d %d\n",i,cdr,cdc);
        Queue<Integer> job = new LinkedList<>();
        job.add(i);
        while(job.size()>0){
            int idx = job.poll();
            if(myMap[r[idx]][c[idx]] != 0){ // 충돌할 경우
                int nj = myMap[r[idx]][c[idx]];
                int nrj = r[nj] + cdr; 
                int ncj = c[nj] + cdc;
                if(validRange(nrj, ncj)) {
                    r[nj] = nrj;
                    c[nj] = ncj;
                    job.add(nj); // 범위 안에 들어가면 job에 add 
                }
                else dead[nj] = true; // 아니라면 산타 탈락
            }
            myMap[r[idx]][c[idx]] = idx; //map 업데이트
        }
    }

    // 루돌프가 충돌을 일으킴
    public static void rAttack(int j, int jrdr, int jrdc){
        score[j] += C; // 점수 얻기
        myMap[r[j]][c[j]] = 0; // 산타맵 업데이트
        // 산타 좌표 이동 (포물선)
        r[j] += jrdr*C;
        c[j] += jrdc*C;
        faint[j] = round + 1;
        if(validRange(r[j], c[j])) chainCrash(j, jrdr, jrdc); // 체스칸 안에 있다면
        else dead[j] = true; // 체스칸 밖으로 밀려 났다면 탈락
    }
    public static int[] makeJList(){
        int [] jList = new int[P+1];
        int pj = 0;
        for(int i=N;i>=1;--i){
            for(int j=N;j>=1;--j){
                if(myMap[i][j] != 0) jList[pj++] = myMap[i][j];
            }
        }
        return jList;
    }

    // 루돌프가 이동함 
    public static void rMove(){
        // 가장 가까운 산타 선택
        int nRr = Rr, nRc = Rc, minDis = maxDis, nRdir = Rdir, crashJ = -1;
        int [] jList = makeJList();

        //System.out.println();
        for(int j : jList){
            if(dead[j] || j == 0) continue; // 죽은 산타는 검사 X
            int curDis = distance(Rr, Rc, r[j], c[j]);
            if(minDis > curDis){
                minDis = curDis; 
                crashJ = j;
            }
        }
        // 가장 가까운 산타가 있으면 그 산타와 가장 가까워지는 방향으로 이동
        if(crashJ != -1){
            
            for(int i=0;i<8;++i){
                int cRr = Rr + rdr[i], cRc = Rc + rdc[i];
                if(!validRange(cRr, cRc)) continue; // 범위 밖으로 이동하면 X
                int curDis = distance(cRr, cRc, r[crashJ], c[crashJ]);
                if(minDis > curDis){
                    minDis = curDis; 
                    nRdir = i;
                    nRr = cRr;
                    nRc = cRc;
                }
            }
            //System.out.println();
            // 움직였다면
            if(minDis != maxDis){
                Rr = nRr;
                Rc = nRc;// 이동한 좌표 업데이트 하기
                Rdir = nRdir; // 이동한 방향 업데이트 하기
                if(minDis == 0){ // 충돌했다면
                    rAttack(crashJ, rdr[Rdir], rdc[Rdir]);
                }
            }
        }
    }
    // 산타가 움직인 후 루돌프와 충돌
    public static void pAttack(int j, int pjdr, int pjdc){ // 산타 좌표. 밀려나는 방향에 대한 r,c 변화량
        score[j] += D; // 점수 획득
        myMap[r[j]][c[j]] = 0; // 산타맵 업데이트
        // 산타 좌표 이동 (포물선)
        r[j] += pjdr*D;
        c[j] += pjdc*D;
        faint[j] = round + 1;
        if(validRange(r[j], c[j])) chainCrash(j, pjdr, pjdc); // 체스칸 안에 있다면
        else dead[j] = true; // 체스칸 밖으로 밀려 났다면 탈락
    }
    public static void pMove(){
        for(int j=1;j<=P;++j){
            if(!canMove(j) || dead[j]) continue; // 기절했거나 탈락한 산타는 움직이지 않음.
            
            int FDis = distance(Rr,Rc,r[j],c[j]),minDis = FDis, cr = r[j], cc = c[j], cdir = -1;
            for(int i=0;i<4;++i){
                int nr = r[j] + dr[i], nc = c[j] + dc[i];
                if(!validRange(nr, nc) || myMap[nr][nc] != 0) continue; // 범위 밖으로 이동 X, 산타가 있는 곳 으로 이동 X
                int curDis = distance(Rr, Rc, nr, nc);
                if(curDis < minDis){
                    minDis = curDis;
                    cr = nr;
                    cc = nc;
                    cdir = i; 
                }
            }
            // 움직였다면
            if(minDis != FDis){
                myMap[r[j]][c[j]] = 0;
                r[j] = cr;
                c[j] = cc; 
                myMap[r[j]][c[j]] = j;
                if(minDis == 0){ // 충돌했다면
                    int ndir = cdir;
                    if(cdir == 0) ndir = 2;
                    else if(cdir == 1) ndir = 3;
                    else if(cdir == 2) ndir = 0;
                    else if(cdir == 3) ndir = 1;
                    pAttack(j,dr[ndir],dc[ndir]);
                }
            }
            
        }
    }
    public static void addScore(){
        for(int j=1;j<=P;++j)
            if(!dead[j]) score[j] += 1;
    }
    public static boolean checkEnd(){
        for(int j=1;j<=P;++j)
            if(!dead[j]) return false;
        return true;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] NMPCD = br.readLine().split(" ");
        N = Integer.parseInt(NMPCD[0]);
        M = Integer.parseInt(NMPCD[1]);
        P = Integer.parseInt(NMPCD[2]);
        C = Integer.parseInt(NMPCD[3]);
        D = Integer.parseInt(NMPCD[4]);
        String[] RrRc = br.readLine().split(" ");
        Rr = Integer.parseInt(RrRc[0]);
        Rc = Integer.parseInt(RrRc[1]);
        // 산타 배열들 초기화
        dead = new boolean[P+1];
        r = new int[P+1];
        c = new int[P+1];
        score = new int[P+1];
        faint = new int[P+1];
        myMap = new int[N+1][N+1];
        maxDis = (N)*(N)*2 + 1;
        int pidx,sr,sc;
        for(int i=0;i<P;++i){
            String[] prc = br.readLine().split(" ");
            pidx = Integer.parseInt(prc[0]);
            sr = Integer.parseInt(prc[1]);
            sc = Integer.parseInt(prc[2]);
            myMap[sr][sc] = pidx;
            r[pidx] = sr;
            c[pidx] = sc;
        }
        //printMap();
        for(int i=1;i<=M;++i){
            //System.out.printf("라운드 : %d\n",i);
            rMove();
            //printMap();
            pMove();
            addScore();
            //printFaints();
            //printScores();
            //printMap();
            round++;
            if(checkEnd()) break;
        }
        for(int j=1;j<=P;++j){
            System.out.printf("%d ", score[j]);
        }
    }
}
