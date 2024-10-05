import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P13422 {
    static int T;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while(T>0){
            T--;
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int[] my = new int[N];
            for(int i=0;i<N;++i){
                my[i] = Integer.parseInt(st.nextToken());
            }
            int answer = 0;
            int curMoney = 0;
            for(int i=0;i<M;++i){
                curMoney+=my[i];
            }
            //System.out.println(curMoney);
            if(curMoney < K) answer++; 
            if(N == M){ // 모든 집을 한번만 탐색하면 되므로 밑의 반복문을 수행할 필요가 없다.
                sb.append(answer).append('\n');
                continue;
            }
            for(int i=1;i<N;++i){
                //System.out.println(curMoney);
                curMoney = curMoney - my[i-1] + my[(i+M-1)%N]; // 맨 앞 위치의 집을 i+M-1 번째 집으로 교체해준다.
                //System.out.println(curMoney);
                if(curMoney < K){
                    answer++;
                }
            }
            sb.append(answer).append('\n');

        }
        System.out.println(sb.toString());
    }
    
}
