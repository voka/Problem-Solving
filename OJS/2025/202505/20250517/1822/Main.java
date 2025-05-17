import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Set<Integer> setA = new HashSet<>();
        Set<Integer> setB = new HashSet<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;++i){
            int a = Integer.parseInt(st.nextToken());
            setA.add(a);
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<M;++i){
            int b = Integer.parseInt(st.nextToken());
            setB.add(b);
        }
        Set<Integer> ansSet = new HashSet<>();
        for(int a : setA){
            if(!setB.contains(a)){
                ansSet.add(a);
            }
        }
        Integer[] ans = ansSet.toArray(new Integer[0]);
        Arrays.sort(ans);
        StringBuilder sb = new StringBuilder();
        sb.append(ansSet.size()).append("\n");
        for(int a : ans){
            sb.append(a).append(" ");
        }
        System.out.println(sb.toString());
    }
}
