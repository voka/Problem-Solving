import java.io.*;
import java.util.*;

class Main {
	static int N;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int[] arr = new int[N];
		for(int i=0;i<N;++i){
			int c = Integer.parseInt(st.nextToken());
			arr[i] = c;
		}
		Arrays.sort(arr);
		
		int answer = N;
		int cMin = arr[0];
		int idMin = 0;
		int cMax = arr[N-1];
		int idMax = N-1;
		for(int i=0;i<K;++i){
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			if(c == 1){
				if(x < cMin) continue;// 숫자가 가장 작은 사람이 x 보다 큰 숫자일때 
				int id = upperbound(arr,x); // x 보다 큰 첫번째 수의 인덱스 찾기
				if( id < 0) continue;
				cMin = arr[id];
				idMin = id;
			}else{
				if(cMax < x) continue; 
				int id = lowerbound(arr,x);
				if( id < 0) continue;
				cMax = arr[id];
				idMax = id;
			}
			if(idMin >= idMax) {
				answer = 0;
				break;
			}
		}
		
		if(idMin >= idMax) {
			answer = 0;
		}else {
			answer = idMax - idMin + 1;
		}
		//System.out.printf("idMin => %d, idMax => %d, cMin = > %d, cMax => %d, total alive : %d\n", idMax, idMin, cMin, cMax, idMax - idMin + 1);
		
		System.out.println(answer);
	}
	static int upperbound(int[] arr, int x){
		int s = 0, e = N-1;
		while(s < e){
			int m = (s+e)/2;
			if(arr[m] <= x){
				s = m + 1;
			}else{
				e = m;
			}
			//System.out.printf("%d,%d,%d\n", s,e,m);
		}
		return e;	
	}// 1 ~ 10, 5 보다 큰 첫번째 수  -> 0, 9 ,m : 4 -> arr[4] = 5  target == 5
	static int lowerbound(int[] arr, int x){
		int s = 0, e = N-1;
		while(s < e){
			int m = (s+e)/2;
			if(arr[m] < x){
				s = m + 1;
			}else{
				e = m;
			}
			//System.out.printf("%d,%d,%d\n", s,e,m);
		}
		return e - 1;	
	}// 1 ~ 10, 4 보다 작은 첫번째 수 -> 0, 9 : m : 4  -> arr[4] = 5  >= 4  // -> e = 4-3 = 3, // 0,3 m : 1 // 1 < 4 --> s = 1 + 1 -> 2 
	
}