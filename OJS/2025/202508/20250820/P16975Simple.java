import java.io.*;
import java.util.*;

public class P16975Simple {
	
	static int N,M;
	static int size, H;
	static int[] arr;
	static long[] lazy;
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
		
		lazy = new long[size];
		M = Integer.parseInt(br.readLine());
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
	static long querySingle(int node, int s, int e, int id) {
		if(s==e) {
			return arr[id] + lazy[node];
		}
		int m = (s+e)>>>1;

        if( id <= m) return lazy[node] + querySingle(node << 1, s, m, id);
        else return lazy[node] + querySingle(node << 1 | 1, m+1, e, id);
    }
	static void updateRange(int node, int s, int e, int l, int r , long diff) {
		if(l > e || r < s) return;
		if(l <= s && e <= r) {
            lazy[node] += diff;
			return;
		}
		int m = (s+e)>>>1;
		updateRange(node << 1, s, m, l, r, diff);
		updateRange(node << 1 | 1, m+1, e, l, r, diff);
	}
}
