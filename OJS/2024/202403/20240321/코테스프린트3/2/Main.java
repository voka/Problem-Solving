import java.io.*;
import java.util.*;
public class Main {
    static int n,m;
    static boolean[][] check;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        check = new boolean[n+1][n+1];
        int ans = 0;
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            check[x][y] = true;
            check[y][x] = true;
        }
        for(int i=1;i<=n-2;++i){
            for(int j=i+1;j<=n-1;++j){
                for(int k=j+1;k<=n;++k){
                    if(!check[i][j] && !check[i][k] && !check[j][k]) ans++;
                }
            }
        }
        System.out.println(ans);
    }
}