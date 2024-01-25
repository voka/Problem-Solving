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
            keySet.add((b-1) * N + a);
            //System.out.println(keySet);
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
            if(keySet.size() == 1) break; // 문명의 개수가 1개가 된다면 나머지 문명들이 모두 통합된것
            bfs();
            //System.out.println(day);
            //System.out.println(count);
            day++;
        }
        System.out.println(day);
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

    static int findP(int x){
        if(parents[x] != x){
            parents[x] = findP(parents[x]);
        }
        return parents[x];
    }
    static void unionP(int a, int b){
        a = findP(a);
        b = findP(b);
        //System.out.printf("%d, %d\n", a, b);
        if(keySet.contains(a) && keySet.contains(b)){
            if(a > b){
                parents[a] = b;
                keySet.remove(a);
            }else{
                parents[b] = a;
                keySet.remove(b);
            }
        }else if(keySet.contains(a)){
            parents[b] = a;
        }else if(keySet.contains(b)){
            parents[a] = b;
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=1) && (y>=1) && (x<=N) && (y<=N);
    }
    static void firstDay(){
        int end = checkPoint.size();
        for(int s=0; s< end;++s){
            Pair cur = checkPoint.poll();
            int c = (cur.x-1) * N + cur.y;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx,ny)){
                    if(visited[nx][ny]){ // 문명인곳을 갔을 경우
                        int next = (nx-1) * N + ny;
                        //System.out.printf("%d, %d\n", c, next);
                        if(findP(c) != findP(next)){ // 서로 다른 문명인 경우
                            unionP(c,next);
                        }
                    }
                }
            }
            checkPoint.add(cur);
        }
    }
    static void bfs(){
        int end = checkPoint.size();
        for(int s=0; s< end;++s){
            Pair cur = checkPoint.poll();
            int c = (cur.x-1) * N + cur.y;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx,ny)){
                    int next = (nx-1) * N + ny; 
                    if(findP(c) != findP(next)){ // 서로 다른 문명인 경우
                        unionP(c,next);
                        check(nx, ny);
                    }
                    if(!visited[nx][ny]){ // 문명이 아닌곳을 갔을 경우
                        checkPoint.add(new Pair(nx, ny));
                        visited[nx][ny] = true;
                    }
                }
            }
        }
    }
    static void check(int x, int y){
        int c = (x-1) * N + y;
        for(int i=0;i<4;++i){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(checkRange(nx,ny)){
                int next = (nx-1) * N + ny; 
                int cP = findP(c);
                int nP = findP(next);
                if(cP != nP && keySet.contains(cP) && keySet.contains(nP)){ // 서로 다른 문명인 경우
                    unionP(c,next);
                }
            }
        }
    }
}
/**
 * 
 
0 0 0
2 0 4
1 0 3 

0 0 0 => day 0
1 0 3
1 0 3


=> day 0
1 0 0 0 2
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
3 0 0 0 4
=> day 1
1 1 0 2 2
1 0 0 0 2
0 0 0 0 0
3 0 0 0 4
3 3 0 4 4
=> day 2
1 1 1 1 1
1 1 0 1 1
1 0 0 0 1
1 1 0 1 1
1 1 1 1 1
 */