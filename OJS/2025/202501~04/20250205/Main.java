import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int S, C, MAX_LEN;
    static int[] L;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in ));        
        StringTokenizer st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        L = new int[S];
        for(int i=0;i<S;++i){
            L[i] = Integer.parseInt(br.readLine());
            MAX_LEN = Math.max(MAX_LEN, L[i]);
        }
        long s = 1, e = MAX_LEN, MAX = 0;
        while(s <= e){
            long m = (s + e) / 2;
            long re = get_max_num(m);
            //System.out.printf("%d, %d\n", m, re);
            if(re < C){
                e = m - 1;
            }else{
                s = m + 1;
                MAX = m;
            }
        }
        //System.out.printf("%d, %d\n", s, e);
        System.out.println(get_ramen(MAX));

    }   
    static long get_max_num(long l){
        long re = 0;
        for(int i=0;i<S;++i){
            re += L[i]/l; // 잘라는 파의 개수
        }
        return re;
    }
    static long get_ramen(long l){
        long pa = 0;
        for(int i=0;i<S;++i){
            pa += L[i]; // 모든 파 길이 더하고
        }
        return pa - l * C; // 파닭에 쓸 만큼만 남기고 라면에 넣자!
    }
}

