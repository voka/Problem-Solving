import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class P13023 {
    static int N,M;
    static boolean possible;
    static boolean[] visit;
    static List<List<Integer>> rel;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        rel = new ArrayList<>();
        visit = new boolean[N+1];
        for(int i=0;i<N;++i){
            rel.add(new ArrayList<>());
        }
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            rel.get(a).add(b);
            rel.get(b).add(a);
        }
        for(int i=0;i<N;++i){
            if(!visit[i]){
                visit[i] = true;
                check(i, 0);
                visit[i] = false;
            }
            if(possible) {
                System.out.println(1);
                return;
            }
        }
        
        System.out.println(0);
    }
    static void check(int s, int cnt){
        if(cnt == 4){
            possible = true;
            return;
        } 
        for(int i : rel.get(s)){
            if(!visit[i]){
                visit[i] = true;
                check(i,cnt+1);
                visit[i] = false;
            }
        }
    }
}
