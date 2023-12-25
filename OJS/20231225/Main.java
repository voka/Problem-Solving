import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int N;
    static long M;
    static long[] tList;
    static boolean check(long t){
        long cnt = 0;
        for(int i=0;i<N;++i){
            cnt += t/tList[i];
            if(cnt >= M ) return true;
        }
        return cnt >= M;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        N = Integer.parseInt(temp[0]);
        M = Long.parseLong(temp[1]);
        tList = new long[N];
        long s = 0, e = 0,m = 0;
        for(int i=0;i<N;++i){
            tList[i] = Long.parseLong(br.readLine());
            e = Math.max(e,tList[i]);
        }
        e *= M;
        while(s <= e){
            m = (s+e)/2;
            if(check(m)){
                e = m - 1 ;
            }
            else{
                s = m + 1;
            }
        }
        System.out.println(s);
    }    
}
