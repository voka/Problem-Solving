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
    int indegree;
    public Pair(String a, int indegree){
        this.a = a;
        this.indegree = indegree;
    }
    @Override
    public int compareTo(Pair o) {
        if(this.indegree > o.indegree) return 1;
        else if(this.indegree < o.indegree) return -1;
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
    static List<List<Integer>> graph = new ArrayList<>();// 아이템들의 연관관계를 저장하는 인접 리스트
    static Map<String,IPair> itemId = new HashMap<>(); // <이름 , <graph Index, 진입차수>>
    static Map<Integer,String> itemName = new HashMap<>(); // <graph Index, 이룸>
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            String a = st.nextToken();
            String b= st.nextToken();
            int aId, bId;
            if(!itemId.containsKey(a)) { // 처음 나온 아이템만 저장
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
            graph.get(aId).add(bId);
            // b의 진입차수 증가시키기 (a를 사야지 b를 살 수 있으므로)
            itemId.get(b).addIndegree(); 
        }
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Pair> queue = new PriorityQueue<>();
         // 가장 처음에는 진입차수 0인것 부터 저장ㄴ
        for(String key : itemId.keySet()){
            if(itemId.get(key).indegree == 0){
                queue.add(new Pair(key, 0));
            }
        }
        
        while(!queue.isEmpty()){
            Pair cur = queue.poll();
            IPair curPair = itemId.get(cur.a);
            count++;
            sb.append(cur.a).append('\n');// 출력할 아이템 이름 저장
            for(int next : graph.get(curPair.id)){
                IPair nextPair = itemId.get(itemName.get(next));
                 // 이번 아이템을 구매하면서 연관된 아이템들의 진입차수를 1 낮춰줌.ㄴ
                nextPair.minusIndegree();
                if(nextPair.indegree == 0){
                    // 이번에 큐에 들어가는 아이템은 이전 진입차수 + 1이 됨.
                    queue.add(new Pair(itemName.get(next),cur.indegree+1)); 
                }
            }
        }
        if(count != itemId.size()) System.out.println(-1);
        else System.out.println(sb.toString());
    }
}
