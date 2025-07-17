/**
 * 
 1. 경로별 최소 비용으로 기존 값 업데이트
 2. 각 질의에 대해 전체 정점 수만큼 비교해서 개수 세기
 */
import java.io.*;
import java.util.*;
class Point{
	int x;
	int c;
	public Point(int x, int c) {
		this.x = x;
		this.c = c;
	}
}
public class Main {
	static int N,Q;
	static boolean[] visit;
	static ArrayList<ArrayList<Point>> graph;
	public static void main(String args[])throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		graph = new ArrayList<>();
		visit = new boolean[N+1];
		for(int i=0;i<=N;++i) {
			graph.add(new ArrayList<>());
 		}
		Q = Integer.parseInt(st.nextToken());
		for(int i=0;i<N-1;++i) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			graph.get(a).add(new Point(b,c));
			graph.get(b).add(new Point(a,c));
		}
		for(int i=0;i<Q;++i) {
			st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			
			System.out.println(count(k,v));
		}
		
	}
	static int count(int k, int v) {
		int re = 0;
		int[] dist = new int[N+1];
		boolean[] visit = new boolean[N+1];
		for(int i=0;i<=N;++i) {
			dist[i] = Integer.MAX_VALUE;
		}
		Queue<Point> q = new ArrayDeque<>();
		q.add(new Point(v,0));
		dist[v] = 0;
		visit[v] = true;
		while(!q.isEmpty()) {
			Point c = q.poll();
			for(Point n: graph.get(c.x)) {
				if(visit[n.x]) continue;
				int minV = c.x == v ? n.c:Math.min(c.c, n.c);
				//System.out.printf("%d -> %d : %d vs %d",v,n.x,n.c, dist[n.x]);
				if(dist[n.x] > minV) {
					dist[n.x] = minV;
					q.add(new Point(n.x,minV));
					visit[n.x] = true;
				}
				//System.out.printf("  win : %d\n",dist[n.x]);
			}
			
		}
		//System.out.println(v);
		for(int i=1;i<=N;++i) {
			//System.out.printf("%d : %d\n",i,dist[i]);
			if(i == v) continue;
			if(dist[i] != Integer.MAX_VALUE && dist[i] >= k) re++;
		}
		return re;
	}
	
}
