package Graph;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Stack;
public class _10451_PermutationCycle {
    static int n;
    static int [] check;
    static int [][] edge;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t;

        t = Integer.parseInt(br.readLine());
        for(int i=0;i<t;i++){
            int count=0;
            n = Integer.parseInt(br.readLine());
            check = new int[n+1];
            edge = new int[n+1][n+1];
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=n;j++){
                int node = Integer.parseInt(st.nextToken());
                edge[j][node] = 1;

            }
            for(int k = 1;k<=n;k++){
                if(check[k] == 0){
                    dfs(k);
                    count++;
                }
            }
            System.out.println(count);
        }
    }

    public static void dfs(int start){
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(start);
        while(!stack.empty()){
            int var = stack.pop();
            if(check[var] == 0){
                check[var] = 1;
            }
            for(int i=n;i>=1;i--){
                if((edge[var][i] == 1) && (check[i] == 0)){
                    stack.push(i);
                }
            }
        }
    }
}
