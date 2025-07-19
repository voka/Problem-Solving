import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] arr;
    static int N, B, answer;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st; 
        for(int tc=1;tc<=T;++tc){
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            arr = new int[N];
            for(int i=0;i<N;++i){
                arr[i] = Integer.parseInt(st.nextToken());
            }
            answer = Integer.MAX_VALUE;
            solve(0,0); 
            System.out.printf("#%d %d\n",tc,answer-B);

        }

    }

    static void solve(int id, int key){
        if(key >= B){
            answer = Math.min(answer,key);
        }
        if(id == N){
            return;
        }
        solve(id+1,key);// 포함 X;
        solve(id+1,key + arr[id]); // 포함 O
    }
}
