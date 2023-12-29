import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int N;
    static long K;
    static long[] list;
    static boolean check(long t){
        long cnt = 0;
        for(int i=0;i<N;++i){
            if(list[i] >= t) continue;
            else cnt += (t - list[i]);
        }
        return cnt <= K;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        N = Integer.parseInt(temp[0]);
        K = Long.parseLong(temp[1]);
        list = new long[N];
        long s = 0, e = 0;
        for(int i=0;i<N;++i){
            list[i] = Long.parseLong(br.readLine());
            e = Math.max(e, list[i] + K);
        }
        while(s <= e){
            long m = (s + e) / 2; 
            if(check(m)) s = m + 1;
            else e = m - 1;
        }
        System.out.println(e);



    }
}
