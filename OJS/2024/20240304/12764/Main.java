import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


class Pair implements Comparable<Pair>{
    int s;
    int e;
    public Pair(int s, int e){
        this.s = s;
        this.e = e;
    }
    @Override
    public int compareTo(Pair o){
        if(this.s > o.s) return 1;
        else if(this.s < o.s) return -1;
        return 0;
    }
}
class Triple implements Comparable<Triple>{
    int s;
    int e;
    int seat;
    public Triple(Pair o, int seat){
        this.s = o.s;
        this.e = o.e;
        this.seat = seat;
    }
    @Override
    public int compareTo(Triple o) {
        if(this.e > o.e) return 1;
        else if(this.e < o.e) return -1;
        return 0;
    }
}
public class Main {
    static int N, seat = 0;
    static PriorityQueue<Triple> pq = new PriorityQueue<>(); 
    static PriorityQueue<Integer> emptySeat= new PriorityQueue<>();
    static Pair[] memory;
    static int[] count, lastUseIndex;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        lastUseIndex = new int[N];
        count = new int[N];
        memory = new Pair[N];
        StringTokenizer st;
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            int P = Integer.parseInt(st.nextToken());
            int Q = Integer.parseInt(st.nextToken());
            memory[i] = new Pair(P, Q);
        }
        Arrays.sort(memory);
        for(int i=0;i<N;++i){
            Pair cur = memory[i];
            // 빈 자리 확보
            while(!pq.isEmpty() && pq.peek().e < cur.s){
                emptySeat.add(pq.poll().seat);
            }
            Triple next;
            if(!emptySeat.isEmpty()){
                next = new Triple(cur, emptySeat.poll());
            }
            else{// 앉을 자리가 없는 경우 자리 생성
                next = new Triple(cur, seat++);
            }
                
            count[next.seat]++;
            pq.add(next);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(seat).append('\n');
        for(int i=0;i<seat;++i){
           sb.append(count[i]).append(" ");
        }
        System.out.println(sb.toString());

    }    
}

/*
7
20 50
10 100
30 120
60 110
80 90
105 130
111 121


 *
2 2 1 2
 */