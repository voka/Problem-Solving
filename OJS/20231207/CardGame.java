import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CardGame {
    static int[][] dp;
    static int[] cards;
    static int N;

    public static int choice(int l, int r, int round){
        if(l > r) return 0;
        //System.out.printf("left: %d, right: %d, round: %d\n",l,r,round);
        if(dp[l][r] != 0 ) return dp[l][r];
        if(round%2 == 1){ // 근우의 턴인 경우 -> 내 점수 최대화
            return dp[l][r] = Math.max(choice(l+1,r,round+1) + cards[l], choice(l, r-1, round+1) + cards[r]);
        }
        else{// 명우의 턴일 경우 -> 내 점수 최소화
            return dp[l][r] = Math.min(choice(l+1, r, round+1), choice(l, r-1, round+1));
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
        int T = Integer.parseInt(br.readLine());
        for(int t = 0; t < T ; ++t){
            N = Integer.parseInt(br.readLine());
            dp = new int[N][N];
            cards = new int[N];
            String[] cardStrings = br.readLine().split(" ");
            int i=0;
            for(String card :cardStrings){
                cards[i++] = Integer.parseInt(card);
            }
            choice(0,N-1,1);
            System.out.println(dp[0][N-1]);
            //printDp();
        }

    }


}
