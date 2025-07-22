import java.io.*;
import java.util.*;

// 백준 11967
class Point{
	int x;
	int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y; 
	}
}
public class Main {
	static int N,M, answer;
	static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
	static ArrayList<ArrayList<Integer>> graph, bGraph;
	public static void main(String args[])throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		graph = new ArrayList<>();
		bGraph = new ArrayList<>();
		for(int i=0;i<N*N;++i) {
			graph.add(new ArrayList<>());
			bGraph.add(new ArrayList<>());
		}
		for(int i=0;i<M;++i) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			int p1 = twoToOne(a, b);
			int p2 = twoToOne(c, d);
			graph.get(p1).add(p2);
			bGraph.get(p2).add(p1);
		
		}
		answer = 0;
		solve();
		System.out.println(answer);
		
		
	}
	static void solve() {
		Queue<Point> q = new ArrayDeque<>();
		Set<Integer> nextP = new HashSet<>();
		boolean[][] visit = new boolean[N][N];
		boolean[][] canGo = new boolean[N][N];
		visit[0][0] = true;
		canGo[0][0] = true;
		q.add(new Point(0,0));
		while(!q.isEmpty()) {
			Point c = q.poll();
			visit[c.x][c.y] = true;
//			System.out.printf("visit :(%d, %d)\n",c.x,c.y);
			int id = twoToOne(c.x,c.y);
			for(int nid : graph.get(id)) {
				Point n = oneToTwo(nid);
				//System.out.printf("open :(%d, %d)\n",n.x,n.y);
				
				if(!canGo[n.x][n.y]) { // 새롭게 길이 열렸을 경우
					for(int i=0;i<4;++i) {
						int nx = n.x + dx[i];
						int ny = n.y + dy[i];
						if(!checkRange(nx,ny)) continue;
						if(!visit[nx][ny]) continue;
						if(nextP.add(twoToOne(nx,ny)));
					}
				}
				canGo[n.x][n.y] = true;
			}
			for(int i=0;i<4;++i) {
				int nx = c.x + dx[i];
				int ny = c.y + dy[i];
				if(!checkRange(nx,ny)) continue;
				if(visit[nx][ny]) continue;
				if(!canGo[nx][ny]) {
					continue;
				}
				visit[nx][ny] = true;
				q.add(new Point(nx,ny));
			}
			if(q.isEmpty() && nextP.size()>0) {
				visit = new boolean[N][N];
				for(int np : nextP) {
					q.add(oneToTwo(np));
				}
				nextP.clear();
			}
		}
		for(int i=0;i<N;++i) {
			for(int j=0;j<N;++j) {
				if(canGo[i][j]) {
					//System.out.printf("(%d, %d)\n",i,j);
					answer++;
				}
			}
		}
	}
	static boolean checkRange(int x, int y) {
		return (x>=0) && (y>=0) && (x<N) && (y<N);
	}
	
	static int twoToOne(int x, int y) {
		return x*N + y;
	}
	static Point oneToTwo(int id) {
		int x = id / N;
		int y = id - x*N;
		return new Point(x,y);
	}
	
}
/*
N = 4, M = ;


4 6
1 2 1 1
1 2 3 3
1 3 1 2
1 3 2 2
3 2 2 3
2 2 2 3
 
4 7
1 1 1 2
1 2 1 3
1 3 2 3
2 3 3 3
3 3 3 4
3 4 4 4
4 4 2 2 

4 6
1 1 1 2
3 3 3 2
3 3 2 3
3 3 4 4
3 3 1 3
1 1 3 3

4 9
1 1 2 1
1 1 3 1
1 1 4 1
4 2 3 3
3 3 2 2
3 1 1 4
4 1 1 2
1 2 4 2
4 2 2 2 

(00 10 20 30 31 11

(11 12 13 23 33 34 44 22) 

(12, 33, 13, 22)
11 -> 12 -> 13
12 -> 22 -> 23 -> 33



*/