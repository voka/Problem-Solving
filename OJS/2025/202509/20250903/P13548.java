import java.io.*;
import java.util.*;

public class P13548 {

    static int N,M;
    static int bSize,maxCount;
    static int[] arr;
    static int[] cnt, tCnt;

    static class Query implements Comparable<Query>{
        int l;
        int r;
        int no;
        public Query(int l, int r, int no){
            this.l = l;
            this.r = r;
            this.no = no;
        }
        @Override
        public int compareTo(Query o) {
            int num1 = this.l/bSize;
            int num2 = o.l/bSize;
            if(num1 == num2) return Integer.compare(this.r, o.r);
            return Integer.compare(num1, num2);
        }
    }

    static void add(int num){
        tCnt[cnt[num]]--;
        if(maxCount == cnt[num]){
            maxCount++;
        }
        cnt[num]++;
        tCnt[cnt[num]]++;
    }
    static void remove(int num){
        tCnt[cnt[num]]--;
        if(tCnt[cnt[num]] == 0 && maxCount == cnt[num]){
            maxCount--;
        }
        cnt[num]--;
        tCnt[cnt[num]]++;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        bSize = (int) Math.sqrt(N);
        cnt = new int[100001];
        tCnt = new int[100001];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        M = Integer.parseInt(br.readLine());
        Query[] query = new Query[M];
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken())-1;
            query[i] = new Query(a, b, i);
        }

        Arrays.sort(query);


        int curL = 0;
        int curR = -1;
        int[] ans = new int[M];
        StringBuilder sb = new StringBuilder();
        for(Query q : query){
            while(curL > q.l){
                add(arr[--curL]);
            }
            while(curR < q.r){
                add(arr[++curR]);
            }
            while(curL < q.l){
                remove(arr[curL++]);
            }
            while(curR > q.r){
                remove(arr[curR--]);
            }
            ans[q.no] = maxCount;
        }
        for(int i=0;i<M;++i){
            sb.append(ans[i]).append('\n');
        }
        System.out.println(sb);

    }
}
