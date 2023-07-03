package DisJointSet;
import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
public class _1939_WeightLimit {
    static int start, end;
    static int[] parent;
    // 가중치가 큰 값이 먼저 나오게 우선순위 큐 조건 설정
    static PriorityQueue<int[]> pqueue = new PriorityQueue<>(new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o2[2] - o1[2]; // o1의 가중치가 o2보다 크다면 음수가 되어 o1이 나옴.
        }
    });
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        parent = new int[n+1];
        for(int i=1;i<=n;i++){
            parent[i] = i;
        }
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            pqueue.add(new int[]{a,b,c});
        }
        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());
        weight(start, end);

    }
    public static int find(int x){
        if(parent[x] == x)
            return x;
        else{
            return find(parent[x]);
        }
    }
    public static void union(int x, int y){
        x = find(x);
        y = find(y);
        if( x  > y){
            parent[y] = x;
        }
        if( x< y ) {
            parent[x] =y;
        }
    }
    public static void weight(int start, int end){
        while(!pqueue.isEmpty()){
            int[] tmp = pqueue.poll();
            union(tmp[0], tmp[1]);
            if(find(start)==find(end)){
                System.out.println(tmp[2]);
                return;
            }
        }
    }
}
