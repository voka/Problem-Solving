import java.util.*;
import java.io.*;

public class Main {
    static int N, M;
    static TrieNode root;
    static char[][] map;
    static int[] dx = { 1, 1, 1, 0, -1, -1, -1, 0 };
    static int[] dy = { 1, 0, -1, -1, -1, 0, 1, 1 };

    static class TrieNode {
        Map<Character, TrieNode> child;
        boolean last;
        int count;

        public TrieNode() {
            this.child = new HashMap<Character, TrieNode>();
            last = false;
            count = 0;
        }

        public void insertNode(String word) {
            TrieNode copy = root;
            for (int i = 0; i < word.length(); ++i) {
                char cur = word.charAt(i);
                if (!copy.child.containsKey(cur)) {
                    copy.child.put(cur, new TrieNode());
                }
                copy = copy.child.get(cur);
            }
            copy.last = true;
        }

        public int contains(String target) {
            TrieNode copy = root;
            for (int i = 0; i < target.length(); ++i) {
                char cur = target.charAt(i);
                if (!copy.child.containsKey(cur)) {
                    return -1;
                }
                copy = copy.child.get(cur);
            }
            if (copy.last) {
                copy.count++;
                return 1;
            } else
                return 2;
        }

        public int getCounts(String target) {
            TrieNode copy = root;
            for (int i = 0; i < target.length(); ++i) {
                char cur = target.charAt(i);
                copy = copy.child.get(cur);
            }
            return copy.count;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        root = new TrieNode();
        map = new char[N][M];
        String[] keys = new String[K];
        for (int i = 0; i < N; ++i) {
            String tmp = br.readLine();
            for (int j = 0; j < M; ++j) {
                map[i][j] = tmp.charAt(j);
            }
        }
        for (int i = 0; i < K; ++i) {
            String word = br.readLine();
            root.insertNode(word);
            // mydict.put(word, 0);
            keys[i] = word;
        }
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                dfs(j, i, new StringBuilder(map[i][j] + ""));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : keys) {
            sb.append(root.getCounts(s)).append("\n");
        }
        System.out.print(sb);

    }

    public static void dfs(int x, int y, StringBuilder sb) {
        String word = sb.toString();
        int result = root.contains(word);
        if (result == -1) {
            return;
        }
        for (int i = 0; i < 8; ++i) {
            int nx = (x + dx[i]) % M;
            int ny = (y + dy[i]) % N;
            if (nx < 0)
                nx += M;
            if (ny < 0)
                ny += N;
            sb.append(map[ny][nx]);
            dfs(nx, ny, sb);
            sb.deleteCharAt(word.length());
        }
    }
}
