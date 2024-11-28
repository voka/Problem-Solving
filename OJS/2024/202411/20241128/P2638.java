import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair{
    int a;
    int b;
    public Pair(int a, int b){
        this.a = a;
        this.b = b;
    }
}
public class P2638 {
    static int N, M;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static int[][] map, temp;
    static boolean[][] visit;
    static Queue<Pair> que;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            } 
        }
        int day = 0;
        while(true){
            if(isClear()){
                break; 
            }
            //printMap();
            check();
            hour();
            day++;
        }

        //printMap();
        System.out.println(day);
        
    }
    static void check(){
        que = new LinkedList<>();
        visit = new boolean[N][M];
        que.add(new Pair(0, 0));
        visit[0][0] = true;
        while(!que.isEmpty()){
            Pair cur = que.poll();
            for(int i=0;i<4;++i){
                int nx = cur.a + dx[i];
                int ny = cur.b + dy[i];
                if(guard(nx, ny) && !visit[nx][ny] && map[nx][ny] == 0){
                    que.add(new Pair(nx, ny));
                    visit[nx][ny] = true;
                }
            }
        }

    }
    static void printMap(){
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static boolean guard(int x, int y){
        return (x >= 0) && (y >= 0) && (x < N ) && (y < M);
    }
    static void hour(){
        temp = new int[N][M];
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(map[i][j] == 1){ // 치즈인 경우
                    for(int k=0;k<4;++k){
                        int nx = i + dx[k];
                        int ny = j + dy[k];
                        if(visit[nx][ny] && map[nx][ny] == 0){
                            temp[i][j]++;
                        }
                    }

                }
            }
        }
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(temp[i][j] >= 2){
                    map[i][j] = 0;
                }
            }
        }
    }
    static boolean isClear(){
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(map[i][j] != 0){
                    return false;
                }
            }
        }
        return true;
    }
}
