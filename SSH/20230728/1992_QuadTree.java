package Divide_and_Conquer;
import java.io.*;

public class _1992_QuadTree {
    static int n;
    static int[][] map;
    static String result = "";
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        map = new int[n][n];

        for(int i=0;i<n;i++){
            String str = br.readLine();
            for(int j=0;j<n;j++){
                map[i][j] = str.charAt(j) - '0';
            }
        }
        
        compression(0,0,n-1,n-1);
        System.out.println(result);
    }
    public static void compression(int start_x, int start_y, int end_x , int end_y){
        int middle_x = (start_x + end_x) / 2;
        int middle_y = (start_y + end_y) / 2;
        if(start_x == end_x) {
            result += map[start_y][start_x];
            return;
        }

        int value = map[start_y][start_x];
        for(int i=start_y;i<=end_y;i++){
            for(int j=start_x;j<=end_x;j++){
                if(value != map[i][j]){
                    result += "(";
                    compression(start_x, start_y, middle_x, middle_y);
                    compression(middle_x+1,start_y, end_x, middle_y);
                    compression(start_x,middle_y+1, middle_x, end_y);
                    compression(middle_x+1,middle_y+1,end_x,end_y);
                    result +=")";
                    return;
                }
            }
        }
        result+=value;
        return;

    }
}
