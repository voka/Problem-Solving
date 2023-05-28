import java.util.*;
import java.io.*;

public class Main {
    static int size;
    static boolean[][] visited;
    static TrieNode root;
    static Map<String, Integer> scoreDict;
    static int[] dx = { 1, 1, 1, 0, -1, -1, -1, 0 };
    static int[] dy = { 1, 0, -1, -1, -1, 0, 1, 1 };

    static class TriePair {
        int x;
        int y;
        int count;
        TrieNode node;

        public TriePair(int x, int y, int count, TrieNode node) {
            this.y = y;
            this.x = x;
            this.count = count;
            this.node = node;
        }
    }

    static class TrieNode {
        Map<Character, TrieNode> child;

        public TrieNode() {
            child = new HashMap<Character, TrieNode>();
        }

        public TrieNode insertNode(Character child) {
            if (!this.child.containsKey(child)) {
                this.child.put(child, new TrieNode());
            }
            return this.child.get(child);
        }

    }

    public static boolean find(String target) {
        TrieNode copy = root;
        for (int i = 0; i < target.length(); ++i) {
            char a = target.charAt(i);
            if (!copy.child.containsKey(a)) {
                return false;
            }
            copy = copy.child.get(a);
        }
        return true;

    }

    public static int getScore(int len) {
        if (len <= 2) {
            return 0;
        } else if (len <= 4) {
            return 1;
        } else if (len == 5) {
            return 2;
        } else if (len == 6) {
            return 3;
        } else if (len == 7) {
            return 5;
        } else {
            return 11;
        }
    }

    public static void make_trie(String[] map, int x, int y, TrieNode trieNode) {
        for (int i = 0; i < 8; ++i) {
            int new_x = x + dx[i];
            int new_y = y + dy[i];
            if ((new_x < 0) || (new_x > 3) || (new_y < 0) || (new_y) > 3)
                continue;
            if (visited[new_y][new_x])
                continue;
            visited[new_y][new_x] = true;
            make_trie(map, new_x, new_y, trieNode.insertNode(map[y].charAt(x)));
            visited[new_y][new_x] = false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        scoreDict = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                int lendiff = b.length() - a.length();
                if (lendiff != 0)
                    return lendiff;
                else
                    return a.compareTo(b);
            }
        });
        for (int i = 0; i < N; ++i) {
            String cur = br.readLine();
            scoreDict.put(cur, getScore(cur.length()));
            // System.out.println(cur);
        }
        br.readLine(); // 개행 제거용
        int B = Integer.parseInt(br.readLine());
        String answer = "";
        while (B > 0) {
            root = new TrieNode();
            visited = new boolean[4][4];
            int total = 0;
            int best = -1;
            int count = 0;
            B--;
            String[] map = new String[4];
            for (int i = 0; i < 4; ++i) {
                map[i] = br.readLine();
                // System.out.println(map[i]);
            }

            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    visited[i][j] = true;
                    make_trie(map, j, i, root);
                    visited[i][j] = false;

                }
            }
            for (String key : scoreDict.keySet()) {
                if (find(key)) {
                    int score = scoreDict.get(key);
                    if (best < score) {
                        best = score;
                        answer = key;
                    }
                    total += score;
                    count++;
                }
            }
            System.out.println(total + " " + answer + " " + count);

            if (B != 0)
                br.readLine(); // 개행 제거용
        }

    }

}
