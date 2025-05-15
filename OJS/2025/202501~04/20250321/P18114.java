import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P18114 {
    static int N,C;
    static int[] tx;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        tx = new int[N];
        st = new StringTokenizer(br.readLine());
        boolean end = false;
        for(int i=0;i<N;++i){
            tx[i] = Integer.parseInt(st.nextToken());
            if(tx[i] == C){
                end = true;
            }
        }
        if(!end){
            Arrays.sort(tx);
            for(int i=0;i<N;++i){
                for(int j=i+1;j<N;++j){
                    int cur = tx[i] + tx[j];
                    if(cur == C){
                        end = true;
                    }else{
                        int target = C - cur;
                        if(target < 0) continue;
                        int s = 0, e = N-1;
                        if(target < tx[i]) e = i-1;
                        else if(target > tx[j]) s = j+1;
                        else {
                            if(i - j < 2) continue;
                            s = i+1;
                            e = j-1;
                        }
                        while(s <= e){
                            int m = (s+e)/2;
                            if(tx[m] == target){
                                end = true;
                                break;
                            }
                            if(tx[m] < target){
                                s = m + 1;
                            }else{
                                e = m - 1;
                            }
                        }
                    }
                    if(end){
                        break;
                    }
                }
                if(end){
                    break;
                }
            }            
        }
        if(end){
            System.out.println(1);
        }else{
            System.out.println(0);
        }

    }
}