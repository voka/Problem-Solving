import java.util.*;
import java.io.*;

public class Main {
    static int N, M, i;
    static ArrayList<ArrayList<Custom>> graph = new ArrayList<ArrayList<Custom>>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        long[] Fdist = new long[N];
        long[][] Wdist = new long[N][2];
        for (i = 0; i < N; ++i) {
            graph.add(new ArrayList<Custom>());
            Fdist[i] = Long.MAX_VALUE;
            Wdist[i][0] = Long.MAX_VALUE;
            Wdist[i][1] = Long.MAX_VALUE;
        }
        for (i = 0; i < M; ++i) {
            st = new StringTokenizer(br.readLine());
            int a, b, d;
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            graph.get(a - 1).add(new Custom(d * 2, b - 1));
            graph.get(b - 1).add(new Custom(d * 2, a - 1));
        }
        // System.out.println();
        // for (i = 0; i < N; ++i) {
        // System.out.println(i + 1);
        // for (Custom c : graph.get(i)) {
        // System.out.print(c.idx);
        // }
        // System.out.println();
        // }

        Fdist[0] = 0;
        Wdist[0][1] = 0;

        PriorityQueue<Custom> pq = new PriorityQueue<>();
        pq.add(new Custom(0, 0));

        while (!pq.isEmpty()) { // Fox
            Custom cur = pq.poll();
            if (Fdist[cur.idx] < cur.cost) {
                continue;
            }
            for (Custom next : graph.get(cur.idx)) {
                long TotalCost = cur.cost + next.cost;
                if (TotalCost < Fdist[next.idx]) {
                    Fdist[next.idx] = TotalCost;
                    pq.add(new Custom(TotalCost, next.idx));
                }
            }
        }

        pq = new PriorityQueue<>();
        pq.add(new Custom(0, 0, 1));
        while (!pq.isEmpty()) { // Wolf
            Custom cur = pq.poll();
            if (cur.cost > Wdist[cur.idx][cur.rest]) {
                continue;
            }
            int Nrest = 1 - cur.rest;
            for (Custom next : graph.get(cur.idx)) {
                long TotalCost = Wdist[cur.idx][cur.rest] + ((cur.rest == 1) ? next.cost / 2 : next.cost * 2);
                if (TotalCost < Wdist[next.idx][Nrest]) {
                    Wdist[next.idx][Nrest] = TotalCost;
                    pq.add(new Custom(TotalCost, next.idx, Nrest));
                }
            }
        }
        int ans = 0;
        // System.out.println();
        for (i = 1; i < N; ++i) {
            // System.out.println(i + 1);
            // System.out.println(Fdist[i]);
            // System.out.println(Wdist[i][0]);
            // System.out.println(Wdist[i][1]);
            // System.out.println();
            if (Fdist[i] < Math.min(Wdist[i][0], Wdist[i][1])) {
                ans++;
            }
        }
        System.out.println(ans);
    }

    static class Custom implements Comparable<Custom> {
        int idx;
        long cost;
        int rest;

        Custom(long cost, int idx) {
            this.cost = cost;
            this.idx = idx;
        }

        Custom(long cost, int idx, int rest) {
            this.cost = cost;
            this.idx = idx;
            this.rest = rest;
        }

        @Override
        public int compareTo(Custom o) {
            if (this.cost < o.cost) {
                return -1;
            }
            return 1;
        }

    }

}
