import java.io.*;
import java.util.*;

public class P32936 {
    static int N,M,a,b,c;
    static List<List<Integer>> graph;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        graph = new ArrayList<>();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        for(int i=0;i<=N;++i){
            graph.add(new ArrayList<>());
        }

        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph.get(u).add(v);
        }

        long[] dist1 = bfs(1);
        long[] distb = bfs(b);
        // 길 3 : 1 -> a -> b -> a -> b -> ......(-inf)
        
        if(dist1[a] != Long.MAX_VALUE  && distb[N] != Long.MAX_VALUE && distb[a] < c){
            System.out.println("-inf");
            return;
        }

        // System.out.println("타임 머신 이용 가능 여부 : " + dist1[a]);
        // System.out.println("타임 머신 이용한 뒤 N번에 도착가능 여부: " + distb[N]);
        // System.out.println("타임 머신 추가 이용 가능 여부 : " + distb[a]);
        // System.out.println("타임머신을 계속 이용하는게 이득인지 판단 : " + (distb[a] < c));        
        // 길 1 : 1 -> N;
        long road1 = dist1[N];
        // 길 2 : 1 -> a -> b -> N;
        long road2 = Long.MAX_VALUE;
        if(dist1[a] != Long.MAX_VALUE && distb[N] != Long.MAX_VALUE){
            road2 = dist1[a] + distb[N] - c;
        }
        
        long minRoad = Math.min(road1,road2);
        if(minRoad != Long.MAX_VALUE){
            System.out.println(minRoad);
        }else{
            System.out.println("x");
        }


    }   
    static long[] bfs(int s){
        long[] dist = new long[N+1];
        Arrays.fill(dist,Long.MAX_VALUE);

        Queue<Integer> que = new ArrayDeque<>();

        que.add(s);
        dist[s] = 0;
        boolean[] visit = new boolean[N+1];
        visit[s] = true;
        while(!que.isEmpty()){
            int cur = que.poll();
            for(int next : graph.get(cur)){
                if(visit[next]) continue;
                long nCost = dist[cur] + 1;
                if(nCost < dist[next]){
                    dist[next] = nCost;
                    visit[next] = true;
                    que.add(next);
                }
            }
        }
        return dist ;
    }
}
