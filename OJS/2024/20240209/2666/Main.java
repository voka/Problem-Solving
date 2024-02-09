import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    
    static int n, m, ans = Integer.MAX_VALUE;
    static int[] use;
    static void dfs(int m1, int m2, int cnt, int cost){
        //System.out.printf("%d, %d, %d, %d\n", m1,m2,cnt,cost);
        if(cnt == m){
            ans = Math.min(cost,ans);
            return;
        }
        // m1 -> use[cnt];
        int cost1 = Math.abs(m1 - use[cnt]);
        dfs(use[cnt],m2,cnt+1,cost + cost1);
        // m2 -> use[cnt];
        int cost2 = Math.abs(m2 - use[cnt]);
        dfs(m1, use[cnt],cnt+1,cost + cost2);

    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(br.readLine());
        use = new int[m];
        for(int i=0;i<m;++i){
            use[i] = Integer.parseInt(br.readLine());
        }
        dfs(a,b,0,0);
        System.out.println(ans);
    }    
}
