import java.util.*;
import java.io.*;

public class DSLR_BIDIDFS {
    public static void BidirectionalBFS(int start, int end) {
        int[] svisited = new int[10001];
        int[] evisited = new int[10001];
        String[] scommand = new String[10001];
        String[] ecommand = new String[10001];
        Arrays.fill(scommand, ""); // 안넣으면 NULL이 안사라짐 ㅋ
        Arrays.fill(ecommand, ""); // 안넣으면 NULL이 안사라짐 ㅋ
        String[] tmp = { "D", "S", "R", "L" };

        Queue<Integer> sQueue = new LinkedList<>();
        Queue<Integer> eQueue = new LinkedList<>();
        sQueue.add(start);
        eQueue.add(end);
        svisited[start] = 1;
        evisited[end] = 1;
        while (!sQueue.isEmpty() || !eQueue.isEmpty()) {
            // while(!sQueue.isEmpty() || !eQueue.isEmpty()){
            int f = sQueue.poll();
            int e = eQueue.poll();
            // mybook e = eQueue.poll();s
            int sresult = 0;
            int eresult = 0;
            int eresult2 = 0; // using RDfunction2
            for (int i = 0; i < 4; ++i) {
                switch (i) {
                    case 0:
                        sresult = DFunction(f);
                        eresult = RDfunction1(e);
                        eresult2 = RDfunction2(e);
                        break;
                    case 1:
                        sresult = SFunction(f);
                        eresult = RSFunction(e);
                        break;
                    case 2:
                        sresult = RFunction(f);
                        eresult = LFunction(e);
                        break;
                    case 3:
                        sresult = LFunction(f);
                        eresult = RFunction(e);
                        break;
                }
                if (svisited[sresult] == 0) {
                    svisited[sresult] = 1;
                    sQueue.add(sresult);
                    scommand[sresult] = scommand[f] + tmp[i];
                }
                if (evisited[eresult] == 0) {
                    evisited[eresult] = 1;
                    eQueue.add(eresult);
                    ecommand[eresult] = ecommand[e] + tmp[i];
                }
                if (i == 0) {
                    if (evisited[eresult2] == 0) {
                        evisited[eresult2] = 1;
                        eQueue.add(eresult2);
                        ecommand[eresult2] = ecommand[e] + tmp[i];
                    }
                    if (svisited[eresult2] == 1) {
                        System.out.println(scommand[eresult2] + ecommand[eresult2]);
                        return;
                    }
                }
                if (evisited[sresult] == 1) {
                    System.out.println(scommand[sresult] + ecommand[sresult]);
                    return;
                }

                if (svisited[eresult] == 1) {
                    System.out.println(scommand[eresult] + ecommand[eresult]);
                    return;
                }

            }
        }
    }

    public static int RDfunction1(int target) {
        return (target / 2) % 10000;
    }

    public static int RDfunction2(int target) {
        return (target / 2) % 10000 + 5000;
    }

    public static int RSFunction(int target) {
        if (target == 9999)
            return 0;
        else
            return target + 1;
    }

    public static int DFunction(int target) {
        return (target * 2) % 10000;
    }

    public static int SFunction(int target) {
        if (target == 0)
            return 9999;
        else
            return target - 1;
    }

    public static int LFunction(int target) {
        return (target % 1000) * 10 + target / 1000;
    }

    public static int RFunction(int target) {
        return (target % 10) * 1000 + target / 10;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while (T > 0) {
            T--;
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            BidirectionalBFS(start, end);
        }
    }
}