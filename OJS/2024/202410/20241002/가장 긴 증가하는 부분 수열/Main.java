import java.io.*;
import java.util.*;
public class Main {
    static int N;
    static List<Integer> LIS = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());    
        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int f = Integer.parseInt(st.nextToken());
        LIS.add(f);
        for(int i=1;i<N;++i){
            int c = Integer.parseInt(st.nextToken());
            if( LIS.get(LIS.size()-1) < c){ // LIS 의 가장 작은 값이 현재 값보다 작다면 LIS 배열에 추가
                LIS.add(c);
            }else{
                int r = lowerbound(c);
                LIS.set(r,c);
            }
        }
        System.out.println(LIS.size());
    
    }
    static int lowerbound(int target){
        int s = 0;
        int e = LIS.size()-1;
        while(s<e){
            int m = (s + e)/2;
            if(LIS.get(m) < target){
                s = m + 1;
            }else{
                e = m;
            }
        }
        return e;
    }
}
