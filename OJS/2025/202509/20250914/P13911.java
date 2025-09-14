import java.io.*;
import java.util.*;


public class P13911 {

    static int V, E, M, S, x, y;
    static int[] mac, star;
    static long[][] dist;
    static List<List<Point>> graph;
    static PriorityQueue<Point> pq;
    static class Point implements Comparable<Point>{
        int v;
        long w;
        int type;
        public Point(int v, int w){
            this.v = v;
            this.w = w;
        }
        public Point(int v, long w, int type){
            this.v = v;
            this.w = w;
            this.type = type;
        }
        @Override
        public int compareTo(Point p){
            return Long.compare(this.w, p.w);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());    
        graph = new ArrayList<>();
        for(int i=0;i<=V;++i){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<E;++i){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph.get(u).add(new Point(v, w));
            graph.get(v).add(new Point(u, w));
        }
        dist = new long[V+1][2];
        for(int i=0;i<=V;++i){
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }
        pq = new PriorityQueue<>();
        // 맥도날드 정보 입력받기
        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        mac = new int[M];
        st = new StringTokenizer(br.readLine());
        boolean[] check = new boolean[V+1];
        for(int i=0;i<M;++i){
            mac[i] = Integer.parseInt(st.nextToken());
            pq.add(new Point(mac[i], 0L, 0));
            dist[mac[i]][0] = 0;
            check[mac[i]] = true;
        }

        // 스타벅스 정보 입력받기
        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        star = new int[S];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<S;++i){
            star[i] = Integer.parseInt(st.nextToken());
            pq.add(new Point(star[i], 0L, 1));
            dist[star[i]][1] = 0;
            check[star[i]] = true;
        }
        while(!pq.isEmpty()){
            Point cur = pq.poll();
            if(dist[cur.v][cur.type] < cur.w) continue;
            for(Point next : graph.get(cur.v)){
                long nCost = cur.w + next.w;
                if(cur.type == 0 && nCost > x) continue;
                if(cur.type == 1 && nCost > y) continue;
                if(dist[next.v][cur.type] > nCost){
                    dist[next.v][cur.type] = nCost;
                    pq.add(new Point(next.v, nCost, cur.type));
                }
            }
        }
        long answer = Long.MAX_VALUE;
        for(int i=1;i<=V;++i){
            if(check[i]) continue;
            if(dist[i][0] == Long.MAX_VALUE || dist[i][1] == Long.MAX_VALUE) continue;
            answer = Math.min(answer, dist[i][0] + dist[i][1]);
        }
        System.out.println(answer == Long.MAX_VALUE ? -1 : answer);

    }    
}
