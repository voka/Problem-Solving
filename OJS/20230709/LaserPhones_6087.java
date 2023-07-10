import java.util.*;
import java.io.*;

class LaserPhones_6078 {

    static int[][] maps; // . -> 1, * -> 0, C -> 1

    static pi[] cPoint = new pi[2];

    static int[] dx = { 1, 0, -1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int W = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());
        maps = new int[H][W];
        int c_idx = 0;
        for (int i = 0; i < H; ++i) {
            char[] temp = br.readLine().toCharArray();
            for (int j = 0; i < W; ++j) {
                char a = temp[j];
                if (a == '.') {
                    maps[i][j] = 1;
                } else if (a == '*') {
                    maps[i][j] = 0;
                } else {
                    maps[i][j] = 1;
                    cPoint[c_idx] = new pi(j, i);
                    c_idx++;
                }
            }
        }

    }

    static class pi {
        int x;
        int y;

        public pi(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}