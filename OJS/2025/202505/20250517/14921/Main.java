import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int arr[] = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int mGap = 200000001;
        int answer = -1;
        int f = 0;
        int s = N-1;
        for(int i=0;i<N;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        while(f < s){
            int c = arr[f] + arr[s];
            //System.out.printf("%d, %d : %d\n", f, s, c );
            if(mGap > Math.abs(c)){
                mGap = Math.abs(c);
                answer = c;
            }
            //
            int fGap = Math.abs(arr[f+1] + arr[s]);
            int sGap = Math.abs(arr[s-1] + arr[f]);
            if(fGap < sGap){
                f++;
            }else{
                s--;
            }
        }
        System.out.println(answer);
    }    
}
