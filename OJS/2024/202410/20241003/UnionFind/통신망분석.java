import java.io.*;
import java.util.*;
//https://level.goorm.io/exam/195699/%EA%B7%B8%EB%9E%98%ED%94%84%EC%9D%98-%EB%B0%80%EC%A7%91%EB%8F%84/quiz/1
class Pair{
	int x;
	int y;
	public Pair(int x, int y){
		this.x = x;
		this.y = y;
	}
}
class Main {
	static int[] parent;
	static int N,M;
	static List<Pair> road;
	static int findP(int x){
		if(parent[x] != x){
			parent[x] = findP(parent[x]);
		}
		return parent[x];
	}
	static void unionP(int a, int b){
		int Pa = findP(a);
		int Pb = findP(b);
		if(Pa < Pb) parent[Pb] = Pa;
		else parent[Pa] = Pb;
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		parent = new int[N+1];
		road = new ArrayList<>();
		for(int i=0;i<=N;++i){
			parent[i] = i;
		}
		boolean[] in = new boolean[N+1];
		for(int i=0;i<M;++i){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			unionP(a,b);
			road.add(new Pair(a,b));
			in[a] = true;
			in[b] = true;
		}
		int[] groupidx = new int[N+1];
		int[] roadCount = new int[N+1];
		List<List<Integer>> group = new ArrayList<>();
		group.add(new ArrayList<>());
		for(int i=1;i<=N;++i){
			findP(i);
		}
		for(int i=1;i<=N;++i){
			if(!in[i]) continue;
			int cp = findP(i);
			if(groupidx[cp] == 0){
				group.add(new ArrayList<>());
				groupidx[cp] = group.size() - 1;
			}
			group.get(groupidx[cp]).add(i);
		}
		for(Pair p : road){
			roadCount[findP(p.x)]++;
		}
		boolean[] check = new boolean[N+1];
		float maxDeep = 0;
		int comNum = 100001;
		int minNum = 100001;
		int answeridx = -1;
		for(int i=1;i<=N;++i){
			if(!in[i]) continue;
			int cp = findP(i);
			if(!check[cp] && groupidx[cp] != 0){
				check[cp] = true;
				float deep = (float) roadCount[cp] / group.get(groupidx[cp]).size();
				if(maxDeep < deep){
					maxDeep = deep;
					answeridx = groupidx[cp];
					comNum = roadCount[cp];
					minNum = group.get(groupidx[cp]).get(0);
				}else if(maxDeep == deep){
					if(comNum > roadCount[cp]){
						answeridx = groupidx[cp];
						comNum = roadCount[cp];
						minNum = group.get(groupidx[cp]).get(0);
					}else if(comNum == roadCount[cp]){
						if(minNum > group.get(groupidx[cp]).get(0)){
							answeridx = groupidx[cp];
							minNum = group.get(groupidx[cp]).get(0);
						}
					}
				}
			}
		}
		for(int id : group.get(answeridx)){
			System.out.printf("%d ", id);	
		}
	}
}