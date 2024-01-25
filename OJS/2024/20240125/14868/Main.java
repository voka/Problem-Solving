import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
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
    static int N, K;
    //촤초 문명 지역 중 결합되지 않은 문명만 남겨놓을 Set
    static Set<Integer> keySet = new HashSet<>();
    static boolean[][] visited;
    static int[] parents, dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static Queue<Pair> checkPoint = new LinkedList<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        visited = new boolean[N+1][N+1];
        parents = new int[N * N + 1];
        for(int i=0;i<K;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            b = N+1 - b;
            keySet.add(changePoint(b, a));
            checkPoint.add(new Pair(b, a));
            visited[b][a] = true;
        }
        for(int i=0;i<N * N + 1 ;++i){
            parents[i] = i;
        }
        int day = 0;
        firstDay();
        //printVisited();
        while(!checkPoint.isEmpty()){
            //System.out.printf("day =============>%d \n",day);
            //printP();
            //printVisited(); 
            //System.out.println(keySet);
            // 문명의 개수가 1개가 된다면 나머지 문명들이 모두 통합된것
            if(keySet.size() == 1) break; 
            bfs();
            //System.out.println(day);
            //System.out.println(count);
            day++;
        }
        System.out.println(day);
    }
    static int changePoint(int x, int y){
        return (x-1) * N + y;
    }
    static int findP(int x){
        if(parents[x] != x){
            parents[x] = findP(parents[x]);
        }
        return parents[x];
    }

    static void unionP(int a, int b){
        a = findP(a);
        b = findP(b);
        // 서로 다른 최초 문명이 결합하는 경우 
        // 두 문명 숫자 중 작은 것을 남기고 큰 것을 지우고 작은 것과 결합시킨다.
        if(keySet.contains(a) && keySet.contains(b)){
            if(a > b){ 
                parents[a] = b;
                keySet.remove(a);
            }else{
                parents[b] = a;
                keySet.remove(b);
            }
            // 하나의 좌표는 미개지역이고 다른 하나는 문명지역이라면 
        }else if(keySet.contains(a)){ 
            parents[b] = a; // 미개지역을 문명지역에 결합시킨다.
        }else if(keySet.contains(b)){
            parents[a] = b;
        }
    }
    // 배열의 좌표 범위를 검사하는 함수
    static boolean checkRange(int x, int y){
        return (x>=1) && (y>=1) && (x<=N) && (y<=N);
    }
    static void firstDay(){ // 가장 첫 날에 인접한 문명은 결합시킨다.
        int end = checkPoint.size();
        for(int s=0; s< end;++s){
            Pair cur = checkPoint.poll();
            int c = changePoint(cur.x,cur.y);
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx,ny)){
                    if(visited[nx][ny]){ //문명 지역 끼리 만났다면
                        int next = changePoint(nx, ny);
                        if(findP(c) != findP(next)){ // 서로 다른 문명인 경우에만 결합시킨다.
                            unionP(c,next);
                        }
                    }
                }
            }
            checkPoint.add(cur);
        }
    }
     //checkPoint에 들어있는 문명 지역에서 주변으로 문명을 전파한다.
    static void bfs(){
        int end = checkPoint.size();
        for(int s=0; s< end;++s){
            Pair cur = checkPoint.poll();
            int c = changePoint(cur.x,cur.y);
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx,ny)){ // 범위를 확인하고
                    int next = changePoint(nx, ny);
                    // (문명 지역 vs 문명 지역) or (문명 지역 vs 미개 지역) 인 경우 모두
                    // 문명을 결합, 전파해야 하므로 visited 조건이 따로 필요하지 않다.
                    if(findP(c) != findP(next)){
                        unionP(c,next);
                        check(nx, ny);
                    } // 미개지역 vs 문명 지역
                    if(!visited[nx][ny]){
                        checkPoint.add(new Pair(nx, ny));
                        visited[nx][ny] = true;
                    }
                }
            }
        }
    }// 인접한 문명이 있는지 확인하는 함수이다. 
    static void check(int x, int y){ 
        int c = changePoint(x,y);
        for(int i=0;i<4;++i){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(checkRange(nx,ny)){// 범위를 확인하고
                int next = changePoint(nx, ny);
                int cP = findP(c);
                int nP = findP(next);
                if(cP != nP && keySet.contains(cP) && keySet.contains(nP)){ 
                    // 서로 다른 최조 문명 지역인 칸끼리 인접해 있을 경우에만 하나의 문명으로 결합한다. 
                    unionP(c,next);
                }
            }
        }
    }
    static void printVisited(){
        System.out.println();
        for(int i=1;i<=N;++i){
            for(int j=1;j<=N;++j){
                System.out.printf("%b ",visited[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void printP(){
        System.out.println();
        for(int i=1;i<=N;++i){
            for(int j=1;j<=N;++j){
                System.out.printf("%d ",parents[(i-1)*N + j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}