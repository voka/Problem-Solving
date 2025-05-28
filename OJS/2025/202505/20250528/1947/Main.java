import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long[] dp = new long[Math.max(3,N+1)];
        dp[2] = 1L;
        for(int i=3;i<=N;++i){
            dp[i] = ((i-1) * (dp[i-1] + dp[i-2])) % 1000000000;
        }
        System.out.println(dp[N]);
    }    
}
/*
 * 
 1 2 3 4 

 1 : (2,3,4) -> x : 3
 2 : (1,3,4) - x -> y : 2
 3 : (1,2,4) - x - y  -> z : 1
 4 : (1,2,3) - x - y - z -> k : 1

 1 2 3 4
 
 2 1 4 3
 2 3 4 1
 2 4 1 3
 
 3 1 4 2
 3 4 1 2
 3 4 2 1

 4 1 2 3
 4 3 1 2 
 4 3 2 1


 1 2 3

 2 1 3
 3 1 2 
ㅁㄴㅇㅁ


 4 ! -> 4 x 3 x 2 x 1 -> 24

 * 
 */