import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int N, V;
    static int[] list; 
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in));
        String[] temp = br.readLine().split(" ");
        N = Integer.parseInt(temp[0]);
        V = Integer.parseInt(temp[1]);
        temp = br.readLine().split(" ");
        list = new int[N];

        int max_ = Integer.MIN_VALUE, min_ = Integer.MAX_VALUE;
        int max_idx = -1, min_idx = -1;
        for(int i=0;i<N;++i){
            list[i] = Integer.parseInt(temp[i]);
            if(max_ < list[i]){
                max_ = list[i];
                max_idx = i;
            }
            if(min_ > list[i]){
                min_ = list[i];
                min_idx = i;
            }
            if(max_ - min_ >= V){
                break;
            }
        }
        if(max_ - min_ < V){ //최대 최소의 차이가 V 보다 클 수 없으면 모든 문제를 품
            System.out.println(N);
            return;
        }
        if(V == 0) { // 최대 최소의 차이가 0이면 1문제만 풀어도 된다.
            System.out.println(1);
            return;
        }
        int answer = 3; // 1번 문제는 꼭 풀어야 함.
        int l_idx = Math.max(max_idx, min_idx);
        int s_idx = Math.min(max_idx, min_idx);
        int s_cnt = 0,l_cnt = 0; 
        if(s_idx == 0) answer -= 1;
        else{
            for(int i=2; i < s_idx; i = i + 2){
                s_cnt++;
            }
            //System.out.println(s_cnt);
        }
        for(int i=s_idx+2; i < l_idx; i = i + 2){
            l_cnt++;
        }
        //System.out.println(l_cnt);
        //System.out.printf("s_idx: %d, l_idx: %d, answer: %d\n",s_idx,l_idx, answer + l_cnt + s_cnt);
        System.out.println(answer + l_cnt + s_cnt);
    }
}
