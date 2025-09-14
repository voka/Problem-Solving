import java.io.*;
import java.util.*;


public class P13911di2 {

    static int V, E, M, S, x, y;
    static int[] mac, star;
    static List<List<Point>> graph;
    static class Point implements Comparable<Point>{
        int v;
        long w;
        public Point(int v, long w){
            this.v = v;
            this.w = w;
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
        // 맥도날드 정보 입력받기
        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        mac = new int[M];
        st = new StringTokenizer(br.readLine());
        boolean[] check = new boolean[V+1];
        for(int i=0;i<M;++i){
            mac[i] = Integer.parseInt(st.nextToken());
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
            check[star[i]] = true;
        }
        long[] distMac = di(x, mac);
        long[] distStar = di(y, star);


        long answer = Long.MAX_VALUE;
        for(int i=1;i<=V;++i){
            if(check[i]) continue;
            if(distMac[i] == Long.MAX_VALUE || distStar[i] == Long.MAX_VALUE) continue;
            answer = Math.min(answer, distMac[i] + distStar[i]);
        }
        System.out.println(answer == Long.MAX_VALUE ? -1 : answer);

    }    
    static long[] di(int max, int[] arr){

        long[] dist = new long[V+1];
        Arrays.fill(dist, Long.MAX_VALUE);
        PriorityQueue<Point> pq = new PriorityQueue<>();

        for(int s : arr){
            dist[s] = 0;
            pq.add(new Point(s, 0));
        }
        while(!pq.isEmpty()){
            Point cur = pq.poll();
            if(dist[cur.v] < cur.w) continue;
            for(Point next : graph.get(cur.v)){
                long nCost = cur.w + next.w;
                if(nCost > max) continue;
                if(dist[next.v] > nCost){
                    dist[next.v] = nCost;
                    pq.add(new Point(next.v, nCost));
                }
            }
        }
        return dist;
    }
}
