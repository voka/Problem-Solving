import java.io.*;
import java.util.*;
public class Main {
	static int N;
	static int[][] w, dp;
	public static void main(String[] args) throws IOException  {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		w = new int[N][N];

		dp = new int[N][(int) Math.pow(2, N)];
		for(int i=0;i<N;++i) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;++j) {
				w[i][j] = Integer.parseInt(st.nextToken());
			}
			Arrays.fill(dp[i], -1);
		}
		System.out.println(solve(0,0));
	}
	static int solve(int id, int state) {
		if(id == N) {
			return 0;
		}
		if(dp[id][state]!=-1) {
			return dp[id][state];
		}
		dp[id][state] = Integer.MAX_VALUE;
		for(int next=0;next<N;++next) {
			if(!check(next, state)) {
				int cost = w[id][next] + solve(id + 1, visit(next, state));
				if(dp[id][state] > cost) {
					dp[id][state] = cost;
				}
			}
		}
		return dp[id][state];
	}
	static boolean check(int id, int state) {
		if((state & (1 << id)) == (1 << id)) return true;
		return false;
	}
	static int visit(int id, int state) {
		return state | (1 << id);
	}
}
