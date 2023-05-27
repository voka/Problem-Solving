import java.util.*;
import java.io.*;

public class DSLR_DFS {
    public static void BFS(int start, int end) {
        int[] visited = new int[10001];
        String[] command = new String[10001];
        Arrays.fill(command, ""); // 안넣으면 NULL이 안사라짐 ㅋ
        String[] tmp = { "D", "S", "R", "L" };

        Queue<Integer> sQueue = new LinkedList<>();
        // Queue<mybook> eQueue = new LinkedList<>();
        sQueue.add(start);
        visited[start] = 1;
        // eQueue.add(new mybook(end, ""));
        while (!sQueue.isEmpty()) {
            // while(!sQueue.isEmpty() || !eQueue.isEmpty()){
            int f = sQueue.poll();
            // mybook e = eQueue.poll();
            if (f == end) {
                break;
            }
            int result = 0;
            for (int i = 0; i < 4; ++i) {
                switch (i) {
                    case 0:
                        result = DFunction(f);
                        break;
                    case 1:
                        result = SFunction(f);
                        break;
                    case 2:
                        result = RFunction(f);
                        break;
                    case 3:
                        result = LFunction(f);
                        break;
                }
                if (visited[result] == 0) {
                    visited[result] = 1;
                    sQueue.add(result);
                    command[result] = command[f] + tmp[i];
                }

            }
        }
        System.out.println(command[end]);
    }

    public static int DFunction(int target) {
        return (target * 2) % 10000;
    }

    public static int SFunction(int target) {
        if (target == 0)
            return 9999;
        else
            return target - 1;
    }

    public static int LFunction(int target) {
        return (target % 1000) * 10 + target / 1000;
    }

    public static int RFunction(int target) {
        return (target % 10) * 1000 + target / 10;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while (T > 0) {
            T--;
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            BFS(start, end);
        }
    }
}