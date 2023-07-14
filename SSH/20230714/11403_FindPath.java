package ShortestPath;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
public class _11403_FindPath {
    static int n;
    static int[][] matrix;
    static int[][] dist;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        matrix = new int[n+1][n+1];
        dist = new int[n+1][n+1];
        for(int i=0;i<=n;i++){
            Arrays.fill(dist[i],1000000);
        }
        for(int i=1;i<=n;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=n;j++){
                matrix[i][j] = Integer.parseInt(st.nextToken());
                if(matrix[i][j] == 1)
                    dist[i][j] = 1;
            }
        }
        floyd();

        
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if(dist[i][j] < 1000000)
                    System.out.print(1 + " ");
                else
                    System.out.print(0 + " ");
            }
            System.out.println();
        }
    }
    public static void floyd(){
        int i,j,k;
        for(k = 1; k <= n; k++){
            for(i = 1; i <= n; i++){
                for(j = 1; j <= n; j++){
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}
