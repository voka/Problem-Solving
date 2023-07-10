import java.util.*;
import java.io.*;

class Main {

    static int[][] maps; // . -> 1, * -> 0, C -> 1
    static int maxValue = Integer.MAX_VALUE;
    static Pi[] cPoint = new Pi[2];

    static int[] dx = { 1, -1, 0, 0 };
    static int[] dy = { 0, 0, 1, -1 };

    static int[][][] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int W = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());
        maps = new int[H][W];
        dist = new int[H][W][4];
        int c_idx = 0;
        for (int i = 0; i < H; ++i) {
            char[] temp = br.readLine().toCharArray();
            for (int j = 0; j < W; ++j) {
                char a = temp[j];
                if (a == '.') {
                    maps[i][j] = 1;
                } else if (a == '*') {
                    maps[i][j] = 0;
                } else {
                    maps[i][j] = 1;
                    cPoint[c_idx] = new Pi(j, i);
                    c_idx++;
                }
                Arrays.fill(dist[i][j], maxValue);
            }
        }

        PriorityQueue<CostWithPi> pq = new PriorityQueue<>();
        for (int i = 0; i < 4; ++i) {
            dist[cPoint[0].y][cPoint[0].x][i] = 0;
            pq.add(new CostWithPi(0, cPoint[0], i));
        }

        while (!pq.isEmpty()) {
            CostWithPi cur = pq.poll();
            if (dist[cur.point.y][cur.point.x][cur.dir] < cur.cost) {
                continue;
            }
            for (int i = 0; i < 4; ++i) {
                int nx = dx[i] + cur.point.x;
                int ny = dy[i] + cur.point.y;
                if (nx < 0 || nx >= W || ny < 0 || ny >= H) {
                    continue;
                }
                if (maps[ny][nx] == 1) {
                    if ((cur.dir != i) && (cur.cost + 1 <= dist[ny][nx][i])) {
                        dist[ny][nx][i] = cur.cost + 1;
                        pq.add(new CostWithPi(cur.cost + 1, new Pi(nx, ny), i));
                    } else if (cur.cost < dist[ny][nx][i]) {
                        dist[ny][nx][i] = cur.cost;
                        pq.add(new CostWithPi(cur.cost, new Pi(nx, ny), i));
                    }
                }
            }

        }
        int answer = maxValue;

        for (int i = 0; i < 4; ++i) {
            answer = Math.min(answer, dist[cPoint[1].y][cPoint[1].x][i]);
        }
        System.out.println(answer);

    }

    static class Pi {
        int x;
        int y;

        public Pi(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class CostWithPi implements Comparable<CostWithPi> {
        int cost;
        Pi point;
        int dir;

        public CostWithPi(int cost, Pi point, int dir) {
            this.cost = cost;
            this.point = point;
            this.dir = dir;
        }

        @Override
        public int compareTo(CostWithPi target) {
            return this.cost >= target.cost ? 1 : -1;
        }

    }
}