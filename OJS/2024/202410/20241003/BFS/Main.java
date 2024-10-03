import java.io.*;
import java.util.*;
///https://level.goorm.io/exam/195695/%EB%B0%9C%EC%A0%84%EA%B8%B0-2/quiz/1
class Pair{
	int x;
	int y;
	public Pair(int x, int y){
		this.x = x;
		this.y = y;
	}
}
class Main {
	static int N, answer, big, K;
	static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
	static int[] group;
	static int[][] map;
	static boolean[][] visit;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		group = new int[31];
		visit = new boolean[N][N];
		for(int i=0;i<N;++i){
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;++j){
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		answer = 0;
		for(int i=0;i<N;++i){
			for(int j=0;j<N;++j){
				if(!visit[i][j]){
					visit[i][j] = true;
					bfs(i,j);
				}
			}
		}
		big = -1;
		for(int i=1;i<=30;++i){
			if(big <= group[i]){
				big = group[i];
				answer = i;
			}
		}
		System.out.println(answer);
	}
	static boolean check(int x, int y){
		return (x>=0) && (y>=0) && (x<N) && (y<N);
	}
	static void bfs(int x, int y){
		Queue<Pair> que = new ArrayDeque<>();
		que.add(new Pair(x,y));
		int cnt = 0;
		while(!que.isEmpty()){
			Pair cur = que.poll();
			cnt++;
			for(int i=0;i<4;++i){
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				if(check(nx,ny) && !visit[nx][ny] && map[nx][ny] == map[cur.x][cur.y]){
					visit[nx][ny] = true;
					que.add(new Pair(nx,ny));
				}
			}
		}
		if(cnt >= K){
			group[map[x][y]]++;
		}
	}
}