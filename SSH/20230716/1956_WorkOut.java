package ShortestPath;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
public class _1956_WorkOut {
    static int v,e;
    static int[][] load;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int answer = Integer.MAX_VALUE;
        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        load = new int[v+1][v+1];

        for(int i=0;i<=v;i++)
            Arrays.fill(load[i],10000000);
        for(int i=0;i<=v;i++){
            load[i][i] = 0;
        }
        for(int i=0;i<e;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            load[a][b] = c;
        }

        floyd();

        for(int i=1;i<v;i++){
            for(int j=i+1;j<=v;j++){
                answer = Math.min(load[i][j]+load[j][i],answer);
            }
        }
        if(answer>=10000000)
            System.out.println(-1);
        else
            System.out.println(answer);

    }
    public static void floyd(){

        for(int k=1;k<=v;k++){
            for(int i=1;i<=v;i++){
                for(int j=1;j<=v;j++){
                    load[i][j] = Math.min(load[i][j],load[i][k]+load[k][j]);
                }
            }
        }
    }
}
