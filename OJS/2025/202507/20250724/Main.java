import java.io.*;
import java.util.*;
 //25515
public class Main {
	static int N;
	static ArrayList<Integer>[] next;
	static int[] value;
	static boolean[] visit;
	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());

		next = new ArrayList[N];
		value = new int[N];
		visit = new boolean[N];
		
		for(int i=0;i<N;++i) {
			next[i] = new ArrayList<>();
		}
		for(int i=0;i<N-1;++i) {
			st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			next[p].add(c);
		}
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;++i) {
			value[i] = Integer.parseInt(st.nextToken());
		}
		visit[0] = true;
		System.out.println(solve(0,0));
	}
	static long solve(int id, long v) {
		
		long temp = value[id]; // 현재 노드 값은 한번만 계산 
		for(int n : next[id]) {
			if(visit[n]) continue;
			visit[n] = true;
			temp = Math.max(temp, temp + solve(n,v)); // 자식 노드를 돌면서 방문하는게 더 이득인 경우에만 방문
		}
		return v + temp;
	}
	
}
