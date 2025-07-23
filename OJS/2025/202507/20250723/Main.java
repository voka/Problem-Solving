
//https://www.acmicpc.net/problem/14718
import java.io.*;
import java.util.*;

class Soldier{
	int STR;
	int DEX;
	int INT;
	public Soldier(int STR, int DEX, int INT){
		this.STR = STR;
		this.DEX = DEX;
		this.INT = INT;
	}
	
}
public class Main {
	static int N,K, answer;
	static Soldier[] arr;
	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		arr = new Soldier[N];
		
		for(int i=0;i<N;++i) {
			st = new StringTokenizer(br.readLine());
			arr[i] = new Soldier(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
		}
		answer = Integer.MAX_VALUE;
		for(int i=0;i<N;++i) {
			for(int j=0;j<N;++j) {
				for(int k=0;k<N;++k) { // 모든 힘, 민첩, 지능의 조합을 구해서 각 조합별로 몇명의 용사를 이길 수 있는지 체크
					if(check(new Soldier(arr[i].STR, arr[j].DEX, arr[k].INT))) {
						answer = Math.min(answer, arr[i].STR + arr[j].DEX + arr[k].INT);
					}
				}
			}
		}
		System.out.println(answer);
	}
	static boolean check(Soldier s) {
		int cnt = 0;
		for(int i=0;i<N;++i) {
			if(arr[i].STR <= s.STR && arr[i].DEX <= s.DEX && arr[i].INT <= s.INT) {
				cnt++;
			}
		}
		return cnt >= K;
	}
	
}