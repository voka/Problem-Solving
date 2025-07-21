
import java.io.*;

import java.util.*;


// 플로이드 + 백트래킹(완전탐색) -> N^3 + 2^N -> N 최대 10
public class Main {
    
	static int N,S,answer;
    static int[][] arr;
	public static void main(String args[])throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
        arr = new int[N][N];
		for(int i=0;i<N;++i) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;++j) {
                arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

        for(int k=0;k<N;++k){
            for(int i=0;i<N;++i){
                for(int j=0;j<N;++j){
                    if(arr[i][j] > arr[i][k] + arr[k][j]){
                        arr[i][j] = arr[i][k] + arr[k][j];
                    }
                }
            }
        }
        answer = Integer.MAX_VALUE;
        boolean[] visit = new boolean[N];
        visit[S] = true;
		backTrace(S,0,1,visit);
        System.out.println(answer);
		
	}
	static void backTrace(int s, int cost, int cnt, boolean[] visit){
        if(N == cnt){ // 모든 지점을 방문했을 경우
            answer = Math.min(answer, cost);
        }
        for(int i=0;i<N;++i){
            if(!visit[i]){
                visit[i] = true;
                backTrace(i,cost + arr[s][i],cnt+1,visit);
                visit[i] = false;
            }
        }
    }
	
	
}
