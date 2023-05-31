import java.util.*;
import java.io.*;

public class Main {
    static Trie root;

    static class Trie {
        Map<String, Trie> child;

        public Trie() {
            child = new TreeMap<>();
        }

        public void insertNode(String tmp) {
            Trie copy = root;
            String[] tmpList = tmp.split("\\\\");
            for (String s : tmpList) {
                if (!copy.child.containsKey(s))
                    copy.child.put(s, new Trie());
                copy = copy.child.get(s);
            }
        }

        public void print(int count) {
            for (String s : this.child.keySet()) {
                for (int i = 0; i < count; ++i) {
                    System.out.print(" ");
                }
                System.out.println(s);
                this.child.get(s).print(count + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        root = new Trie();
        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; ++i) {
            String tmp = br.readLine();
            root.insertNode(tmp);
        }
        root.print(0);
    }
}
