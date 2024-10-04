//https://www.acmicpc.net/problem/9024

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class Pair{
    int a;
    int b;
    public Pair(int a, int b){
        this.a = a;
        this.b = b;
    }
}
public class P9024 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while(T>0){
            T--; 
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int[] nums = new int[N];
            for(int i=0;i<N;++i){
                nums[i] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(nums);
            // 가장 가까운 수가 K랑 얼마나 차이 나는지 구하기
            int cnt = 0;
            int minAbs = 2 * 100000000 + 1;
            int s = 0, e = N-1;
            while(s < e){
                int sum = nums[s] + nums[e];
                if(Math.abs(sum - K) < Math.abs(minAbs - K)){
                    minAbs = sum;
                    cnt = 1;
                }
                else if(Math.abs(sum - K) == Math.abs(minAbs - K)){
                    cnt++;
                }
                if(sum > K) e--;
                else s++;
            }
            sb.append(cnt).append("\n");
        }
        System.out.println(sb.toString());
       
    }
}
