import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P10811 {
    static int N,M;
    static int[] my;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        my = new int[N+1];
        for(int i=1;i<=N;++i){
            my[i] = i;
        }
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            reverse(a, b);    
        }
        for(int i=1;i<=N;++i){
            System.out.printf("%d ", my[i]);
        }
        System.out.println();
    }
    static void reverse(int s, int e){
        int len = e - s + 1;
        int[] temp = new int[len];
        int ts = 0;
        for(int i=0;i<len;++i){
            temp[i] = my[e - i];
        }
        for(int i=s;i<=e;++i){
            my[i] = temp[ts++];
        }
    }
}
