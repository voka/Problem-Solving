package DisJointSet;
import java.io.*;
import java.util.StringTokenizer;
public class _20040_CycleGame {
    static int n,m;
    static int[] parent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        parent = new int[n];
        for(int i=0;i<n;i++){
            parent[i] = i;
        }
        int number=0;
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if(union(a,b)){
                if(number == 0){
                    number= i+1;
                }

            }
        }
        System.out.println(number);
    }
    public static int find(int x){
        if(parent[x] == x)
            return x;
        else{
            return parent[x] = find(parent[x]);
        }
    }
    public static boolean union(int x, int y){
        x = find(x);
        y = find(y);


        if(x != y){
            if(x > y) parent[y] = x;
            else if (x < y) parent[x] = y;
            return false;
        }
        else{
            return true;
        }

    }
}
