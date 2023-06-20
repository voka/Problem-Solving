package other;
import java.io.*;
import java.sql.SQLOutput;
import java.util.StringTokenizer;
import java.util.Stack;
import java.util.ArrayList;
public class _1707_BinaryGraph {

    static ArrayList<Integer> edge[];
    static int[] check;
    static int[] group;
    static String ans = "YES";
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        boolean bGraph = true;
        int k;
        k = Integer.parseInt(br.readLine());

        for(int i=0;i<k;i++){
            ans = "YES";
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            edge = new ArrayList[v+1];
            for(int j = 1;j <=v;j++){
                edge[j] = new ArrayList<Integer>();
            }
            check = new int[v+1];
            group = new int[v+1];
            for(int j=0;j<e;j++){
                st = new StringTokenizer(br.readLine());
                int v1 = Integer.parseInt(st.nextToken());
                int v2 = Integer.parseInt(st.nextToken());
                edge[v1].add(v2);
                edge[v2].add(v1);
            }
            for(int z=1;z<=v;z++){
                if(check[z] == 0){
                    if(!dfs(z)) {
                        break;
                    }

                }
            }
            System.out.println(ans);
        }

    }
    public static boolean dfs(int start){
        Stack<Integer> stack = new Stack<Integer>();
        int groupNum = 1;
        stack.push(start);
        group[start] = groupNum;
        while(!stack.empty()){
            int var = stack.pop();
            if(check[var] == 0){
                check[var] = 1;
            }
            groupNum = -group[var];
            for(int i : edge[var]){
                if( (check[i] == 0)){
                    stack.push(i);
                    if(group[i] == 0)
                        group[i] = groupNum;
                    else if( group[i] != groupNum) {
                        ans = "NO";
                        return false;
                    }
                }
                else if((check[i] == 1)) {
                    if (group[var] == group[i]) {
                        ans = "NO";
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
