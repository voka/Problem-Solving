import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N,M;
    static int[] indegree;
    static List<List<Integer>> graph = new ArrayList<>(); //자원 - 해당 자원을 필요로 하는 건물 리스트
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        indegree = new int[N+1];
        int a,b;
        for(int i=0;i<N+1;++i){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            graph.get(b).add(a);
            indegree[a]++;
        }
        Queue<Integer> my = new LinkedList<>();
        for(int i=1;i<=N;++i){
            if(indegree[i] == 0){
                my.add(i);
            }
        }
    
        int ans = my.size();
        while(!my.isEmpty()){
            int cur = my.poll();
            for(int next : graph.get(cur)){
                indegree[next]--;
                if(indegree[next] == 0){
                    my.add(next);
                    ans++;
                }
                
            }
        }
        System.out.println(ans);
    }


}
