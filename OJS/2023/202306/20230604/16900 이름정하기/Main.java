import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static long K, plen;
    static char[] pattern;
    static long[] pTable;

    public static void makeTable() {
        int i, j = 0;
        for (i = 1; i < plen; ++i) {
            while (j > 0 && pattern[i] != pattern[j])
                j = (int) pTable[j - 1];
            if (pattern[i] == pattern[j])
                pTable[i] = ++j;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        pattern = temp[0].toCharArray();
        plen = pattern.length;
        pTable = new long[(int) plen];
        K = Integer.parseInt(temp[1]);
        makeTable();
        // System.out.println(pattern);
        // for (int i = 0; i < plen; ++i) {
        // System.out.print(pTable[i] + " ");
        // }
        // System.out.println(pTable[plen - 1]);
        // System.out.println(plen);
        // System.out.println(plen - pTable[plen - 1]);
        long answer = (plen) + (K - 1) * (plen - pTable[(int) plen - 1]);
        System.out.println(answer);
    }
}
