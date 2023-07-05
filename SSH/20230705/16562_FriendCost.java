package DisJointSet;
import java.io.*;
import java.util.StringTokenizer;
public class _16562_FriendCost {
    static int n,m,k, mincost;
    static int[] root;
    static int[] cost;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        root = new int[n+1];
        cost = new int[n+1];
        for(int i=1;i<=n;i++){
            root[i] = i;
        }
        st = new StringTokenizer(br.readLine());
        for(int i=1;i<=n;i++){
            cost[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            union(v,w);
        }

        for(int i=1;i<=n;i++){
            if(root[i] == i){
                mincost += cost[i];
            }
        }
        if(mincost <= k){
            System.out.println(mincost);
        }
        else{
            System.out.println("Oh no");
        }

    }
    public static int find(int x){
        if(root[x] == x)
            return x;
        else
            return find(root[x]);
    }
    public static void union(int x, int y){
        x = find(x);
        y = find(y);

        if( x != y){
            if( cost[x] > cost[y]) root[x] = y;
            else root[y] = x;
        }
    }
}
