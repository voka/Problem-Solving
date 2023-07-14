package ShortestPath;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
public class _11404_Floyd {
    static int n,m;
    static int[][] dist;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        dist = new int[n+1][n+1];
        for(int i=0;i<=n;i++)
            Arrays.fill(dist[i],1000000000);
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            if(dist[a][b] >= c)
                dist[a][b] = c;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if( i == j )
                    dist[i][j] = 0;
            }
        }

        floyd();

        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if(dist[i][j] != 1000000000 )
                    System.out.print(dist[i][j]+" ");
                else
                    System.out.print(0+" ");
            }
            System.out.println();
        }
    }
    public static void floyd(){
        int i,j,k;

        for(k = 1;k<=n;k++){
            for(i = 1;i<=n;i++){
                for(j = 1;j<=n;j++){
                    dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
                }
            }
        }

    }
}
