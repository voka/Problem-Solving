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
        int N = Integer.parseInt(st.nextToken());
        maps = new int[N][N];
        dist = new int[N][N][4];
        int c_idx = 0;
        for (int i = 0; i < N; ++i) {
            char[] temp = br.readLine().toCharArray();
            for (int j = 0; j < N; ++j) {
                char a = temp[j];
                if (a == '.') {
                    maps[i][j] = 1;
                } else if (a == '*') {
                    maps[i][j] = 0;
                } else if (a == '#') {
                    maps[i][j] = 1;
                    cPoint[c_idx] = new Pi(j, i);
                    c_idx++;
                } else {
                    maps[i][j] = 2;
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
            if (maps[cur.point.y][cur.point.x] == 2) { // 거울이 설치 가능한 위치라면 설치해서 반사하거나 그대로 진행하거나 반사
                for (int i = 0; i < 4; ++i) {
                    int nx = dx[i] + cur.point.x;
                    int ny = dy[i] + cur.point.y;
                    if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
                        continue;
                    }
                    if (maps[ny][nx] == 0) {
                        continue;
                    }
                    if ((cur.dir != i) && (cur.cost + 1 <= dist[ny][nx][i])) {
                        dist[ny][nx][i] = cur.cost + 1;
                        pq.add(new CostWithPi(cur.cost + 1, new Pi(nx, ny), i));
                    } else if (cur.cost < dist[ny][nx][i]) {
                        dist[ny][nx][i] = cur.cost;
                        pq.add(new CostWithPi(cur.cost, new Pi(nx, ny), i));
                    }
                }
            } else { // 무조건 통과 -> 오던 방향 그대로
                int nx = dx[cur.dir] + cur.point.x;
                int ny = dy[cur.dir] + cur.point.y;
                if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
                    continue;
                }
                if (maps[ny][nx] == 0) { // 막혀 있으면 그만
                    continue;
                }
                if (cur.cost < dist[ny][nx][cur.dir]) {
                    dist[ny][nx][cur.dir] = cur.cost;
                    pq.add(new CostWithPi(cur.cost, new Pi(nx, ny), cur.dir));
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