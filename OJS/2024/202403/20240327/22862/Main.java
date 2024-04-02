import java.io.*; 
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] arr = new int[a];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<a;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }   
        int s = 0;
        int e = 0;
        int cnt = 0;
        int ans = 0;
        boolean[] contain = new boolean[a];
        while( s < a && e < a){
            if(arr[e]%2 == 0){
                contain[e] = true;
            }else cnt++;
            if(cnt > k){
                while(true){
                    if(!contain[s]){
                        cnt--;
                    }
                    s++;
                    if(cnt <= k){
                        break;
                    }
                }
            }
            //System.out.printf("%d, %d, %d\n", arr[s], arr[e], cnt);
            e++;
            ans = Math.max(ans,(e - s) - cnt);
        }
        System.out.println(ans);
    }
}
