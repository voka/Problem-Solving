import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int N;
    static int[] ability;
    static int lowerBound(int target){
        int s = 0, e = N, m = -1;
        while(s < e){
            m = (s+e)/2;
            if(ability[m] >= target){
                e = m;
            }else{
                s = m + 1;
            }
        }
        return e;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        String[] temp = br.readLine().split(" ");
        ability = new int[N];
        for(int i=0;i<N;++i){
            ability[i] = Integer.parseInt(temp[i]);
        }
        Arrays.sort(ability);
        long answer = 0;
        for(int i=0;i<N-2;++i){
            int s = i+1, e = N-1;
            while(s<e){
                int ai = ability[i], as = ability[s], ae = ability[e];
                int cur = ai + as + ae;
                if(cur == 0) {
                    if(as != ae) answer += (e - lowerBound(ae) + 1);
                    else answer += (e - s);
                }
                if(cur > 0) e -= 1;
                else s += 1;
            }
        }
        System.out.println(answer);
        
    }
}