import java.util.*;
import java.io.*;

public class Main {
    static TrieNode root;
    static StringBuilder sb = new StringBuilder();

    static class TrieNode {
        Map<String, TrieNode> child;

        public TrieNode() {
            child = new TreeMap<String, TrieNode>();
        }

        public TrieNode insertNode(String child) {
            if (!this.child.containsKey(child)) {
                this.child.put(child, new TrieNode());
            }
            return this.child.get(child);
        }

        public void getTreeMap(int depth) {
            for (String key : this.child.keySet()) {
                addDepth(depth);
                sb.append(key);
                sb.append('\n');
                this.child.get(key).getTreeMap(depth + 1);
            }
        }

        public void addDepth(int depth) {
            for (int i = 0; i < depth; ++i) {
                sb.append("--");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        root = new TrieNode();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            TrieNode copyRoot = root;
            for (int j = 0; j < K; ++j) {
                String key = st.nextToken();
                copyRoot = copyRoot.insertNode(key);
            }
        }
        root.getTreeMap(0);
        System.out.println(sb.toString());
    }

}
