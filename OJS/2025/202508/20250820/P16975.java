import java.io.*;
import java.util.*;

public class P16975 {
	
	static int N,M;
	static int size, H;
	static int[] arr;
	static long[] tree,lazy;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;++i) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		StringBuilder sb = new StringBuilder();
		H = (int) Math.ceil(Math.log(N)/Math.log(2));
		size = (1<<(H+1));
		
		tree = new long[size];
		lazy = new long[size];
		M = Integer.parseInt(br.readLine());
		init(1,0,N-1);
		for(int i=0;i<M;++i) {
			st = new StringTokenizer(br.readLine());
			int c = Integer.parseInt(st.nextToken());
			if(c == 1) {
				int l = Integer.parseInt(st.nextToken())-1;
				int r = Integer.parseInt(st.nextToken())-1;
				long diff = Long.parseLong(st.nextToken());
				updateRange(1,0,N-1,l,r,diff);
				
			}
			else {
                //printSegmentTree();
				int x = Integer.parseInt(st.nextToken())-1;
				sb.append(querySingle(1,0,N-1,x)).append('\n');
			}
			
		}
		System.out.println(sb.toString());
		
	}
	static void init(int node, int s, int e) {
		if(s == e) {
			tree[node] = arr[s];
		}else {
			int m = (s+e)>>>1;
			init(node<<1,s,m);
			init(node<<1|1,m+1,e);
			tree[node] = tree[node<<1] + tree[node<<1|1];
		}
	}
	static long querySingle(int node, int s, int e, int id) {
		updateLazy(node,s,e);
		if(s==e) {
			return tree[node];
		}
		int m = (s+e)>>>1;

        if( id <= m) return querySingle(node << 1, s, m, id);
        else return querySingle(node << 1 | 1, m+1, e, id);
    }
	static void updateLazy(int node, int s, int e) {
		if(lazy[node] != 0) {
			tree[node] += lazy[node];
			if(s != e) {
				lazy[node<<1] += lazy[node]; // 왼쪽 합 갱신
				lazy[node<<1|1] += lazy[node]; 
			}
			lazy[node] = 0;
		}
	}
	static void updateRange(int node, int s, int e, int l, int r , long diff) {
		updateLazy(node,s,e);
		if(l > e || r < s) return;
		if(l <= s && e <= r) {
            lazy[node] += diff;
            updateLazy(node, s, e);
			return;
		}
		int m = (s+e)>>>1;
		updateRange(node << 1, s, m, l, r, diff);
		updateRange(node << 1 | 1, m+1, e, l, r, diff);
		tree[node] = tree[node<<1] + tree[node<<1|1];
	}
    static  void printSegmentTree() {
        int height = (int) (Math.ceil(Math.log(tree.length) / Math.log(2)));
        int levelSize = 1;
        int index = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < levelSize; j++) {
                if (index < tree.length) {
                    for (int k = 0; k < (int) (Math.pow(2, height - i - 1)) - 1; k++) {
                        System.out.print("   ");
                    }
                    System.out.print(tree[index] + " ");
                    index++;
                }
            }
            System.out.println();
            levelSize *= 2;
        }
    }
}
