import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
class Pair{
    int idx; 
    int val;
    public Pair(int idx, int val){
        this.idx = idx;
        this.val = val;
    }
}

public class Main {
    static int N;
    static int[] list, leftToRight, rightToLeft; 
    static int[][] see;
    static Stack<Pair> leftBig = new Stack<>(), rightBig = new Stack<>();
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in));
        N = Integer.parseInt(br.readLine());
        String[] temp = br.readLine().split(" ");
        list = new int[N];
        leftToRight = new int[N];
        rightToLeft = new int[N];
        see = new int[N][2];// 0 -> 건물 인덱스 번호, 1 -> 건물과의 거리

        for (int[] row : see)
            Arrays.fill(row, 100001);

        for(int i=0;i<N;++i){
            list[i] = Integer.parseInt(temp[i]);
        }
        // 순방향 탐색
        leftBig.add(new Pair(0, list[0]));
        for(int i=1;i<N;++i){
            while(!leftBig.empty() && leftBig.peek().val <= list[i]){// 자기 보다 낮은 탑은 어짜피 다음 탑에서도 보이지 않으므로 제거
                leftBig.pop();
            }
            leftToRight[i] = leftBig.size();
            if(leftBig.size() != 0) {// 처음에는 거리, 인덱스 그냥 입력
                see[i][0] = leftBig.peek().idx;
                see[i][1] = Math.abs(leftBig.peek().idx - i);
            }
            leftBig.add(new Pair(i, list[i]));
        }
        // 역방향 탐색
        rightBig.add(new Pair(N-1, list[N-1]));
        for(int i=N-2;i>=0;--i){;
            while(!rightBig.empty() && rightBig.peek().val <= list[i]){// 자기 보다 낮은 탑은 어짜피 다음 탑에서도 보이지 않으므로 제거
                rightBig.pop();
            }
            rightToLeft[i] = rightBig.size();
            if(rightBig.size() != 0) { // 거리, 인덱스 검사
                int dist = Math.abs(rightBig.peek().idx - i);
                if(dist < see[i][1]){
                    see[i][0] = rightBig.peek().idx;
                    see[i][1] = dist;
                }else if((dist == see[i][1]) && rightBig.peek().idx < see[i][0]){
                    see[i][0] = rightBig.peek().idx;
                }
            }
            rightBig.add(new Pair(i, list[i]));
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<N;++i){
            //System.out.printf("idx : %d, left -> right : %d, right -> left : %d, see : %d\n",i, leftToRight[i],rightToLeft[i],see[i]);
            if(leftToRight[i] + rightToLeft[i] == 0)
                sb.append(0);
            else
                sb.append(leftToRight[i] + rightToLeft[i]).append(' ').append(see[i][0] + 1);
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
/**
 * dp     0         1 2         3           4           5         6             7
 *        3         7 1         6           3           5         1             7            
 * s      0         0 dp[1] + 1 dp[1] + 1   dp[3] + 1   dp[3] + 1 dp[5] + 1     0 
 * re     dp[1] + 1 0 dp[3] + 1 dp[7] + 1   dp[5] + 1   dp[7] + 1 dp[7] + 1     0
 *        0         0 1         1           2           2         3             0
 *        1         0 2         1           2           1         1             0
 * 
 * 
 */