import java.io.*;
import java.util.*;
public class P5567 {
    static int N;
    static ArrayList<ArrayList<Integer>> list;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());
        list = new ArrayList<>();
        for(int i=0;i<=N;++i){
            list.add(new ArrayList<>());
        }
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list.get(a).add(b);
            list.get(b).add(a);
        }
        int answer = 0;
        int[] can = canGo();
        for(int i=2;i<=N;++i){
            if(can[i] <= 2){
                answer++;
            }
        }

        System.out.println(answer);
    }
    static int[] canGo(){
        int[] dist = new int[N+1];
        for(int i=0;i<=N;++i){
            dist[i] = Integer.MAX_VALUE;
        }
        Queue<Integer> q = new ArrayDeque<>();
        q.add(1);//start => 1;
        dist[1] = 0;
        boolean[] check = new boolean[N+1];
        while(!q.isEmpty()){
            int cur = q.poll();
            for(int next : list.get(cur)){
                if(check[next]) continue;
                check[next] = true;
                dist[next] = dist[cur] + 1;
                q.add(next);
            }
        }
        return dist;
    }
}
