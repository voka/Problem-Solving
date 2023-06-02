import java.io.*;

public class Main {
    static KMP kmp;

    static class KMP {
        char[] pattern;
        int[] pTable;
        int plen;

        public KMP(String s) {
            plen = s.length();
            pattern = s.toCharArray();
            pTable = new int[plen];
            makeTable();
        }

        public void makeTable() {
            int j = 0, i;
            for (i = 1; i < plen; ++i) {
                while (j > 0 && pattern[i] != pattern[j])
                    j = pTable[j - 1];
                if (pattern[i] == pattern[j])
                    pTable[i] = ++j;
            }

        }

        public void printPTable() {
            for (int i = 0; i < plen; ++i) {
                System.out.print(pTable[i] + " ");
            }
            System.out.println();
        }

        public boolean matching(String target) {
            int pi = 0, ti;
            for (ti = 0; ti < target.length(); ++ti) {
                while (pi > 0 && pattern[pi] != target.charAt(ti))
                    pi = pTable[pi - 1];
                if (pattern[pi] == target.charAt(ti)) {
                    if (pi == plen - 1)
                        return true;
                    pi++;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = br.readLine();
        if (temp.length() <= 2) { // 길이가 2 이하이면 그냥 답은 -1임 .
            System.out.println(-1);
            return;
        }
        kmp = new KMP(temp);
        String target = temp.substring(1, kmp.plen - 1); // 마지막 인덱스는 포함안함
        int max_len = kmp.pTable[kmp.plen - 1];
        for (int i = max_len; i >= 1; --i) {
            String prefix = temp.substring(0, i);
            String subfix = temp.substring(kmp.plen - i);
            if (prefix.equals(subfix)) {
                KMP tmp = new KMP(prefix);
                if (tmp.matching(target)) {
                    System.out.println(prefix);
                    return;
                }
            }
        }
        System.out.println(-1);
    }
}
