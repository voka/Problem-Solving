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
    static Queue<Pair> nextCheckPoint = new LinkedList<>();
    
    static int N,M; 
    static int[][] map;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1}, parents;
    static Set<Integer> myKeys = new HashSet<>();
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        parents = new int[N * M + 1];
        map = new int[N][M];
        visited = new boolean[N][M];

        for(int i=0;i<N;++i){
            String temp = br.readLine().strip();
            for(int j=0;j<M;++j){
                map[i][j] = temp.charAt(j) - '0';
                if(map[i][j] == 0){
                    myKeys.add(i * M + j);
                }
                // parents 배열 초기화
                parents[i*M+j] = i*M+j;
            }
        }
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(!visited[i][j] && map[i][j] == 0){
                    firstBfs(i, j);
                }
            }
        }
        int day = 0;
        int preDay = 0;
        int preFireSize = countAllFire();
        int preKeySize = myKeys.size();
        //불 옆의 나무가 남아 있다면 아직 불이 합쳐질 가능성이 있으므로 계속 진행
        while(!nextCheckPoint.isEmpty()){ 
            if(myKeys.size() == 1) break; // 불이 하나로 합쳐졌다면 중지
            solve();
            day+= 1;
            if(preKeySize != myKeys.size()){ //Key 사이즈가 줄어들었다면 => 불이 합쳐졌다면
                preFireSize = countAllFire(); //정답 최신화
                preKeySize = myKeys.size(); // keySize 최신화
                preDay = day;
            }
        }
        // keySize가 1이 되었다면 정답 다시 구하기
        if(preKeySize != myKeys.size()) System.out.printf("%d %d\n",day,countAllFire());
        else System.out.printf("%d %d\n",preDay,preFireSize);
    }   
    static int countAllFire(){ // bfs로 모든 불의 크기 구하기
        boolean[][] newVisited  = new boolean[N][M]; 
        int fire = 0;
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(!newVisited[i][j] && map[i][j] == 0){
                    fire += countFire(i, j, newVisited);
                }
            }
        }
        return fire;
    }
    static int findP(int x){
        if(parents[x] != x){
            parents[x] = findP(parents[x]);
        }
        return parents[x];
    }

    static void unionP(int a, int b){
        a = parents[a];
        b = parents[b];
        // 불의 시작점을 항상 부모의 우선순위로 두기
        if(myKeys.contains(a) && myKeys.contains(b)){ 
            if(a < b){
               parents[b] = a;
               myKeys.remove(b); 
            }else{
                parents[a] = b;
                myKeys.remove(a);
            }
        }else if(myKeys.contains(a)){
            parents[b] = a;
        }else{
            parents[a] = b;
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x <N) &&  (y<M);
    }

    static void firstBfs(int x, int y){
        Queue<Pair> myQueue = new LinkedList<>();
        visited[x][y] = true;
        myQueue.add(new Pair(x, y));
        while(!myQueue.isEmpty()){
            Pair cur = myQueue.poll();
            int c = cur.x * M + cur.y;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny) && !visited[nx][ny]){
                    visited[nx][ny] = true;
                    Pair next = new Pair(nx, ny);
                    int n = nx * M + ny;
                    if(map[nx][ny] == 1){ // 나무인 경우
                        nextCheckPoint.add(next);
                    }
                    if(map[nx][ny] == 0){ // 불인 경우
                        if(findP(c) != findP(n)) unionP(c, n);
                        myQueue.add(next);
                    }
                }
            }
        }
    }
    static int countFire(int x, int y, boolean[][] newVisited){
        int count = 1;
        Queue<Pair> myQueue = new LinkedList<>();
        newVisited[x][y] = true;
        myQueue.add(new Pair(x, y));
        while(!myQueue.isEmpty()){
            Pair cur = myQueue.poll();
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx, ny) && !newVisited[nx][ny]){
                    newVisited[nx][ny] = true;
                    Pair next = new Pair(nx, ny);
                    if(map[nx][ny] == 0){ // 불인 경우
                        myQueue.add(next);
                        count+=1;
                    }
                }
            }
        }
        return count;

    }
    //이번에 나무 -> 불로 바뀔 좌표들만 뽑아내서 다음 불이될 나무들을 찾고 불과 불을 Union해줌.
    static void solve(){
        int size = nextCheckPoint.size();
        for(int i=0;i<size;++i){
            Pair cur = nextCheckPoint.poll();
            int c = cur.x * M + cur.y;
            map[cur.x][cur.y] = 0;
            for(int d=0;d<4;++d){
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                int n = nx * M + ny;
                if(checkRange(nx, ny)){
                    if(map[nx][ny] == 0){ // 불인 경우 
                        if(findP(c) != findP(n)) unionP(c, n);
                    }
                    if(map[nx][ny] == 1 && !visited[nx][ny]){ // 나무인 경우
                        visited[nx][ny] = true;
                        nextCheckPoint.add(new Pair(nx, ny));
                    }
                }
            }
        }
    } 
}
