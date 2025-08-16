
import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/1717
public class 유니온파인드 {
	static int V, Q ;
	static int[] parent, size;
	static int findP(int x) {
        // 여기서 이 부분이 중요합니다. 
        // return findP(parent[x]) 이렇게만 써도 이 함수의 목적(부모 노드 찾기)은 달성할 수 있어요.
        // 그런데도 return parent[x] = findP(parent[x]) 라고 쓰는 이유가 아까 하준이가 말한 부분인데 
        // parent 배열에 업데이트를 해주지 않으면 union이 많아질수록 dfs로 부모를 찾는 과정이 길어지기 때문에 비효율적이고 TLE(시간초과)가
        // findP 함수가 동작하면서 parent 배열이 업데이트 되도록 저렇게 작성하는 거에요!
		
        if(x != parent[x]) return parent[x] = findP(parent[x]);
		return parent[x];
	}
	
	static void unionP(int a, int b) {
		int AP = findP(a);
		int BP = findP(b);
		int root,child;
		if(size[AP] < size[BP]) {
			root = BP;
			child = AP;
		}else {
			root = AP;
			child = BP;
		}
		size[root] += size[child];
		parent[child] = root;
	}
	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		parent = new int[V+1];
		size = new int[V+1];
        // parent, size 배열 초기화
		for(int i=1;i<=V;++i) {
			size[i] = 1;
			parent[i] = i;
		}
		for(int i=0;i<Q;++i) {
			st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
            if(command == 0){
                if(findP(a) != findP(b)){
                    unionP(a, b);
                }
            }else{
                // 같은 집합이면 Yes, 아니라면 No
                System.out.println((findP(a) == findP(b)) ? "YES" : "NO"); 
            }
        }
	}

}