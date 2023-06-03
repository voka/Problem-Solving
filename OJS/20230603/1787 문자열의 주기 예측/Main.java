
import java.io.*;

public class Main {
    static int len;
    static int[] pTable;
    static int[] small_common;

    public static void makeTable(char[] pattern) {
        int i, j = 0;
        for (i = 1; i < len; ++i) {
            while (j > 0 && pattern[i] != pattern[j])
                j = pTable[j - 1]; // j가 0 이면 while 문에서 탈출하기 때문에 index 에러가 나지 않음
            if (pattern[i] == pattern[j]) // 같으면 현재 prefix,suffix의 길이를 1 증가 시킴 (pre == suf)
                pTable[i] = ++j;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        len = Integer.parseInt(br.readLine());
        pTable = new int[len];
        small_common = new int[len + 1]; // small_common[i] -> 0 ~ i 번째의 문자열 중에서 공통 접미, 접두어 중에서 가장 길이가 짧은 것의 길이
        long answer = 0;
        String p = br.readLine();
        makeTable(p.toCharArray());
        // for (int i = 0; i < len; ++i) {
        // System.out.print(pTable[i] + " ");
        // }
        // System.out.println();
        for (int i = 1, j = 0; i <= len; ++i, ++j) {// P[i] 는 1 부터 N 까지 존재하기 때문에 i만 인덱스를 다르게 한거임
            if (pTable[j] != 0) { // 길이가 1 이상인 접두, 접미어가 존재함
                if (small_common[pTable[j]] != 0) { // 더 작은 접미어가 존재할 경우 그걸로 바꿈
                    small_common[i] = small_common[pTable[j]];
                } else
                    small_common[i] = pTable[j]; // 더 작은 공통 접미 접두어가 없으면 pTable[j] 값이 제일 작은 거임.
            }
            // System.out.println(i + ", " + small_common[i] + ", " + i / 2 + ", " + (i -
            // small_common[i]));
            if (small_common[i] != 0 && i - small_common[i] >= i / 2) { // 공통 접미 접두어가 존재하고, i에서 제일 작은 공통 접미두어를 뺀 값이 부분이
                                                                        // 문자열의 길이 / 2 보다 같거나 크면 정답에 더하기
                answer += (i - small_common[i]);
            }
        }
        // for (int i = 1; i <= len; ++i) {
        // System.out.print(small_common[i] + " ");
        // }
        // System.out.println();
        System.out.println(answer);
    }
}
