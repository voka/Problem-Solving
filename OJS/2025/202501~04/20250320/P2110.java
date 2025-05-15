import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P2110 {
    static int N,C;
    static int arr[];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        arr = new int[N];
        for(int i=0;i<N;++i){
            arr[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(arr);
        long s = 1,e = arr[N-1] - arr[0];
        long re = 0;
        while(s <= e){
            int count = 1; // 필요한 공유기 개수
            int p = arr[0];
            long m = (s+e)/2;
            for(int i=1;i<N;++i){
                if(m <= (arr[i] - p)){
                    count++;
                    p = arr[i];
                }
            }
            if(count >= C){
                re = m;
                s = m + 1;
            }else{
                e = m - 1;
            }
        }
        System.out.println(re);
    }    
}
