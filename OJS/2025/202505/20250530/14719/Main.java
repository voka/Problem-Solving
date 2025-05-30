import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] arr ;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int H = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        arr = new int[W];
        int answer = 0;
        for(int i=0;i<W;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=1;i<=W-1;++i){
            int l = 0;
            int r = 0;
            for(int j=0;j<i;++j){
                l = Math.max(arr[j],l);
            }
            for(int j=i+1;j<W;++j){
                r = Math.max(arr[j],r);
            }
            if(arr[i] < l && arr[i] < r) answer += Math.min(l,r) - arr[i];
            //System.out.printf("%d, %d\n", arr[i], answer);
        }
        System.out.println(answer);
    }
}
