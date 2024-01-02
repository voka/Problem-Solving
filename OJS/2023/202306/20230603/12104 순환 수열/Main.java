import java.io.*;

public class Main {

    static class KMP {
        char[] p;
        int[] pT;
        int len;

        public KMP(String s) {
            len = s.length();
            p = s.toCharArray();
            pT = new int[len];
            makeTable();
        }

        public void makeTable() { // pT[i] = 3 -> p문자열의 부분 분자열인 p[0 ~ i+1]은 prefix와 suffix가 최대 길이 3 만큼 같다.
            int i, j = 0;
            for (i = 1; i < len; ++i) {
                while (j > 0 && p[i] != p[j]) // j가 0일때 까지
                    j = pT[j - 1];
                if (p[i] == p[j]) {
                    pT[i] = ++j;
                }
            }
        }

        /*
         * p = 1 0 1
         * pT = 0 1 2
         * t = 1 1 1 1 1
         * i = 0, j = 0
         * i, j
         * 1 0 1 0 1
         * i,j
         * 1 1 1 1 1
         * 
         */
        public int match(String t) {
            int i, j = 0, count = 0;
            for (i = 0; i < t.length() - 1; ++i) {
                // System.out.println(i + " " + j);
                // System.out.println(t.charAt(i) + " " + p[j]);
                // pT[0] == 0-> 이기 때문에 pT[j-1] -> j가 1일때 까지 반복된다.
                while (j > 0 && t.charAt(i) != p[j]) { // j -> 3 이면 -> j -> 0 일때 까지 j에 pT[j - 1] 하면서 현재 i번째 문자와 같은지 확인
                    // System.out.println("J == " + j);
                    j = pT[j - 1];
                }
                if (t.charAt(i) == p[j]) {
                    if (j == len - 1) {
                        count++;
                        j = pT[j];
                    } else
                        ++j;
                }
            }
            return count;
        }

        public void printTable() {
            for (int i = 0; i < len; ++i) {
                System.out.print(pT[i] + ", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String p = br.readLine();
        String t = br.readLine();
        KMP kmp = new KMP(p);
        System.out.println(kmp.match(t + t));
    }
}
