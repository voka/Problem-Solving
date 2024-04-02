import java.io.*;
import java.util.*;

class Tower implements Comparable<Tower>{
    int x;
    int y;
    int attackRound;
    int attack;
    public Tower(int x, int y, int attack, int aR){
        this.x = x; 
        this.y = y;
        this.attack = attack;
        this.attackRound = aR;
    }
    @Override
    public int compareTo(Tower o){
        if(this.attack>o.attack) return 1; 
        else if(this.attack < o.attack) return -1;
        if(this.attackRound < o.attackRound) return 1;
        else if(this.attackRound > o.attackRound) return -1;
        if(this.x + this.y < o.x + o.y) return 1;
        else if(this.x + this.y > o.x + o.y) return -1;
        if(this.y < o.y) return 1;
        else if(this.y > o.y) return -1;
        return 0;
    }

}

class PairTower{
    Tower attack;
    Tower defense;
    public PairTower(Tower a, Tower d){
        this.attack = a;
        this.defense = d;
    }
}
class Point{
    int x;
    int y;
    public Point(int x, int y){
        this.x = x; 
        this.y = y;
    }
}
public class Main {
    static int N,M,K,alive;
    static int[][] attackStatus;
    static int[][] map, backX, backY;
    static boolean[][] visited;
    static int[] dx = {0,1,0,-1}, dy = {1,0,-1,0}; //우, 하, 좌, 상 순
    static int[] ddx = {-1,-1,-1,0,1,1,1,0}, ddy = {-1,0,1,1,1,0,-1,-1};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        attackStatus = new int[N][M];
        map = new int[N][M];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j]!=0) alive++;
            }
        }
        for(int r=1;r<=K;++r){
            visited = new boolean[N][M];
            backX = new int[N][M];
            backY = new int[N][M];
            PairTower cur = pickAttacker(r);
            if(alive < 2) break;
            //printArr();
            attack(cur);
            //printArr();
            ready();
            //printArr();
        }
        System.out.println(powerfulTower());
    }
    static int powerfulTower(){
        int re = Integer.MIN_VALUE;
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                re = Math.max(re, map[i][j]);
            }
        }
        return re;
    }
    static PairTower pickAttacker(int r){
        List<Tower> my = new ArrayList<>();
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(map[i][j] == 0) continue;
                my.add(new Tower(i,j,map[i][j],attackStatus[i][j]));
            }
        }
        alive = my.size();
        if(alive < 2) return null;
        Collections.sort(my);
        Tower a = my.get(0); // 가장 약한 포탑
        a.attack += (N+M);
        map[a.x][a.y] += (N+M);
        attackStatus[a.x][a.y] = r;
        Tower d = my.get(my.size()-1); // 가장 강한 포탑
        //System.out.printf("%d, %d -> %d, %d\n", a.x, a.y, d.x, d.y);
        return new PairTower(a,d);
    }
    static boolean getDP(Point s, Point e){
        boolean[][] check = new boolean[N][M];
        Queue<Point> que = new ArrayDeque<>();
        check[s.x][s.y] = true;
        que.add(s);
        while(!que.isEmpty()){
            Point cur = que.poll();
            if(cur.x == e.x && cur.y == e.y) return true;
            for(int i=0;i<4;++i){
                int nx = (cur.x + dx[i] + N) % N;
                int ny = (cur.y + dy[i] + M) % M;
                //System.out.printf("%d,%d : %d -> %b,%b\n",nx,ny,map[nx][ny],map[nx][ny] != 0 , !check[nx][ny]);
                if(map[nx][ny] != 0 && !check[nx][ny]){
                    //System.out.printf("%d,%d\n",nx,ny);
                    check[nx][ny] = true;
                    backX[nx][ny] = cur.x;
                    backY[nx][ny] = cur.y;
                    que.add(new Point(nx,ny));
                }
            }
        }
        return false;
    }
    static void attack(PairTower pt){
        Tower a = pt.attack;
        Tower d = pt.defense;
        Point s = new Point(a.x,a.y);
        Point e = new Point(d.x,d.y);
        visited[s.x][s.y] = true;
        visited[e.x][e.y] = true;
        boolean re = getDP(s,e);
        map[e.x][e.y] = Math.max(map[e.x][e.y] - a.attack,0);
        if(re){ //레이저 공격
            //System.out.println("레이저");
            int sx = backX[e.x][e.y];
            int sy = backY[e.x][e.y];
            while(!(sx == s.x && sy == s.y)){
                visited[sx][sy] = true;
                map[sx][sy] = Math.max(map[sx][sy] - (a.attack/2),0);
                int nx = backX[sx][sy];
                int ny = backY[sx][sy];
                sx = nx;
                sy = ny;
            }
        }
        else{// 포탄 공격
            //System.out.println("포탄");
            for(int i=0;i<8;++i){
                int nx = (e.x + ddx[i] + N) % N;
                int ny = (e.y + ddy[i] + M) % M;
                if(!((nx == s.x) && (ny == s.y)) && map[nx][ny] != 0){
                    visited[nx][ny] = true;
                    map[nx][ny] = Math.max(map[nx][ny] - (a.attack/2),0);
                }
            }
        }
        
    }
    static void ready(){
        int al = 0;
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(map[i][j] != 0){
                    if(!visited[i][j]) map[i][j]++;
                }
            }
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<N) && (y<M);
    }
    static void printArr(){
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
    }
}