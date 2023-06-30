package DisJointSet;
import java.io.*;
import java.util.StringTokenizer;
public class _1717_SetExpress {
    static int[] root;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        root = new int[n+1];
        for(int i=1;i<=n;i++){
            root[i] = i;
        }
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if(p == 0) union(x,y);
            else if( p == 1){
                if(find(x) == find(y)) System.out.println("YES");
                else System.out.println("NO");
            }
        }
    }
    public static int find(int x){
        if(root[x] == x)
            return x;
        else{
            // find(root[x]) -> root[x] = find(root[x]) 로 변경해야 시간초과 x
            // 처음 find 할 떄 root[x]를 변경해줘서 불필요한 반복을 줄임
            return root[x] = find(root[x]);
        }
    }
    public static void union(int x, int y){
        x = find(x);
        y = find(y);
        root[y] = x;
    }
}
