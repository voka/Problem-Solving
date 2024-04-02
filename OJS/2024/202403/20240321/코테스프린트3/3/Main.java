import java.io.*;
import java.util.*;
public class Main {
    static int n,m;
    static int[] indegree;
    static StringBuilder sb = new StringBuilder();
    static List<List<Integer>> graph = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int ans = 0;
        indegree = new int[n];
        for(int i=0;i<n;++i){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            indegree[y]++;
            graph.get(x).add(y);
        }
        Queue<Integer> my = new ArrayDeque<>();
        for(int i=0;i<n;++i){
            if(indegree[i] == 0){
                my.add(i);
            }
        }
        while(!my.isEmpty()){
            int cur = my.poll();
            sb.append(cur+1).append(' ');
            for(int next : graph.get(cur)){
                indegree[next]--;
                if(indegree[next] == 0){
                    my.add(next);
                }
            }
        }
        System.out.println(sb.toString());
    }
}