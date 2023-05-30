import java.util.*;
import java.io.*;

public class Main {
    static int N;
    static TrieNode root;

    static class TrieNode {
        boolean isLast;
        int numberOfChild;
        Map<Character, TrieNode> child;

        public TrieNode() {
            isLast = false;
            numberOfChild = 0;
            child = new HashMap<>();
        }

        public void insertNode(String s) {
            TrieNode copy = root;
            for (char a : s.toCharArray()) {
                if (!copy.child.containsKey(a)) {
                    copy.numberOfChild++;
                    copy.child.put(a, new TrieNode());
                }
                copy = copy.child.get(a);
            }
            copy.isLast = true;
        }

        public int search() {
            int result = 0;
            for (char a : this.child.keySet()) {
                if (this.numberOfChild > 1) {
                    System.out.println(a);
                    result += this.numberOfChild;
                }
                result += this.child.get(a).search();
            }
            return result;
        }

        public int count(String target) {
            TrieNode copy = root.child.get(target.charAt(0));
            int count = 1;
            for (int i = 1; i < target.length(); ++i) {
                char a = target.charAt(i);
                if (copy.numberOfChild > 1 || copy.isLast) {
                    count += 1;
                }
                copy = copy.child.get(a);
            }
            return count;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text;
        while (true) {
            text = br.readLine();
            if (text == null) {
                break;
            }
            root = new TrieNode();
            N = Integer.parseInt(text);
            String[] myList = new String[N];
            for (int i = 0; i < N; ++i) {
                String temp = br.readLine();
                myList[i] = temp;
                root.insertNode(temp);
            }
            double answer = 0;
            for (String s : myList) {
                answer += root.count(s);
            }
            answer = answer / N;
            String result = String.format("%.2f", (double) Math.round(answer * 100) / 100);
            System.out.println(result);

        }
    }

}
