import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Matrix {   
    static int N; 
    static int[] r, c;
    static long[][] dp; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        r = new int[N];
        c = new int[N];
        dp = new long[N][N];
        for(int i=0;i<N;++i){
            String[] temp = br.readLine().split(" ");
            r[i] = Integer.parseInt(temp[0]);
            c[i] = Integer.parseInt(temp[1]);
            Arrays.fill(dp[i], Long.MAX_VALUE);
            dp[i][i] = 0;
        }
        for(int num=0;num<N-1;++num){
            for(int i=0;i<N-1-num;++i){
                int j =  i + num + 1;
                for(int k=i;k<j;++k){
                    //System.out.printf("%d, %d, %d\n",i,j,k);
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k+1][j] + r[i]*c[k]*c[j]);
                }
            }
        }
        System.out.println(dp[0][N-1]);
        // for(int i=0;i<N;++i){
        //     System.out.println();
        //     for(int j=0;j<N;++j){
        //         System.out.printf("%d ",dp[i][j]);
        //     }
        // }


    }
    
}
