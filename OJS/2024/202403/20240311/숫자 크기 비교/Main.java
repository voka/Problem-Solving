import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair>{
    int a; /// 몇 번째로 들어왔는지 
    int b; // 값 
    public Pair(int a, int b){
        this.a = a;
        this.b = b;
    }
    @Override
    public int compareTo(Pair o){
        if(this.b > o.b) return 1;
        else if(this.b < o.b) return -1;
        if(this.a > o.a) return 1;
        else if(this.a < o.a) return -1;
        return 0;
    }
}
public class Main {
    static int n,m,aIndex = 0;
    static List<List<Integer>> graph = new ArrayList<>();
    static int[] indegree;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        indegree = new int[n+1];
        for(int i=0;i<=n;++i){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph.get(a).add(b);
            indegree[b]++;
        }
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Pair> que = new PriorityQueue<>();
        for(int i=1;i<=n;++i){
            if(indegree[i] == 0) que.add(new Pair(0, i));
        }
        while(!que.isEmpty()){
            Pair cur = que.poll();
            for(int k : graph.get(cur.b)){
                indegree[k]--;
                if(indegree[k] == 0){
                    que.add(new Pair(cur.a+1, k));
                }
            }   
            sb.append(cur.b).append(" ");
        }
        // while(!ans.isEmpty()){
        //     sb.append(ans.poll().b).append(" ");
        // }
        System.out.println(sb.toString());
    }
}
