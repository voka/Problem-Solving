
public class Main {

    static KMP kmp;

    static class KMP {
        int len;
        char[] pattern;
        int[] patternTable;

        public KMP(String s) {
            pattern = s.toCharArray();
            len = s.length();
            makeTable();
        }

        public void makeTable() {
            patternTable = new int[len];
            int i, j = 0;
            for (i = 1; i < len; ++i) {
                while (pattern[i] != pattern[j] && j > 0)
                    j = patternTable[j - 1];
                if (pattern[i] == pattern[j])
                    patternTable[i] = ++j;
            }
            for (i = 0; i < len; ++i) {
                System.out.print(patternTable[i] + ", ");
            }
        }

        public int matching(String target) {
            int n = target.length();
            int i, pi = 0, result = 0, find_idx = 0;
            for (i = 0; i < n; ++i) {
                while (pi > 0 && target.charAt(i) != pattern[pi])
                    pi = patternTable[pi - 1];
                if (target.charAt(i) == pattern[pi]) {
                    if (pi == len - 1) {
                        result++;
                        find_idx = i - len + 2;
                        System.out.println(find_idx);
                        pi = patternTable[pi];
                    } else {
                        pi++;
                    }
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        kmp = new KMP("AABAAB");
        System.out.println();
        System.out.println(kmp.matching("BAABAABAABAA"));

    }
}
