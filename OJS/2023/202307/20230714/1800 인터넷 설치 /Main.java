import java.util.*;
import java.io.*;

class Main {
    static int N, P, K;
    static ArrayList<ArrayList<Point>> graph = new ArrayList<ArrayList<Point>>();
    static int[] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        dist = new int[N];

        for (int i = 0; i < N; ++i) {
            graph.add(new ArrayList<Point>());
        }
        for (int i = 0; i < P; ++i) {
            st = new StringTokenizer(br.readLine());
            int a, b, t;
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());
            graph.get(a - 1).add(new Point(t, b - 1));
            graph.get(b - 1).add(new Point(t, a - 1));
        }
        int start = 0, end = 1000000, mid = 0;
        int ans = -1;
        while (start <= end) {
            mid = (start + end) / 2;
            if (solve(mid)) { // mid 가 의미하는 것은 원장이 지불해야할 금액임
                ans = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        System.out.println(ans);

    }

    static boolean solve(int x) {
        for (int i = 0; i < N; ++i) {
            dist[i] = 1000001;
        }
        dist[0] = 0;
        PriorityQueue<Point> pq = new PriorityQueue<>();
        pq.add(new Point(0, 0)); // 요기서 cost가 의미하는 건 기준값 x 보다 큰 비용을 가진 선의 개수임.

        while (!pq.isEmpty()) {
            Point cur = pq.poll();
            if (dist[cur.idx] < cur.cost)
                continue;
            for (Point p : graph.get(cur.idx)) {
                int w = 0;
                if (p.cost > x) {
                    w = 1;
                }
                int TotalCoat = w + cur.cost;
                if (dist[p.idx] > TotalCoat) {
                    dist[p.idx] = TotalCoat;
                    pq.add(new Point(TotalCoat, p.idx));
                }
            }
        }
        return dist[N - 1] <= K; // N-1 번까지 연결하는 데에 사용한 x 보다 큰 선의 개수가 K개 보다 적은지 확인 하는 과정
    }

    static class Point implements Comparable<Point> {
        int cost;
        int idx;

        Point(int cost, int idx) {
            this.cost = cost;
            this.idx = idx;

        }

        @Override
        public int compareTo(Point target) {
            if (this.cost < target.cost) {
                return 1;
            } else {
                return -1;
            }

        }
    }
}