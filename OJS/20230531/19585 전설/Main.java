import java.util.*;
import java.io.*;

public class Main {
    static int cMax = 0, nMax = 0, cMin = 1001, nMin = 2001;
    static Trie color;
    static Set<String> name;

    static class Trie {
        boolean isLast;
        Trie[] child;

        public Trie() {
            isLast = false;
            child = new Trie[26];
        }

        public void insertNode(String s) {
            Trie copy = color;
            for (char a : s.toCharArray()) {
                int idx = a - 'a';
                if (copy.child[idx] == null)
                    copy.child[idx] = new Trie();
                copy = copy.child[idx];
            }
            copy.isLast = true;
        }

    }

    public static int findColor(String s) {
        Trie copy = color;
        for (int i = 0; i < s.length(); ++i) {
            char a = s.charAt(i);
            int idx = a - 'a';
            if (copy.child[idx] == null)
                return -1;
            copy = copy.child[idx];
            if (copy.isLast)
                return i + 1;
        }
        return -1; // 전체 문자열이 그냥 컬러만 있을 경우
    }

    private static boolean isWin(String s) { // 컬러를 찾으면서 사이즈를 체크해야지 시간 제한 통과 가능함 ㅇ
        int len = s.length();
        Trie copy = color;
        for (int i = 0; i < len; ++i) {
            if (len - i < nMin)
                break;
            int idx = s.charAt(i) - 'a';
            copy = copy.child[idx];
            if (copy == null)
                break;
            if (copy.isLast) {
                if (name.contains(s.substring(i + 1)))
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        // System.out.println('a' - 'a');
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int C = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int i;
        color = new Trie();
        name = new HashSet<>();
        for (i = 0; i < C; ++i) {
            String temp = br.readLine();
            int len = temp.length();
            cMax = Math.max(cMax, len);
            cMin = Math.min(cMin, len);
            color.insertNode(temp);
        }
        for (i = 0; i < N; ++i) {
            String temp = br.readLine();
            int len = temp.length();
            nMax = Math.max(nMax, len);
            nMin = Math.min(nMin, len);
            name.add(temp);
        }
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder("");
        for (i = 0; i < q; ++i) {
            String temp = br.readLine();
            if (isWin(temp)) {
                sb.append('Y').append('e').append('s').append('\n');
            } else {
                sb.append('N').append('o').append('\n');
            }
        }
        System.out.println(sb.toString());
    }

}
