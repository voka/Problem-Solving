import java.io.*;
import java.util.*;

class Point{
	int x;
	int y;
	public Point(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public boolean check(Point a) {
		return (this.x == a.x) && (this.y == a.y);
	}
}
public class Main {
    static int N, K;
	static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};//하, 우, 상, 좌
	static boolean[][] meet;
	static int[][] map;
    static int[][][] road;
	public static void main(String args[])throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(st.nextToken());
		map = new int[N][N];
        road = new int[N][N][4];
        meet = new boolean[N+1][N+1];
		for(int i=0;i<R;++i) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int aa = Integer.parseInt(st.nextToken())-1;
			int bb = Integer.parseInt(st.nextToken())-1;
            if(a == aa){
                if(b < bb){ // 좌, 우
                    road[a][b][1] = 1;
                    road[aa][bb][3] = 1;
                }else{
                    road[a][b][3] = 1;
                    road[aa][bb][1] = 1;
                }
            }else{
                if(a < aa){ // 아래, 위
                    road[a][b][0] = 1;
                    road[aa][bb][2] = 1;
                }else{
                    road[a][b][2] = 1;
                    road[aa][bb][0] = 1;
                }
            }
		}
		Point[] caw = new Point[K+1];
		for(int i=1;i<=K;++i) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			map[a][b] = i;
			caw[i] = new Point(a,b);
		}
        //printMap();
		for(int i=1;i<=K;++i) {
			bfs(caw[i], i);
		}
        int answer = 0;
        for(int i=1;i<=K-1;++i){
            for(int j=i+1;j<=K;++j){
                if(!meet[i][j]) answer++;
            }
        }
        System.out.println(answer);
	}
    static void printMap(){
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
    }
    
	static void bfs(Point p, int id) {
        //System.out.println(id+ "번");
		boolean[][] visit = new boolean[N][N];
		Queue<Point> q = new ArrayDeque<>();
        q.add(p);
        visit[p.x][p.y] = true;
        while(!q.isEmpty()){
            Point c = q.poll();
            for(int i=0;i<4;++i){
                int nx = c.x + dx[i];
                int ny = c.y + dy[i];
                //System.out.printf("%d, %d\n", nx,ny);
                if(!checkRange(nx, ny)) continue;
                if(visit[nx][ny]) continue;
                if(road[c.x][c.y][i]==1) continue;
                if(map[nx][ny] != 0) {
                    int num = map[nx][ny];
                    meet[id][num] = true;
                    meet[num][id] = true;
                }
                visit[nx][ny] = true;
                q.add(new Point(nx, ny));
            } 
        }
        // for(int i=0;i<N;++i){
        //     for(int j=0;j<N;++j){
        //         System.out.printf("%d ", visit[i][j] ? 1 : 0);
        //     }
        //     System.out.println();
        // }
        // System.out.println();

	}
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<N) && (y<N);
    }
	
}
