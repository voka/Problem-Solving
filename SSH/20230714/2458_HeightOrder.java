package ShortestPath;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
public class _2458_HeightOrder {
    static int n,m;
    static int[][] dist;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        dist = new int[n+1][n+1];
        int[] count = new int[n+1];
        int result=0;
        for(int i=0;i<=n;i++)
            Arrays.fill(dist[i], 100000);

        for(int i=0;i<=n;i++){
            for(int j=0;j<=n;j++){
                if(i==j)
                    dist[i][j] = 0;
            }
        }
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            dist[a][b]=1;
        }
        floyd();

        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if(dist[i][j]>0 && dist[i][j] <100000){
                    count[j]++;
                    count[i]++;
                }
            }
        }
        for(int i=1;i<=n;i++){
            if(count[i] == n-1)
                result++;
        }
        System.out.println(result);
    }
    public static void floyd() {
        int i,j,k;

        for(k = 1; k <= n; k++){
            for(i = 1; i <= n; i++){
                for(j = 1; j <= n; j++){
                    dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
                }
            }
        }
    }
}
