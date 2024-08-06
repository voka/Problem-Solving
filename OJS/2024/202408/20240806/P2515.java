import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair>{
    int h;
    int c;
    public Pair(int h, int c){
        this.h = h;
        this.c = c;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.h < o.h) return -1;
        else if (this.h > o.h) return 1;
        else if(this.c > o.c) return -1;
        else if(this.c < o.c) return 1;
        return 0;
    }
}

public class P2515 {
    static int N,S;
    static Pair[] array;
    static int[] dp,preMaxIdx;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        array = new Pair[N+1];
        dp = new int[N+1];
        preMaxIdx = new int[N+1];
        array[0] = new Pair(0,0);
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            int h = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            array[i+1] = new Pair(h,c);
        }
        Arrays.sort(array);
        for(int i=1;i<=N;++i){
            int s = 0;
            int e = i-1;
            int m = 0;
            while(s<=e){
                m = (s+e)/2;
                int gap = array[i].h - array[m].h;
                // m번째 그림과의 높이 차이가 S 보다 크고 m+1번째 그림과의 높이 차이가 S 보다 작다면 m번째 그림이 확인해야 하는 이전 그림 인덱스가 된다.
                if(gap >= S && array[i].h - array[m+1].h < S) break; 
                if(gap >= S) s = m + 1;
                else e = m - 1;
            }
            preMaxIdx[i] = m;
            //System.out.printf("i : %d, pMI : %d, iH : %d, pMIH : %d\n", i,preMaxIdx[i],array[i].h,array[preMaxIdx[i]].h);
        }
        for(int i=1;i<=N;++i){
            dp[i] = Math.max(dp[i-1],dp[preMaxIdx[i]] + array[i].c);
        }

        System.out.println(dp[N]);
    }
}

/**
 * 1. 현재 그림의 값을 최종 정답에 포함 시킬 지 말지 결정하는 것은 현재 그림보다 높이가 S 이상 낮은 이전 그림의 값 + 현제 그림의 값 vs 이전 인덱스의 까지 최대 금액 이된다.
 * 2. 이를 위해 맨 처음 그림의 높이순으로 정렬을 한다.
 * 3. 각 인덱스 별로 높이 차가 S 이하인 가장 가까운 인덱스를 구한다. => preMaxIdx / 이때 그림의 개수가 많아 이분탐색을 이용해 빠르게 구한다.
 * 4. dp를 이용해 i번째 그림까지의 최대금액을 저장한다. (dp[i] : i번째 그림까지 팔았을 경우 최대 금액/ dp[i] = Math.max(dp[i-1], dp[preMaxIdx[i]]+i번째 그림의 가격))
 * 5. 최종 정답은 dp[N]이 된다.
 * 
 */