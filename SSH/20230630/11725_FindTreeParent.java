package Graph;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
public class _11725_FindTreeParent {

    static int n;
    static int[] parent;
    static int[] visited;
    static ArrayList<ArrayList<Integer>> list = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        parent = new int[n+1];
        visited = new int[n+1];
        for(int i=0;i<=n;i++){
            list.add(new ArrayList<Integer>());
        }
        parent[1] = 1;
        for(int i=0;i<n-1;i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            list.get(x).add(y);
            list.get(y).add(x);
        }
        for(int i=1;i<=n;i++){
            if(visited[i] == 0){
                bfs(i);
            }
        }
        for(int i=2;i<=n;i++){
            System.out.println(parent[i]);
        }
    }

    public static void bfs(int start){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        while(!queue.isEmpty()){
            int t = queue.poll();
            if(visited[t] == 0){
                visited[t] = 1;
                for(int i : list.get(t)){
                    if(visited[i] == 0){
                        parent[i] = t;
                        queue.add(i);
                    }
                }

            }

        }
    }

}
