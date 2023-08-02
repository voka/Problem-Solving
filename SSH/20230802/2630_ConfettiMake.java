package Divide_and_Conquer;
import java.io.*;
import java.util.StringTokenizer;
public class _2630_ConfettiMake {
    static int n;
    static int blue, white;
    static int[][] confetti;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        confetti = new int[n+1][n+1];

        for(int i=1;i<=n;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=n;j++){
                confetti[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        dac(1,n,1,n);
        System.out.println(white);
        System.out.println(blue);
    }
    public static void dac(int start_x, int end_x, int start_y, int end_y){
        int middle_x = (start_x + end_x)/2;
        int middle_y = (start_y + end_y)/2;

        int value = confetti[start_y][start_x];
        for(int i=start_y;i<=end_y;i++){
            for(int j=start_x;j<=end_x;j++){
                if(value != confetti[i][j]){
                    dac(start_x,middle_x,start_y,middle_y);
                    dac(middle_x+1,end_x,start_y,middle_y);
                    dac(start_x,middle_x,middle_y+1,end_y);
                    dac(middle_x+1,end_x,middle_y+1,end_y);
                    return;

                }
            }
        }
        if (value == 0) white++;
        else if (value == 1) blue++;
        return;
    }
}
