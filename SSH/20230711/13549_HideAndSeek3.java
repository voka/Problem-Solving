package ShortestPath;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
public class _13549_HideAndSeek3 {
    static int n,k;
    static int min = Integer.MAX_VALUE;
    static int[] visited = new int[100001];
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
        k = Integer.parseInt(st.nextToken());



        bfs();
        System.out.println(min);

    }
    public static void bfs(){
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(n,0));

        visited[n] = 1;

        while(!queue.isEmpty()){
            Node tmp = queue.poll();
            int i = tmp.end;

            visited[i] = 1;
            if( i == k){
                min = Math.min(min, tmp.cost);
            }


            int next;
            next = i*2;
            if(next >= 0 && next <= 100000 && (visited[next] == 0)){
                queue.add(new Node(next, tmp.cost));
            }
            next = i+1;
            if(next >= 0 && next <= 100000 && (visited[next] == 0)){
                queue.add(new Node(next, tmp.cost+1));
            }
            next = i-1;
            if(next >= 0 && next <= 100000 && (visited[next] == 0)){
                queue.add(new Node(next, tmp.cost+1));
            }
        }
    }
}
