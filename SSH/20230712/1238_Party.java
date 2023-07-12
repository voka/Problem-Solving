package ShortestPath;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
public class _1238_Party {
    static int n,m,x;
    static int max = 0;
    static int[] time,cometime, visited;
    static ArrayList<ArrayList<Node>> list = new ArrayList<>();
    static class Node{
        int end, cost;
        Node(int end, int cost){
            this.end = end;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        time = new int[n+1];
        visited = new int[n+1];
        for(int i=0;i<=n;i++){
            list.add(new ArrayList<Node>());
            time[i] = Integer.MAX_VALUE;
        }

        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            list.get(a).add(new Node(b,t));
        }

        dijkstra(x);
        cometime = time.clone();
        for(int i=1;i<=n;i++) {
            for(int j=0;j<=n;j++)
                time[j] = Integer.MAX_VALUE;
            visited = new int[n+1];
            dijkstra(i);
            max = Math.max(max, time[x]+cometime[i]);
        }
        System.out.println(max);
    }
    public static void dijkstra(int start){
        PriorityQueue<Node> pqueue = new PriorityQueue<>(new Comparator<Node>(){
            @Override
            public int compare(Node o1, Node o2){return o1.cost - o2.cost;}
        });
        pqueue.add(new Node(start,0));
        time[start]=0;

        while(!pqueue.isEmpty()){
            Node tmp = pqueue.poll();
            int cur = tmp.end;
            if(visited[cur] == 1) continue;
            visited[cur] = 1;

            for(Node node : list.get(cur)){
                if(time[node.end] > time[cur]+node.cost) {
                    time[node.end] = time[cur] + node.cost;
                    pqueue.add(new Node(node.end,time[node.end]));
                }
            }
        }
    }
}
