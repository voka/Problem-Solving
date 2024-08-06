import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Rule{
    int a;
    int b;
    int c;
    public Rule(int a, int b, int c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
}

public class P15732 {
    static int N,K,D;
    static Rule[] rules; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        rules = new Rule[K];
        for(int i=0;i<K;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            rules[i] = new Rule(a, b, c);
        }
        int s = 1;
        int e = N;
        while(s <= e){
            long count = 0;
            int m = (s + e)/2;
            for(int i=0;i<K;++i){
                Rule r = rules[i];
                if(r.a > m) continue;
                if(r.b <= m) {
                    count += 1 + (r.b - r.a) / r.c;
                    continue;
                }
                if(m == r.a) count++;
                else count += 1 + (m - r.a) / r.c;
            }
            if(count >= D){
                e = m - 1;
                continue;
            }
            s = m + 1;
        }
        System.out.println(s);

    }
}
