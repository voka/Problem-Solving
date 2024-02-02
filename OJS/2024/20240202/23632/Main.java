import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair{
    int t;
    int x;
    public Pair(int t, int x){
        this.t = t;
        this.x = x;
    }
}
public class Main {
    static int N,M,T;
    static int[] pre, indegree;
    static boolean[] visited; //자원 생산이 가능한지 확인하는 배열
    static List<List<Integer>> resources = new ArrayList<>();  //건물 - 생산할 수 있는 자원 리스트 
    static List<List<Integer>> graph = new ArrayList<>(); //자원 - 해당 자원을 필요로 하는 건물 리스트
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        pre = new int[M];
        indegree = new int[N];
        visited = new boolean[N];
        st = new StringTokenizer(br.readLine());

        for(int i=0;i<M;++i){
            pre[i] = Integer.parseInt(st.nextToken()) - 1;
        }  
        for(int i=0;i<N;++i){
            graph.add(new ArrayList<>());
            resources.add(new ArrayList<>());
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for(int k=0;k<num;++k){
                resources.get(i).add(Integer.parseInt(st.nextToken()) - 1);
            }
        }
        for(int i=0;i<N-M;++i){
            st = new StringTokenizer(br.readLine());
            int buildingId = Integer.parseInt(st.nextToken()) - 1;
            int resourceNum = Integer.parseInt(st.nextToken());
            indegree[buildingId] = resourceNum;
            for(int j=0;j<resourceNum;++j){
                int resource = Integer.parseInt(st.nextToken()) - 1;
                graph.get(resource).add(buildingId);
            }
        }
        Queue<Pair> queue = new LinkedList<>();
        for(int i=0;i<M;++i){
            queue.add(new Pair(0,pre[i]));
        }

        while(!queue.isEmpty()){
            Pair cur = queue.poll();
            if(cur.t >= T) continue;
            for(int r : resources.get(cur.x)){
                if(visited[r]) continue;
                visited[r] = true;
                for(int next : graph.get(r)){
                    indegree[next]--;
                    if(indegree[next] == 0){
                        queue.add(new Pair(cur.t+1, next));
                    }
                }
            }
        }
        int ans = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<N;++i){
            if(indegree[i] <= 0){
                ans++;
                sb.append(i+1).append(" "); 
            }
        }
        System.out.println(ans);
        System.out.println(sb.toString());
    }
}
