import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

class myList{
    PriorityQueue<Integer> low;
    PriorityQueue<Integer> high;
    Set<Integer> del;
    public myList(){
        low = new PriorityQueue<>();
        high = new PriorityQueue<>();
        del = new HashSet<>();
    }
    public void put(int i){
        del.remove(i);
        low.add(i);
        high.add(-i);
    }
    public int getLow(){
        if(del.contains(low.peek())){
            low.poll();
        }
        return low.peek();
    }
    public int getHigh(){
        if(del.contains(-high.peek())){
            high.poll();
        }
        return -high.peek();
    }
    public void delete(int i){
        del.add(i);
    }
    
}
public class P21939 {
    static HashMap<Integer,myList> hash;
    static HashMap<Integer,Integer> dict;
    static myList level;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        level = new myList();
        StringTokenizer st;
        hash = new HashMap<>();
        dict = new HashMap<>();
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            int P = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());
            dict.put(P,L);
            put(P,L);
            level.put(L);
        }
        int M = Integer.parseInt(br.readLine());
        int p,l,a;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            switch (s) {
                case "add":
                    p = Integer.parseInt(st.nextToken());
                    l = Integer.parseInt(st.nextToken());
                    put(p,l);
                    dict.put(p,l);
                    level.put(l);
                    break;

                case "recommend":
                    a = Integer.parseInt(st.nextToken());
                    if(a == 1){
                        l = level.getHigh();
                        p = hash.get(l).getHigh();
                        sb.append(p).append('\n');
                    }else if(a == -1){
                        l = level.getLow();
                        p = hash.get(l).getLow();
                        sb.append(p).append('\n');
                    }
                    break;

                case "solved":
                    p = Integer.parseInt(st.nextToken());
                    l = dict.get(p);// level 찾기
                    hash.get(l).delete(p); //
                    int size = hash.get(l).high.size();
                    if(size == 1){ // 해당 레벨의 문제가 1개라면 
                        level.delete(l);
                    }
                    break;
            
                default:
                    break;
            }
        }
        System.out.println(sb.toString());
    }
    static void put(int p, int l){
        if(hash.containsKey(l)){
            hash.get(l).put(p);
        }else{
            myList my = new myList();
            my.put(p);
            hash.put(l,my);
        }

    }
}
