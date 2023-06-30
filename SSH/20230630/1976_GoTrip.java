package DisJointSet;
import java.io.*;
import java.util.StringTokenizer;
public class _1976_GoTrip {
    static int[] root;
    static int n,m;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        root = new int[n+1];
        for(int i=1;i<=n;i++)
            root[i] = i;
        for(int i=1;i<=n;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=n;j++){
                int tmp = Integer.parseInt(st.nextToken());
                if(tmp == 1){
                    union(i,j);
                }
            }
        }
        checkPath(br.readLine());

    }
    public static int find(int x){
        if(root[x] == x)
            return x;
        else{
            return root[x] = find(root[x]);
        }
    }
    public static void union(int x, int y){
        x = find(x);
        y = find(y);
        if(x != y)
            root[y] = x;
    }
    public static void checkPath(String str){
        StringTokenizer st = new StringTokenizer(str);
        int set = find(Integer.parseInt(st.nextToken()));
        for(int i=2;i<=m;i++){
            int tmp = Integer.parseInt(st.nextToken());
            if(set != find(tmp)){
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
        return;
    }
}
