import java.io.*;
import java.util.*;

class Main {
    static StringBuilder sb = new StringBuilder();
    static int MaxValue = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());
        PriorityQueue<PI> pq = new PriorityQueue<PI>((o1, o2) -> Integer.compare(o1.dist, o2.dist));

        List<List<PI>> graph = new ArrayList<>();

        int[] dist = new int[N + 1];
        for (int i = 0; i <= N; ++i) {
            dist[i] = MaxValue;
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; ++i) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph.get(a).add(new PI(b, 1));
        }

        pq.offer(new PI(X, 0));
        dist[X] = 0;
        while (!pq.isEmpty()) {
            PI cur = pq.poll();
            if (dist[cur.node] < cur.dist) {
                continue;
            }
            for (PI next : graph.get(cur.node)) {
                int tcost = cur.dist + next.dist;
                if (tcost < dist[next.node]) {
                    dist[next.node] = tcost;
                    pq.offer(new PI(next.node, tcost));
                }
            }
        }
        int cnt = 0;
        for (int i = 1; i <= N; ++i) {
            if (dist[i] == K) {
                cnt += 1;
                sb.append(i + "\n");
            }
        }
        if (cnt == 0) {
            System.out.println(-1);
        } else {
            System.out.println(sb.toString());
        }

    }

    public static class PI {
        int dist;
        int node;

        public PI(int node, int dist) {
            this.dist = dist;
            this.node = node;
        }

    }
}