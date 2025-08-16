
import java.io.*;
import java.util.*;
//https://www.acmicpc.net/problem/11404
public class 플로이드워셜 {
    static int N,M ;
    static int[][] map;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }

        }
        for(int k=0;k<N;++k){
            for(int i=0;i<N;++i){
                for(int j=0;j<N;++j){
                    if(map[i][j] > map[i][k] + map[k][j]){
                        map[i][j] = map[i][k] + map[k][j];
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());
            if(map[a][b] <= c){
                sb.append("Enjoy other party\n");
            }else{
                sb.append("Stay here\n");
            }
        }
        System.out.println(sb.toString());

    }
}