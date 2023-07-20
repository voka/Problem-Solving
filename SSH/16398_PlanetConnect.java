package MinimumSpanningTree;
import java.io.*;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.Comparator;
public class _16398_PlanetConnect {
    static int n;
    static int[] root;
    static int[][] map;
    static PriorityQueue<int[]> pqueue = new PriorityQueue<>(new Comparator<int[]>(){
        @Override
        public int compare(int[] o1, int[] o2){return o1[2] - o2[2];}
    });

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        root = new int[n+1];
        map = new int[n+1][n+1];

        for(int i=0;i<=n;i++)
            root[i] = i;

        for(int i=1;i<=n;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=n;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(i<j)
                    pqueue.add(new int[]{i,j,map[i][j]});
            }
        }
        long totalCost = 0;
        while (!pqueue.isEmpty()) {
            int[] tmp = pqueue.poll();
            if(union(tmp[0],tmp[1])){
                totalCost += tmp[2];
            }
        }
        System.out.println(totalCost);

    }
    public static int find(int x){
        if(root[x] == x)
            return x;
        else
            return root[x]=find(root[x]);
    }
    public static boolean union(int x, int y){
        x = find(x);
        y = find(y);

        if(x!=y){
            if(x<y) root[y] = x;
            else root[x] = y;
            return true;
        }
        return false;
    }
}
