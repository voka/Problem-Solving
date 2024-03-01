import java.util.*;
class Solution {
    public int solution(int[] menu, int[] order, int k) {
        int n = order.length;
        int answer = 0;
        int oIndex = 0;
        int time = 0;
        Queue<Integer> que = new LinkedList<>(); // 가게에 입장한 사람들 대기 큐
        while(!que.isEmpty() || oIndex < n){
            System.out.printf("Time => %d\n", time);
            if(oIndex < n){
                //입장시키기
                int cur = time + menu[order[oIndex++]];
                System.out.printf("IN -> %d\n",cur);
                que.add(cur);
                answer = Math.max(answer,que.size());
            }else{
                answer = Math.max(answer,que.size());
                break;
            }
            
            //시간 증가 시키기
            time += k;
            //첫번째 사람의 주문이 완료됐는지 확인
            if(que.peek() <= time){
                int out = que.poll(); // 주문 완료라면 퇴장 시키기
                System.out.printf("OUT -> %d\n",out);
            }
            
        }
        System.out.println(que.size());
        
        return answer;
    }
}