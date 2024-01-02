import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] pattern;
    static int[] pTable;
    static int plen;

    static void makeTable() {
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

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        plen = Integer.parseInt(br.readLine());
        pTable = new int[plen];
        pattern = new int[plen];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < plen; ++i) {
            int j = plen - i - 1;
            pattern[j] = Integer.parseInt(st.nextToken());
        }
        
        makeTable();
        int count = 0, max_num = 0;
        for (int i = 1; i < plen; ++i) {
            if (pTable[i] > max_num) {
                count = 0;
                max_num = pTable[i];
            }
            if (pTable[i] == max_num)  {
                count++;
            }
        }
        if (max_num == 0) {
            System.out.println(-1);
        } else {
            System.out.println(max_num + " " + count);
        }

    }
}
