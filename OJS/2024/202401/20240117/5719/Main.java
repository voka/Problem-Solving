
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Path { // 경로 저장용 class
    int u;
    int v;
    int cost;
    public Path(int u, int v, int cost){
        this.u = u;
        this.v = v;
        this.cost = cost;
    }
}

class Pair implements Comparable<Pair>{ // 다익스트라용 class
    int x;
    int cost;
    public Pair(int x,int cost){
        this.x = x;
        this.cost = cost;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.cost > o.cost){
            return 1;
        }
        else if(this.cost < o.cost){
            return -1; 
        }
        return 0;
    }
    
}
public class Main {
    static int N,M,S,D;
    static List<List<Pair>> sGraph, rGraph;// 정방향 그래프, 역방향 그래프
    static boolean[][] pathCanNotUse; // 최단경로에서 사용하는 간선들의 정보를 모아둔 boolean 배열
    static Path[] paths; // Path Array
    static int[] findShortestPath(int s, List<List<Pair>> graph){ // 다익스트라 결과를 반환
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        int[] dist = new int[N];
        for(int i=0;i<N;++i){// 배열 초기화
            dist[i] = Integer.MAX_VALUE;
        }
        dist[s] = 0;
        pq.add(new Pair(s, 0));
        while(!pq.isEmpty()){
            Pair cur = pq.poll();
            if(cur.cost > dist[cur.x]) continue; 
            for(Pair next : graph.get(cur.x)){
                if(pathCanNotUse[cur.x][next.x]) continue;// 최단거리 경로에 포함된 경로는 사용하지 않아야 한다.
                int nCost = cur.cost + next.cost;
                if(dist[next.x] > nCost){
                    dist[next.x] = nCost;
                    pq.add(new Pair(next.x, nCost));
                }
            }

        }
        return dist;
    }
    static void findShortestPathVertex(int[] sDist, int[] dDist){ // 최단 경로에 포함된 경로들을 확인하는 함수
        for(Path path : paths){
            int sPath = sDist[path.u];
            int dPath = dDist[path.v];
            // S -> u  + u -> v + v -> D 까지의 거리가 최단경로의 합과 같다면 최단 경로에 포함된 경로다.
            if(sPath + path.cost + dPath == sDist[D]){
                pathCanNotUse[path.u][path.v] = true;
            }
        }
    }
    static void printArray(int[] arr){
        for(int i=0;i<arr.length;++i){
            System.out.printf("%d ",arr[i]);
        }
        System.out.println();
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while(true){
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            if(N == 0 && M == 0) break;
            pathCanNotUse = new boolean[N][N];
            st = new StringTokenizer(br.readLine());
            S = Integer.parseInt(st.nextToken());
            D = Integer.parseInt(st.nextToken());
            sGraph = new ArrayList<>();//S 에서 사용할 graph
            rGraph = new ArrayList<>();//D 에서 사용할 graph
            paths = new Path[M];
            for(int i=0;i<N;++i){
                sGraph.add( new ArrayList<>());
                rGraph.add( new ArrayList<>());
            }   
            for(int i=0;i<M;++i){
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int p = Integer.parseInt(st.nextToken());  
                sGraph.get(u).add(new Pair(v, p));
                rGraph.get(v).add(new Pair(u, p));
                paths[i] = new Path(u, v, p);
            }
            int[] sDist = findShortestPath(S,sGraph); // 첫번째 최단 경로 탐색 
            //System.out.println("sDist ===============");
            //printArray(sDist);
            //이때 최단 경로가 발견되지 않으면 정답은 -1 이다. 
            if(sDist[D] == Integer.MAX_VALUE){
                sb.append(-1).append('\n');
                continue;
            }
            int[] dDist = findShortestPath(D,rGraph); // 역방향 퇴단 경로 탐색
            //System.out.println("dDist ===============");
            //printArray(dDist);
            findShortestPathVertex(sDist, dDist); // 최단 경로에 포함된 간선들 제거하기

            int[] answer = findShortestPath(S,sGraph); // 다시 최단 경로를 찾는다.
            
            //System.out.println("answer ===============");
            //printArray(answer);
            if(answer[D] == Integer.MAX_VALUE){ // 거의 최단 경로가 없다면 정답은 -1이다.
                sb.append(-1).append('\n');
                continue;
            }
            sb.append(answer[D]).append('\n');
        }
        System.out.println(sb.toString());
    }
}


