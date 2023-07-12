package ShortestPath;
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
public class _1504_PeculiarSP {
    static int n,e,start,end,v1,v2,res1,res2;
    static int[] arr;
    static int[] dist;
    static boolean[] visited;
    static boolean flag= true;
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
        e = Integer.parseInt(st.nextToken());
        start = 1;
        end = n;
        dist = new int[n+1];
        visited = new boolean[n+1];
        for(int i=0;i<=n;i++){
            list.add(new ArrayList<Node>());
            dist[i] = Integer.MAX_VALUE;
        }
        for(int i=0;i<e;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            list.get(a).add(new Node(b,c));
            list.get(b).add(new Node(a,c));
        }
        st = new StringTokenizer(br.readLine());
        v1 = Integer.parseInt(st.nextToken());
        v2 = Integer.parseInt(st.nextToken());


        res1 += dijkstra(1,v1);
        res1 += dijkstra(v1,v2);
        res1 += dijkstra(v2,n);
        if(flag == false)
            res1 = -1;

        flag = true;
        res2 += dijkstra(1,v2);
        res2 += dijkstra(v2,v1);
        res2 += dijkstra(v1,n);
        if(flag == false)
            res2 = -1;

        if(res1 != -1 && res2 != -1)
            System.out.println(Math.min(res1, res2));
        else
            System.out.println(Math.max(res1, res2));
        


    }
    public static int dijkstra(int start, int end){
        PriorityQueue<Node> pqueue = new PriorityQueue<>(new Comparator<Node>(){
            @Override
            public int compare(Node o1, Node o2){return o1.cost - o2.cost;}
        });
        dist = new int[n+1];
        visited = new boolean[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        pqueue.add(new Node(start,0));
        dist[start] = 0;

        while(!pqueue.isEmpty()){
            Node tmp = pqueue.poll();
            int cur = tmp.end;

            if(visited[cur] == true) continue;
            visited[cur] = true;

            for(Node node : list.get(cur)){
                if(dist[node.end] > dist[cur] + node.cost){
                    dist[node.end] = dist[cur] + node.cost;
                    pqueue.add(new Node(node.end,dist[node.end]));
                }
            }

        }
        if(dist[end] == Integer.MAX_VALUE)
            flag = false;
        return dist[end];
    }
}
