import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, M;
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int answer = Integer.MAX_VALUE;
        int[][] arr = new int[N][M];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;++j){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(arr[i]);
        }
        int[] index = new int[N];
        int minId = -1;
        while(true){
            int maxV = -1;
            int minV = Integer.MAX_VALUE;
            for(int i=0;i<N;++i){
                int cur = arr[i][index[i]];
                if(minV > cur){
                    minId = i;
                    minV = cur;
                }
                if(maxV < cur){
                    maxV = cur;
                }
            }
            int gap = maxV - minV;
            answer = Math.min(answer,gap);
            index[minId]++;
            if(index[minId] >= M) break;
        }
        System.out.println(answer);
    }    
}
