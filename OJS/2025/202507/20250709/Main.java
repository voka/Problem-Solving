import java.io.*;
import java.util.*;
// sw expert academy
class Point{
	int x;
	int y;
	public Point(int x,int y) {
		this.x = x;
		this.y = y;
	}
}
public class Main
{
	static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
	static int[][] arr, dp;
	static int sx,sy,ex,ey;
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int N = Integer.parseInt(br.readLine());
			arr = new int[N][N];
			dp = new int[N][N];
			StringTokenizer st;
			for(int i=0;i<N;++i) {
				st = new StringTokenizer(br.readLine());
				for(int j=0;j<N;++j) {
					arr[i][j] = Integer.parseInt(st.nextToken());
					dp[i][j] = Integer.MAX_VALUE;
				}
			}
			st = new StringTokenizer(br.readLine());
			sx = Integer.parseInt(st.nextToken());
			sy = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(br.readLine());
			ex = Integer.parseInt(st.nextToken());
			ey = Integer.parseInt(st.nextToken());
			System.out.printf("#%d %d\n",test_case,solve(N)==Integer.MAX_VALUE ? -1 : dp[ex][ey]);
			//printArr(N);
			
		}
	}
	public static boolean check(int N, int x, int y) {
		return (x>=0) && (y>=0) && (x<N) && (y<N);
	}
	public static void printArr(int N) {
		for(int i=0;i<N;++i) {
			for(int j=0;j<N;++j) {
				System.out.printf("%d ", dp[i][j]==Integer.MAX_VALUE ? -1: dp[i][j]);
			}
			System.out.println();
		}
	}
	public static int solve(int N) {
		Queue<Point> q = new ArrayDeque<>();
		q.add(new Point(sx,sy));
		dp[sx][sy] = 0;
		while(!q.isEmpty()) {
			Point c = q.poll();
			for(int i=0;i<4;++i) {
				int nx = c.x + dx[i];
				int ny = c.y + dy[i];
				if(!check(N,nx,ny) || arr[nx][ny] == 1) continue;
				int ncost = 0;
				int cost = dp[c.x][c.y];
				if(arr[nx][ny] == 2) {
					if(cost == 0 || cost % 3 == 0) {
						ncost = dp[c.x][c.y] + 3;
					}else if(cost == 1 || (cost-1) % 3 == 0) {
						ncost = dp[c.x][c.y] + 2;
					}else if(cost == 2 || (cost-2) % 3 == 0) {
						ncost = dp[c.x][c.y] + 1;
					}
					
				}else {
					ncost = cost + 1;
				}
				if(dp[nx][ny] > ncost) {
					dp[nx][ny] = ncost;
					q.add(new Point(nx,ny));
				}
			}
		}
		
		return dp[ex][ey];
	}
}
// 0-1 /2/ 3-4 /5/ 6-7/8
// 0, 3, 6, 9 -> +2 -> +3 -> x==0 || x%3 == 0
// 1, 4, 7, 10 -> +1 -> +2 -> x==1 || (x-1)%3 == 0
// 2, 5, 7 -> x==2 || (x-2)%3 == 0