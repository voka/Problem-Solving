package ShortestPath;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
public class _14938_BattleGround {
    static int n, m, r;
    static int[] item;
    static int[][] edge;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        item = new int[n+1];
        edge = new int[n+1][n+1];

        for(int i=1;i<=n;i++){
            Arrays.fill(edge[i],15*100);
        }
        for(int i=1;i<=n;i++){
            edge[i][i] = 0;
        }


        st = new StringTokenizer(br.readLine());
        for(int i=1;i<=n;i++)
            item[i] = Integer.parseInt(st.nextToken());

        for(int i=0;i<r;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            edge[a][b]=edge[b][a]=c;
        }

        floyd();
        int answer = 0;
        for(int i=1;i<=n;i++){
            int tmp=0;
            for(int j=1;j<=n;j++){
                if(edge[i][j] <=m){
                    tmp += item[j];
                }
            }
            answer= Math.max(answer,tmp);
        }
        System.out.println(answer);
    }
    public static void floyd(){
        for(int k=1;k<=n;k++){
            for(int i=1;i<=n;i++){
                for(int j=1;j<=n;j++){
                    edge[i][j] = Math.min(edge[i][j],edge[i][k]+edge[k][j]);
                }
            }
        }
    }
}
