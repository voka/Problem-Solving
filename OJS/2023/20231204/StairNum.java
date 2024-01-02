import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StairNum{
    static int mod = 1000000000;
    static int binaryNum = (1<<10)-1;
    // dp[i][j][k] -> i : 자리수, j : 맨 마지막 숫자, k -> 0001000101 (0,2,6을 사용했다는 뜻)
    // f(k): 0~9까지 사용한 수의 종류 dp[i][j][k] : i 길이를 가지고, j가 맨 마지막 숫자인 0 ~ 9 까지의 숫자를 f(k) 종류 사용한 숫자의 수 
    static int [][][] dp = new int[101][10][binaryNum+1];
    public static void main(String[] args) throws IOException{
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strN = br.readLine();
        int N = Integer.parseInt(strN);
        //System.out.println(Integer.toBinaryString(binaryNum));
        for(int j=1;j<10;++j){
            dp[1][j][1<<j] = 1; // 1 -> 1이라는 숫자 1종류를 사용한 1자리 숫자 ! 0으로만 이루어진 수는 존재 X
        }
        for(int i=2;i<=N;++i){
            for(int j=0;j<10;++j){
                for(int k=1;k<binaryNum+1;++k){ // k | (1<<j) k | j 번째 자리를 사용한 것  -> f(k) 종류 + 1 or 0(k에 이미 j가 있는 경우)
                    if(j < 9){
                        dp[i][j][k | (1<<j)] = (dp[i][j][k | (1<<j)] + dp[i-1][j+1][k]) % mod;
                    }
                    if (j > 0){
                        dp[i][j][k | (1<<j)] = (dp[i][j][k | (1<<j)] + dp[i-1][j-1][k]) % mod;
                    }
                }
            }
        }
        int answer = 0;
        for(int j=0;j<10;++j){
            answer = (answer + dp[N][j][binaryNum]) % mod;
        }
        // for(int k=1;k<=binaryNum;++k){
        //     System.out.printf("k : %d, bin : %s, num : %d\n", k, Integer.toBinaryString(k),dp[2][0][k]);
        // }
        System.out.println(answer);

    }
}
/**
 * 1 : 0000000010
 * 2 : 0000000100
 * 3 : 0000001000
 * 4 : 0000010000
 * 5 : 0000100000
 * 6 : 0001000000
 * 7 : 0010000000
 * 8 : 0100000000
 * 9 : 1000000000
 * 10 : 0000000011 = 0000000010 | 0000000001 (dp[2][0][0000000011] = dp[2][0][0000000011] + dp[1][1][0000000001] = 1)
 * 
 */