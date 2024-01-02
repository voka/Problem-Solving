import java.util.*;
import java.io.*;

public class Main {
    static int size;
    static boolean[][] visited;
    static TrieNode root;
    static Map<String, Integer> scoreDict;
    static Set<String> answers;
    static int[] dx = { 1, 1, 1, 0, -1, -1, -1, 0 };
    static int[] dy = { 1, 0, -1, -1, -1, 0, 1, 1 };

    static class TrieNode {
        Map<Character, TrieNode> child;
        boolean last;

        public TrieNode() {
            child = new HashMap<Character, TrieNode>();
            last = false;
        }

        public void insertNode(String word) {
            TrieNode cur = root;
            for (int i = 0; i < word.length(); ++i) {
                char c = word.charAt(i);
                if (!cur.child.containsKey(c)) {
                    cur.child.put(c, new TrieNode());
                }
                cur = cur.child.get(c);
            }
            cur.last = true;
        }

        public int contains(String target) {
            TrieNode copy = root;
            for (int i = 0; i < target.length(); ++i) {
                char a = target.charAt(i);
                if (!copy.child.containsKey(a)) {
                    return -1;
                }
                copy = copy.child.get(a);
            }
            // System.out.println(target + ", " + copy.last);
            if (copy.last)
                return 1; // 제일 끝
            else
                return 2; // 존재하는데 중간 놈임

        }

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

    public static void make_trie(char[][] map, int x, int y, StringBuilder sb) {
        String word = sb.toString();
        int result = root.contains(word);
        if (result == -1) { // 존재하지 않을 경우
            return;
        }
        if (result == 1) { // 존재하면서 동시에 맨 마지막 노드인 경우 -> 전부 다 찾음
            answers.add(word);
        }
        for (int i = 0; i < 8; ++i) {
            int new_x = x + dx[i];
            int new_y = y + dy[i];
            if ((new_x < 0) || (new_x > 3) || (new_y < 0) || (new_y) > 3)
                continue;
            if (visited[new_y][new_x])
                continue;
            visited[new_y][new_x] = true;
            sb.append(map[new_y][new_x]);
            make_trie(map, new_x, new_y, sb);
            visited[new_y][new_x] = false;
            sb.deleteCharAt(word.length());
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        root = new TrieNode();
        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; ++i) {
            String cur = br.readLine();
            /// System.out.println(cur.length());
            root.insertNode(cur);

        }
        br.readLine(); // 개행 제거용
        int B = Integer.parseInt(br.readLine());
        while (B > 0) {
            visited = new boolean[4][4];
            answers = new HashSet<>();
            B--;
            char[][] map = new char[4][4];
            for (int i = 0; i < 4; ++i) {
                String temp = br.readLine();
                for (int j = 0; j < 4; ++j) {
                    map[i][j] = temp.charAt(j);
                }
                // System.out.println(map[i]);
            }

            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    visited[i][j] = true;
                    make_trie(map, j, i, new StringBuilder(map[i][j] + ""));
                    visited[i][j] = false;

                }
            }
            int total = 0;
            int best = -1;
            int count = answers.size();
            String longAnswer = "";
            Iterator<String> it = answers.iterator();
            while (it.hasNext()) {
                String key = it.next();
                int score = getScore(key.length());
                if (best <= score) {
                    best = score;
                    // System.out.println(longAnswer + ", "+key+ ", "+ key.compareTo(longAnswer));
                    if (key.length() != longAnswer.length())
                        longAnswer = key;
                    else if (key.compareTo(longAnswer) < 0) {
                        longAnswer = key;
                    }
                }
                total += score;
            }
            System.out.println(total + " " + longAnswer + " " + count);

            if (B != 0)
                br.readLine(); // 개행 제거용
        }

    }

}
