import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class P2015 {
    static int[] sum, arr;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        arr = new int[N];
        sum = new int[N+1];// 누적합 배열
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;++i){
            arr[i] = Integer.parseInt(st.nextToken());
            sum[i+1] = sum[i] + arr[i];
        }
        long answer = 0;
        //
        HashMap<Integer,Integer> my = new HashMap<Integer,Integer>();  // 누적합 , 값의 개수
        for(int i=1;i<=N;++i){
            if(sum[i] == K) answer++; // 현재 누적합이 K와 같다면 answer 증가
            answer += my.getOrDefault(sum[i] - K, 0); // 현재 누적합에 - K를 한 값이 hash에 존재한다면 그 개수만큼 정답 증가
            my.put(sum[i], my.getOrDefault(sum[i],0)+1); // hash에 현재 누적합이 있다면 그 개수를 1 증가시키고, 없다면 1개로 추가
            
            //System.out.printf("%d %d\n", sum[i],answer);
        }
        // Set<Integer> keys = my.keySet();
        // for(Integer key : keys){
        //     System.out.printf("%d , %d \n",key, my.get(key));
        // }
        System.out.println(answer);
    }
}
