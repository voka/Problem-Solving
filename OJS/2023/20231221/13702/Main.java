import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int N, K;
    static long[] list;
    static boolean check(long target){
        int cnt = 0;
        for(int i=0;i<N;++i){
            cnt += (list[i] / target);
        }
        return cnt >= K;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        N = Integer.parseInt(temp[0]);
        K = Integer.parseInt(temp[1]);
        list = new long[N];
        for(int i=0;i<N;++i){
            list[i] = Long.parseLong(br.readLine());
        }
        long s = 0,m = 0;
        long e = Long.MAX_VALUE;
        while(s <= e){
            m = (s + e) / 2;
            if(check(m)){
                s = m + 1;
            }
            else{
                e = m - 1;
            }
        }
        System.out.println(e);
    }
}
