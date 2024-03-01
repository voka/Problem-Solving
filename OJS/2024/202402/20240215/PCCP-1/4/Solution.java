import java.util.PriorityQueue;

class Triple implements Comparable<Triple>{
    int score; 
    int callTime;
    int exeTime;
    public Triple(int a, int b, int c){
        this.score = a;
        this.callTime = b;
        this.exeTime = c;
    }
    @Override
    public int compareTo(Triple o){
        if(this.callTime > o.callTime) return 1; 
        else if(this.callTime < o.callTime) return -1;
        return 0;

    }
}
class Triple2 implements Comparable<Triple2>{
    int score;
    int callTime;
    int exeTime;
    public Triple2(Triple t){
        this.score = t.score;
        this.callTime = t.callTime;
        this.exeTime = t.exeTime;
    }
    @Override
    public int compareTo(Triple2 o){
        if(this.score > o.score) return 1;
        else if(this.score < o.score) return -1;
        else{
            if(this.callTime > o.callTime) return 1; 
            else if(this.callTime < o.callTime) return -1;
            return 0;
        }

    }
}
class Solution {
    static int n;
    public long[] solution(int[][] program) {
        n = program.length;
        long[] answer = {0,0,0,0,0,0,0,0,0,0,0};
        PriorityQueue<Triple> pq = new PriorityQueue<>();
        PriorityQueue<Triple2> readyQueue = new PriorityQueue<>();
        for(int i=0;i<n;++i){
            pq.add(new Triple(program[i][0], program[i][1], program[i][2]));
        }
        long time = 0;
        while(!pq.isEmpty() || !readyQueue.isEmpty()){
            //현재 시간에 혹은 현재 시간보다 먼저 호출된 프로그램이 있는 경우 준비큐에 담는다.
            while(!pq.isEmpty() && pq.peek().callTime <= time){
                Triple cur = pq.poll();
                readyQueue.add(new Triple2(cur));
            }
            //끝난 프로그램과 다음에 호출할 프로그램 사이에 시간차가 있는 경우
            if(readyQueue.isEmpty() && !pq.isEmpty()){
                time = pq.peek().callTime;
            }else if(!readyQueue.isEmpty()) {
                // 준비 큐에 다음 실행할 프로그램이 있는 경우
                Triple2 exeProgram = readyQueue.poll();
                answer[exeProgram.score] += (time - exeProgram.callTime);
                time += exeProgram.exeTime;
            }
        }
        answer[0] = time;
        return answer;
    }
}