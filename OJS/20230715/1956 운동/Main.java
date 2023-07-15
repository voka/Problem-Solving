import java.util.*;
import java.io.*;

public class Main {
    static int V, E, i, j, k;
    static int[] parent;
    static Set<Integer> CycleRoot = new HashSet<>();

    static List<List<Point>> graph = new ArrayList<>();
    static int[][] dist;
    static final int INF = 10001 * 10000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        parent = new int[V];
        dist = new int[V][V];
        for (i = 0; i < V; ++i) {
            graph.add(new ArrayList<>());
            parent[i] = i;
            for (j = 0; j < V; ++j) {
                if (i == j)
                    dist[i][j] = 0;
                else
                    dist[i][j] = INF;
            }
        }

        int a, b, t;
        for (i = 0; i < E; ++i) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());
            dist[a - 1][b - 1] = t;
            // graph.get(a).add(new Point(t, b));
        }
        for (k = 0; k < V; ++k) {
            for (i = 0; i < V; ++i) {
                for (j = 0; j < V; ++j) {
                    if (i == j) {
                        continue;
                    }
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        // union_parent(i, k);
                        // union_parent(j, k);
                    }
                }
            }
        }
        int ans = INF;
        for (i = 0; i < V; ++i) {
            for (j = 0; j < V; ++j) {
                if (i == j) {
                    continue;
                }
                if (dist[i][j] != INF && dist[j][i] != INF) { // a -> b, b-> a 이면 사이클 발생한 것 !!!
                    ans = Math.min(ans, dist[i][j] + dist[j][i]);
                }
            }
        }
        if (ans == INF) {
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }

    static class Point {
        int cost;
        int idx;

        Point(int cost, int idx) {
            this.cost = cost;
            this.idx = idx;
        }
    }
}
