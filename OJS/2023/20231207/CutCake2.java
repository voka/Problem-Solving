import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CutCake2 {
    static int[][] dp;
    static int[] cake;
    static int N;

    public static int left(int idx){
        return (N + idx - 1) % N;
    }
    public static int right(int idx){
        return (idx + 1) % N; 
    }

    public static int JOI(int l,int r){
        int ll = cake[left(l)] + choice(left(l), r, false);
        int rr = cake[right(l)] + choice(l, right(r), false);
        return Math.max(ll,rr);
    }
    public static int IOI(int l, int r){
        if(cake[left(l)] > cake[right(r)]){
            return choice(left(l), r, true);
        }
        else{
            return choice(l,right(r),true);
        }
    }
    public static int choice( int l, int r, boolean flag){
        if(left(l) == r) return 0; // 끝까지 온 경우 
        if(dp[l][r] != 0 ) return dp[l][r];
        if(flag){ 
            return dp[l][r] = JOI(l,r);
        }
        else{
            return dp[l][r] = IOI(l,r);
        }
    }
    static void  printDp(){
        for(int l = 0;l<N;++l){
            for(int r = l+1;r<N;++r){
                System.out.printf("%d ~ %d : %d\n",l,r,dp[l][r]);
            }
        }
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        cake = new int[N];
        dp = new int[2][2*N];
        for(int i=0;i<N;++i){
            cake[i] = Integer.parseInt(br.readLine());
            dp[0][i] = cake[i];
        }
        for(int i=1;i<N;++i){
            if(i%2 == 0){
                for(int j=0;j<N;++j){
                    dp[1][left(j)] = Math.max(dp[1][left(j)], dp[0][j] + cake[left(j)]);
                    dp[1][j] = Math.max(dp[1][j],dp[0][j] + cake[right(i+j)]);
                }
            }
            else{
                for(int j=0;j<N;++j){
                    if(cake[left(j)] > cake[right(i+j)]){
                        dp[0][left(j)] = Math.max(dp[0][left(j)],dp[1][j]);
                    }
                    else dp[0][j] = Math.max(dp[0][j],dp[1][j]);
                }
            }
        }
        int answer = 0;
        for(int i=0;i<N;++i){
            answer = Math.max(answer, dp[0][i]);
        }
        System.out.println(answer);
    }
}
