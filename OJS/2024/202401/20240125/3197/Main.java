import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair{
    int x;
    int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int R, C, L1 = -1, L2 = -1;
    static int[] parent, dx = {1,0,-1,0}, dy = {0,1,0,-1} ;
    static int[][] map;
    static boolean[][] visited;
    static Queue<Pair> nextMeltingPair = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        char L = 'L',X = 'X',dot = '.'; 
        parent = new int[ R * C + 1];
        map = new int[R][C];
        visited = new boolean[R][C];
        for(int i=0;i<R;++i){
            String temp = br.readLine();
            for(int j=0;j<C;++j){
                char cur = temp.charAt(j);
                if(cur == X){ // 얼음
                    map[i][j] = -1;
                }else if(cur == dot){ // 물
                    map[i][j] = 0;
                }else if(cur == L){ // 백조 위치
                    map[i][j] = 0;
                    if(L1 == -1) {
                        L1 = i * C + j;  // 좌표 변환
                    }
                    else {
                        L2 = i * C + j;
                    }
                }
                parent[i * C + j] = i * C + j;
            }
        }

        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                if(!visited[i][j] && map[i][j] == 0){
                    findNextMeltingPoint(new Pair(i, j));
                }
            }
        }
        int day = 0;
        while(!nextMeltingPair.isEmpty()){
            // System.out.printf("DAY ===============> %d\n",day);
            // printP();
            // printMap();
            if(findP(L1) == findP(L2)){
                break;
            }
            meltingPoint();
            day++;
        }
        //printP();
        System.out.println(day);
    }

    static void findNextMeltingPoint(Pair p){
        Queue<Pair> my = new LinkedList<>();
        my.add(p);
        visited[p.x][p.y] = true;
        while(!my.isEmpty()){
            Pair cur = my.poll();
            int c = C * cur.x + cur.y;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny)  && !visited[nx][ny]){
                    if(map[nx][ny] == -1){ // 얼음이라면 녹는점에 포함 시키기
                        nextMeltingPair.add(new Pair(nx, ny));
                    }
                    if(map[nx][ny] == 0){// 물이라면 탐색 계속  // 물인 곳은 다시 방문하지 않아도 된다.
                        my.add(new Pair(nx, ny));
                        // 집합 합쳐주기
                        int next = nx * C + ny;
                        if(findP(c) != findP(next)) unionP(c, next);
                        visited[nx][ny] = true;
                    }
                }
            }
        }
    }
    static void meltingPoint(){
        int end = nextMeltingPair.size();
        for(int s=0;s<end;++s){
            Pair cur = nextMeltingPair.poll();
            map[cur.x][cur.y] = 0;
            int c = cur.x * C + cur.y;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny)){
                    if(map[nx][ny] == 0){ // 물인 곳은 다시 방문하지 않아도 된다.
                        // 집합 합쳐주기
                        int next = nx * C + ny;
                        if(findP(c) != findP(next)) unionP(c, next);
                    }
                    if(!visited[nx][ny] && map[nx][ny] == -1){ // 얼움이라면 다음 녹는점에 포함
                        visited[nx][ny] = true;
                        nextMeltingPair.add(new Pair(nx, ny));
                    }
                }
            }
        }
    }
    static void printP(){
        System.out.println("Parent ==> ");
        for(int i=0;i < R * C ; ++i){
            System.out.printf("%d ",parent[i]);
        }
        System.out.println();
    }
    static void printMap(){
        System.out.println();
        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static int findP(int x){
        if(parent[x] != x){
            parent[x] = findP(parent[x]);
        }return parent[x];
    }
    static void unionP(int a, int b){
        a = findP(a);
        b = findP(b);
        if( a < b){
            parent[b] = a;
        }else{
            parent[a] = b;
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<R) && (y<C);
    }
   
}
