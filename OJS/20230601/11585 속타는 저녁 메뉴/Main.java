import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
            int i, j = 0;
            for (i = 1; i < plen; ++i) {
                while (j > 0 && pattern[i] != pattern[j]) {
                    j = pTable[j - 1];
                }
                if (pattern[i] == pattern[j]) {
                    pTable[i] = ++j;
                }
            }
        }

        public int match(String target) {
            int tlen = target.length();
            int pi = 0, i = 0, result = 0, count = 0;
            while (count < 2) {
                while (pi > 0 && target.charAt(i) != pattern[pi])
                    pi = pTable[pi - 1];
                if (target.charAt(i) == pattern[pi]) {
                    if (pi == plen - 1) {
                        result++;
                        pi = pTable[pi];
                    } else
                        pi++;
                }
                i++;
                if (i == tlen) {
                    i = 0;
                    count++;
                }
                if (count == 1 && i == tlen - 1)
                    break;
            }
            return result;
        }
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a%b);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        kmp = new KMP(br.readLine().replaceAll(" ", ""));
        int result = kmp.match(br.readLine().replaceAll(" ", ""));
        int gcdNum = gcd(result, N);
        System.out.println(result / gcdNum + "/" + N / gcdNum);

    }
}
