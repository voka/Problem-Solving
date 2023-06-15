package other;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Stack;
public class _11724_ConnectComponentNum {

    static int n,m;
    static int[] check = new int[1001];
    static int[][] edge = new int[1001][1001];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int num = 0;
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            edge[v1][v2]=edge[v2][v1]=1;
        }
        for(int i=1;i<=n;i++){
            if(check[i] == 0){
                check[i] = 1;
                dfs(i);
                num++;
            }
        }
        System.out.println(num);
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
