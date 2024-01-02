import java.io.*;
import java.util.*;

public class Main {

    static int[] dx = { 1, -1, 0, 0 };
    static int[] dy = { 0, 0, 1, -1 };

    static int[][] maps;
    static int N, M;
    static Point start, target;
    static int[][] visited;
    static int answer = 1;
    static boolean flag = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        Integer.parseInt(st.nextToken());
        Integer.parseInt(st.nextToken());
        Integer.parseInt(st.nextToken());
        Integer.parseInt(st.nextToken());

        maps = new int[N][M];
        visited = new int[N][M];
        for (int i = 0; i < N; ++i) {
            char[] temp = br.readLine().toCharArray();
            for (int j = 0; j < M; ++j) {
                if (temp[j] == '#') {
                    maps[i][j] = 2;
                    target = new Point(j, i);
                } else if (temp[j] == '*') {
                    maps[i][j] = 0;
                    start = new Point(j, i);
                } else {
                    maps[i][j] = temp[j] - '0';
                }
            }
        }
        Queue<Point> next = new LinkedList<>();
        Queue<Point> myque = new LinkedList<>();
        visited[start.y][start.x] = 1;
        myque.add(start);
        while (!myque.isEmpty()) {
            // PrintMap();
            // PrintVisited();
            while (!myque.isEmpty()) {
                Point cPoint = myque.poll();
                for (int i = 0; i < 4; ++i) {
                    int nx = cPoint.x + dx[i];
                    int ny = cPoint.y + dy[i];
                    if (nx < 0 || nx >= M || ny < 0 || ny >= N) {
                        continue;
                    }
                    if (visited[ny][nx] != 0) {
                        continue;
                    }
                    visited[ny][nx] = answer;
                    if (maps[ny][nx] == 0) {
                        myque.add(new Point(nx, ny));
                    } else if (maps[ny][nx] == 1) {
                        maps[ny][nx] = 0;
                        next.add(new Point(nx, ny));
                    } else if (maps[ny][nx] == 2) {
                        System.out.println(answer);
                        break;
                    }
                }
            }
            while (!next.isEmpty()) {
                myque.add(next.poll());
            }
            answer++;
        }
    }

    public static void PrintMap() {
        System.out.println();
        System.out.println("MAP++++");
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                System.out.print(maps[i][j]);
            }
            System.out.println();
        }
    }

    public static void PrintVisited() {
        System.out.println();
        System.out.println("Visited++++");
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                System.out.print(visited[i][j]);
            }
            System.out.println();
        }
    }

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
