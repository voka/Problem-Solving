import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
class Pair implements Comparable<Pair>{
    int x;
    int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public int compareTo(Pair p){
        return y - p.y;
    }

}
public class P2539 {
    static int r,c,pn,p;
    static Pair[] x;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        pn = Integer.parseInt(br.readLine().trim());
        p = Integer.parseInt(br.readLine().trim());
        x = new Pair[p];
        int minre = 0;
        for(int i=0;i<p;++i){
            st = new StringTokenizer(br.readLine());
            int tx = Integer.parseInt(st.nextToken());
            int ty = Integer.parseInt(st.nextToken());
            x[i] = new Pair(tx, ty);
            minre = Math.max(minre,tx);
        }
        Arrays.sort(x);
        int s = minre, e = 10000001;
        int re = e;
        while(s<=e){
            int m = (s + e)/2;
            int cnt = 1;
            int pre = x[0].y;
            for(int i=1;i<p;++i){
                if(pre + m <= x[i].y){
                    cnt++;
                    pre = x[i].y;
                }
            }
            if(cnt <= pn){
                re = m;
                e = m - 1;
            }else{
                s = m + 1;
            }
        }
        System.out.println(re);
    }
}