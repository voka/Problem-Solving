import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] arr ;
    static HashMap<Integer, Integer> cnt = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int maxL = 0;
        int l = 0;
        arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        for(int r=0;r<N;++r){
            cnt.put(arr[r], cnt.getOrDefault(arr[r], 0) + 1);
            while(cnt.size() > 2){
                cnt.put(arr[l], cnt.get(arr[l]) - 1);
                if(cnt.get(arr[l]) == 0){
                    cnt.remove(arr[l]);
                }
                l++;
            }
            maxL = Math.max(maxL,r-l+1);
        }
        System.out.println(maxL);
    }
}
