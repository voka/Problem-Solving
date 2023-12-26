import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Sub {
    static int N;
    static int[] ability;
    static int lowerBound(int s, int e, int target){
        int m = -1;
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
    static int upperBound(int s, int e, int target){
        int m = -1;
        while(s < e){
            m = (s+e)/2;
            if(ability[m] <= target){
                s = m + 1;
            }else{
                e = m;
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
            for(int j=i+1;j<N-1;++j){
                int ai = ability[i], aj = ability[j];
                int target = -(ai + aj);
                int up = upperBound(j+1, N,target);
                int lo = lowerBound(j+1, N,target);
                int cur = up - lo;
                answer += (cur);
            }
        }
        System.out.println(answer);
        
    }
}
