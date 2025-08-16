import java.util.*;
import java.io.*;

class Node implements Comparable<Node>{
    int v;
    int cost;
    int cnt;
    public Node(int v, int cost){
        this.v = v;
        this.cost = cost;
    }
    public Node(int v, int cost, int cnt){
        this.v = v;
        this.cost = cost;
        this.cnt = cnt;
    }
    @Override
    public int compareTo(Node n){
        return this.cost - n.cost;
    }
}
public class P13907 {
    static int[] p;
    static int N, M, K, S, D;
    static List<List<Node>> graph;
    static List<Node> dest;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());


        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        
        dest = new ArrayList<>();
        graph = new ArrayList<>();
        
        for(int i=0;i<=N;++i){
            graph.add(new ArrayList<>());
        }

        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            graph.get(a).add(new Node(b,c));
            graph.get(b).add(new Node(a,c));
        }
        StringBuilder sb = new StringBuilder();
        int pSum = 0;
        int[][] dist = bfs();
        for(int i=0;i<=K;++i){
            if(i>0) {
                int p = Integer.parseInt(br.readLine());
                pSum += p; 
            }
            int answer = Integer.MAX_VALUE;
            for(int j=1;j<=N;++j){
                if(dist[D][j] != Integer.MAX_VALUE)
                    answer = Math.min(answer, dist[D][j] + j * pSum);
            }
            sb.append(answer).append('\n');

        }
        System.out.println(sb.toString());


    }

    static int[][] bfs(){
        int[][] dist = new int[N+1][N+1];
        for(int i=1;i<=N;++i){
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[S][0] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(S, 0,0));

        while(!pq.isEmpty()){
            Node cur = pq.poll();
            if(dist[cur.v][cur.cnt] < cur.cost) continue;
            for(Node next : graph.get(cur.v)){
                int nCost = next.cost + cur.cost;
                int nCnt = cur.cnt + 1;
                if(nCnt <= N && dist[next.v][nCnt] > nCost){
                    dist[next.v][nCnt] = nCost;
                    pq.add(new Node(next.v, nCost, nCnt)); 
                }
            }
        }
        return dist;

    }   
}
