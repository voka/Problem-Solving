import java.util.PriorityQueue;

class Solution {
    public int solution(int[] ability, int number) {
        int answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int a : ability){
            pq.add(a);
        }
        for(int i=0;i<number;++i){
            int a = pq.poll();
            int b = pq.poll();
            pq.add(a + b);
            pq.add(a + b);
        }
        while(!pq.isEmpty()){
            answer += pq.poll();
        }
        return answer;
    }
}