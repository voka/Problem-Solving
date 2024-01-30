import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


class Pair implements Comparable<Pair>{
    String a;
    int day;
    public Pair(String a, int day){
        this.a = a;
        this.day = day;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.day > o.day) return 1;
        else if(this.day < o.day) return -1;
        else{
            return this.a.compareTo(o.a);
        }
    }

}

class IPair{
    int id;
    int indegree;
    public IPair(int a, int b){
        id = a;
        indegree = b;
    }
    public void addIndegree(){
        this.indegree++;
    }

    public void minusIndegree(){
        this.indegree--;
    }
}
public class Main {
    static int N, count = 0;
    static List<List<Integer>> graph = new ArrayList<>();
    static Map<String,IPair> itemId = new HashMap<>();
    static Map<Integer,String> itemName = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            String a = st.nextToken();
            String b= st.nextToken();
            int aId, bId;
            if(!itemId.containsKey(a)) {
                itemId.put(a, new IPair(itemId.size(),0));
                itemName.put(itemId.get(a).id, a);
                graph.add(new ArrayList<>());
            }
            if(!itemId.containsKey(b)){
                itemId.put(b, new IPair(itemId.size(),0));
                itemName.put(itemId.get(b).id, b);
                graph.add(new ArrayList<>());
            }
            aId = itemId.get(a).id;
            bId = itemId.get(b).id;
            itemId.get(b).addIndegree();
            graph.get(aId).add(bId);
        }
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Pair> queue = new PriorityQueue<>();
        for(String key : itemId.keySet()){
            if(itemId.get(key).indegree == 0){
                queue.add(new Pair(key, 0));
            }
        }
        
        while(!queue.isEmpty()){
            Pair cur = queue.poll();
            IPair curPair = itemId.get(cur.a);
            count++;
            sb.append(cur.a).append('\n');
            for(int next : graph.get(curPair.id)){
                IPair nextPair = itemId.get(itemName.get(next));
                nextPair.minusIndegree();
                if(nextPair.indegree == 0){
                    queue.add(new Pair(itemName.get(next),cur.day+1));
                }
            }
        }
        //System.out.println(sb.toString());
        if(count != itemId.size()) System.out.println(-1);
        else System.out.println(sb.toString());
    }
}
