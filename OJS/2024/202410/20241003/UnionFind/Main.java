import java.io.*;
import java.util.*;
// https://level.goorm.io/exam/195698/%EC%97%B0%ED%95%A9/quiz/1
class Main {
	static int N,M,answer = 0;
	static int[] parent;
	static int findP(int x){
		if(parent[x]!= x){
			parent[x] = findP(parent[x]);
		}
		return parent[x];
	}
	static void unionP(int a, int b){
		int Pa = findP(a);
		int Pb = findP(b);
		if(Pa < Pb) parent[Pa] = Pb;
		else parent[Pb] = Pa;
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		boolean[][] road = new boolean[N+1][N+1];
		parent = new int[N+1];
		for(int i=1;i<=N;++i){
			parent[i] = i;	
		}
		for(int i=0;i<M;++i){
			st= new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			road[a][b] = true;
		}
		for(int i=1;i<=N;++i){
			for(int j=i+1;j<=N;++j){
				if(road[i][j] && road[j][i]){
					unionP(i,j);
				}
			}
		}
		boolean[] check = new boolean[N+1];
		for(int i=1;i<=N;++i){
			check[findP(i)] = true;
		}
		answer = 0;
		for(int i=1;i<=N;++i){
			if(check[i]) answer++;
		}
		System.out.println(answer);
		
	}
}