package BruteForce;
import java.io.*;
import java.util.StringTokenizer;

public class _18111_minecraft {
    static int N, M, B;
    static int[][] map;
    static int min = 256;
    static int max = 0;
    static int resultCount =Integer.MAX_VALUE;
    static int resultHeight = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] >max){
                    max = map[i][j];
                }
                if(map[i][j] < min){
                    min = map[i][j];
                }
            }
        }
        for(int i=min;i<=max;i++){
            int result = find(i,B, 0);
            if(resultCount>result){
                resultCount = result;
                resultHeight= i;
            }
            else if (resultCount == result){
                resultHeight = Math.max(resultHeight,i);
            }
        }
        System.out.println(resultCount+" "+resultHeight);
    }
    public static int find(int height, int block, int count){
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(map[i][j] > height){
                    count += (map[i][j] - height)*2;
                    block += (map[i][j] - height);
                }
                else if (map[i][j] < height){

                    count += height-map[i][j];
                    block -= (height-map[i][j]);
                }
            }
        }
        if(block < 0) return Integer.MAX_VALUE;
        return count;
    }
}
