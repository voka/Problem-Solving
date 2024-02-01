import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] w,s;
    static int[] p;
    static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        long num=0;
        int pid=0;
        s = new long[n+1];
        w = new long[n+1];
        p = new int[n+1];
        p[1] = -1;
        for(int i=2;i<=n;++i){
            st = new StringTokenizer(br.readLine()," ");
            String flag = st.nextToken();
            num = Long.parseLong(st.nextToken());
            pid = Integer.parseInt(st.nextToken());
            p[i] = pid;
            if (flag.equals("S")) {
                s[i] = num;
            }else{
                w[i] = num; 
            }
        }
        for(int i=2;i<=n;++i){
            dfs(i);
        }
        // for(int i=1;i<=n;++i){
        //     System.out.printf("i : %d, w : %d, s : %d\n",i,w[i],s[i]);
        // }
        System.out.println(s[1]);
    }
    static void dfs(int x){
        int pid = p[x];
        if(pid != -1 && s[x] > 0){
            long leftSheep = s[x] - w[pid];  
            s[x] = 0;
            if(leftSheep <= 0){
                w[pid] = -leftSheep;
                return;
            }else{
                w[pid] = 0;
                if(p[pid]!= -1) p[x] = p[pid]; // 지나가지 않아도 되는 섬은 스킵 
                s[pid] += leftSheep;
                dfs(pid);
            }
        } 
    }
}
