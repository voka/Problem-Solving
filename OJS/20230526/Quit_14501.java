import java.util.*;
import java.io.*;

public class Quit_14501 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());
        int[] T = new int[N];
        int[] P = new int[N];
        int[] DP = new int[N + 1];
        for (int i = 0; i < N; ++i) {
            StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
            T[i] = Integer.parseInt(st.nextToken());
            P[i] = Integer.parseInt(st.nextToken());
        }
        int cur_max = -1;
        for (int i = 0; i < N; ++i) {
            cur_max = Math.max(cur_max, DP[i]);
            if (i + T[i] <= N) {
                DP[i + T[i]] = Math.max(DP[i + T[i]], cur_max + P[i]);
            }
        }
        System.out.println(Math.max(cur_max, DP[N]));
    }
}
