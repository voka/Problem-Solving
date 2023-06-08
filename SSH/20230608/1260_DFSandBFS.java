package other;
import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Stack;
import java.util.Queue;
public class _1260_DFSandBFS {
    static int n, m, start;
    static int[][] edge = new int[1001][1001];
    static int[] check = new int[1001];
    static StringBuilder sb  =  new StringBuilder();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(st.nextToken());


        for(int i=1;i<=m;i++){
            st = new StringTokenizer(br.readLine());
            int vt1 = Integer.parseInt(st.nextToken());
            int vt2 = Integer.parseInt(st.nextToken());
            edge[vt1][vt2]=1;
            edge[vt2][vt1]=1;
        }

        dfs(start);
        check = new int[1001];
        sb.append("\n");
        bfs(start);
        System.out.println(sb);
    }

    public static void dfs(int start){
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(start);
        while(!stack.empty()){
            int ver = stack.pop();
            if( check[ver] == 0){
                check[ver]=1;
                sb.append(ver+" ");
            }
            for(int i=n;i>=1;i--){
                if((edge[ver][i] == 1) && (check[i] ==0)){
                    stack.push(i);
                }
            }
        }
    }

    public static void bfs(int start){
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(start);

        while(!queue.isEmpty()){
            int ver = queue.poll();
            if(check[ver] == 0){
                check[ver] =1;
                sb.append(ver+" ");

            }
            for(int i=1;i<=n;i++){
                if((edge[ver][i] == 1) && (check[i]==0)){
                    queue.add(i);
                }
            }
        }


    }
}
