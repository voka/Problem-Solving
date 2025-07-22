import java.io.*;
import java.util.*;
class Point{
	int x;
	int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
public class Main {
	//static int cnt;
	static int N,M,MINH,MAXH,answer;
	static boolean[][] in, visit;
	static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
	static int[][] map;
	public static void main(String args[])throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
        MINH = Integer.MAX_VALUE;
		String s;
		//cnt = 0;
		map = new int[N][M];
		in = new boolean[N][M];
		for(int i=0;i<N;++i) {
			s = br.readLine();
			int j=0;
			for(char c : s.toCharArray()) {
				int cur = c - '0';
				map[i][j++] = cur;
				MAXH = Math.max(cur, MAXH);
                MINH = Math.min(cur,MINH);
			}
		}

//		for(int i=0;i<N;++i) {
//			for(int j=0;j<M;++j) {
//				System.out.printf("%d ",map[i][j]);
//			}
//			System.out.println();
//		}
		answer = 0;
		for(int h=MINH;h<MAXH;++h) {
		for(int i=1;i<N-1;++i) {
			for(int j=1;j<M-1;++j) {
				int cur = map[i][j];
				if(cur != h) continue;
				//if(in[i][j]) continue;
//					if(bfs(i,j,h)) {
//						break;
//					};

					visit = new boolean[N][M];
					if(bfs(i,j,h)) break;
				}
			}
		}
		//System.out.println(cnt);
		System.out.println(answer);
		
	}
	static boolean bfs(int x, int y, int h) {
		
		Queue<Point> q = new ArrayDeque<>();
		boolean[][] visit = new boolean[N][M];
		visit[x][y] = true;
		q.add(new Point(x,y));
		while(!q.isEmpty()) {
			//cnt++;
			Point p = q.poll();
			for(int i=0;i<4;++i) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(!checkRange(nx,ny)) continue;
				if(visit[nx][ny]) continue;
				if(map[nx][ny] >= h) continue;
				if(isOutLine(nx,ny)) return false; // 바깥쪽 까지 탐색이 이어진다면 
				visit[nx][ny] = true;
				q.add(new Point(nx,ny));
			}
		}
		for(int i=1;i<N-1;++i) {
			for(int j=1;j<M-1;++j) {
				if(visit[i][j]) {
					in[i][j] = true; 
					//System.out.printf("(%d,%d)\n", i,j);
					answer += (h - map[i][j]);
				}
			}
		}
		return true;
		
	}
	static boolean isOutLine(int x, int y) {
		return (x==0) || (y == 0) || (x == N-1) || (y == M-1);
	}
	static boolean checkRange(int x, int y) {
		return (x>=0) && (y>=0) && (x<N) && (y<M);
	}
	
	
}
