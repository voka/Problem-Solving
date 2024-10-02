import java.io.*;
import java.util.*;
class Pair{
    int a; 
    int b; 
    int dis;
    public Pair(int a, int b){
        this.a = a;
        this.b = b;
        this.dis = 1;
    }
}
public class Main {
    static int[][] map;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static int N,M,answer = 0,maxdis = 0;
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;++j){
                int c = Integer.parseInt(st.nextToken());
                map[i][j] = c;
            }   
        }
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(map[i][j] != 0){
                    bfs(i,j);
                }
            }
        }
        System.out.println(answer);
        
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x < N) && (y< M);
    }
    static void bfs(int x, int y){
        boolean[][] visit = new boolean[N][M];
        Queue<Pair> que = new ArrayDeque<>();
        que.add(new Pair(x,y));
        visit[x][y] = true;
        int s = map[x][y]; 
        int maxDis = 1;
        int maxDisEnd = s;
        while(!que.isEmpty()){
            Pair cur = que.poll();
            if(cur.dis > maxDis){
                maxDis = cur.dis;
                maxDisEnd = map[cur.a][cur.b];
            }
            for(int i=0;i<4;++i){
                int nx = cur.a + dx[i];
                int ny = cur.b + dy[i];
                if(checkRange(nx, ny) && map[nx][ny] != 0 && !visit[nx][ny]){
                    visit[nx][ny] = true;
                    Pair next = new Pair(nx, ny);
                    next.dis = cur.dis + 1;
                    que.add(next);
                }
            }
        }
        //System.out.printf("%d -> %d\n",s, maxDisEnd );
        if(maxdis < maxDis){
            maxdis = maxDis;
            answer = s + maxDisEnd;
        }else if(maxdis == maxDis){
            answer = Math.max(answer,s + maxDisEnd);
        }
    }
}
