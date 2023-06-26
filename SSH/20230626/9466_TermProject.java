package Graph;
import java.io.*;
import java.util.StringTokenizer;

public class _9466_TermProject {
    static int[] student;
    static int[] want;
    static int[] finished;
    static int n, count=0;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        for(int i=0;i<t;i++){
            n = Integer.parseInt(br.readLine());
            student = new int[n+1];
            want = new int[n+1];
            finished = new int[n+1];
            count=0;
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=n;j++){
                int tmp = Integer.parseInt(st.nextToken());
                want[j] = tmp;
            }

            for(int k=1;k<=n;k++){
                dfs(k);
            }
            System.out.println(n - count);
        }
    }
    public static void dfs(int start) {
        if(student[start] == 1)
            return;

        student[start] = 1;
        int next = want[start];
        if(student[next] != 1)
            dfs(next);
        else{
            if(finished[next] != 1){
                count++;
                for(int i=next; i!=start;i =want[i])
                    count++;
            }
        }
        finished[start] = 1;
    }
}
