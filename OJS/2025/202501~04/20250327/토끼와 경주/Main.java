import java.io.*;
import java.util.*;

class Rabbit implements Comparable<Rabbit>{
    int pid;
    int dis;
    int jump;
    int x;
    int y;
    public Rabbit(int pid){
        this.pid = pid;
        this.jump = 0;
        this.x = 1;
        this.y = 1;

    }
    @Override
    public int compareTo(Rabbit r){
        if(this.jump != r.jump){
            return this.jump - r.jump;
        }else if(sumxy() != r.sumxy()){
            return sumxy() - r.sumxy();
        }else if(this.x!=r.x){
            return this.x - r.x;
        }else if(this.y!=r.y){
            return this.y - r.y;
        }else {
            return this.pid - r.pid;
        }

    }
    @Override
    public String toString(){
        return String.format("pid : %d, jump : %d, x : %d, y : %d", pid, jump, x, y);
    }
    public int sumxy(){
        return this.x + this.y;
    }
}
public class Main{
    static int N,M,P,Q;
    static long total;
    static int[] rax, ray, rid;
    static long[] score,dis;
    static int[] dx = {-1,0,1,0}, dy = {0,-1,0,1};


    static PriorityQueue<Rabbit> pq = new PriorityQueue<>();
    static Map<Integer,Integer> dic = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //test();
        total = 0;
        Q = Integer.parseInt(br.readLine());
        for(int i=0;i<Q;++i){
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 명령
            int com = Integer.parseInt(st.nextToken());
            if(com == 100){
                N = Integer.parseInt(st.nextToken());
                M = Integer.parseInt(st.nextToken());
                P = Integer.parseInt(st.nextToken());
                dis = new long[P];
                rax = new int[P];
                ray = new int[P];
                rid = new int[P];
                score = new long[P];
                for(int j=0;j<P;++j){
                    int pid = Integer.parseInt(st.nextToken());
                    int d = Integer.parseInt(st.nextToken());
                    dis[j] = d;
                    dic.put(pid, j);
                    rid[j] = pid;
                    pq.add(new Rabbit(pid));
                }
            }else if(com == 200){
                int K = Integer.parseInt(st.nextToken());
                int S = Integer.parseInt(st.nextToken());
                run(K,S);
            }else if(com == 300){
                int pid = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                int index = dic.get(pid);
                dis[index] *= d;
            }else{
                pickBestRabbit();
            }
        }
    }
    static void run(int K, int S){
        boolean[] visit = new boolean[P];
        for(int i=0;i<K;++i){
            Rabbit r = pq.poll();
            int index = dic.get(r.pid);
            visit[index] = true;
            long d = dis[index];
            int rx = 0;
            int ry = 0;
            for(int j=0;j<4;++j){
                 // 한 바퀴 도는 개수들 제거
                int nx = r.x;
                int ny = r.y;
                // 이동할 방향 설정
                if((j+1)%2 == 1){ // 위, 아래일 경우
                    int range = N-1;
                    long total = Math.abs(r.x + dx[j] * d);
                    total = total % (range*2);
                    if(total <= range + 1){
                        nx = (int)total;
                    }else{
                        nx = range - (int)total % range;
                    }
                }else{
                    int range = M-1;
                    long total = Math.abs(r.y + dy[j] * d);
                    total = total % (range*2);
                    if(total <= range + 1){
                        ny = (int)total;
                    }else{
                        ny = range - (int)total % range;
                    }
                }

                if((rx + ry) < (nx + ny)){
                    rx = nx;
                    ry = ny;
                }else if((rx + ry) == (nx + ny)){
                    if(rx < nx){
                        rx = nx;
                        ry = ny;
                    }else if(rx == nx){
                        if(ry < ny){
                            rx = nx;
                            ry = ny;
                        }
                    }
                }

            }
            //System.out.printf("\n%d번 토끼 (%d,%d) -> (%d,%d)\n", r.pid, r.x, r.y, rx,ry);
            // 최종 위치로 이동
            r.x = rx;
            r.y = ry;
            rax[index] = rx; 
            ray[index] = ry; 
            // 점프 횟수 증가
            r.jump++;
            total += (rx + ry);
            score[index] -= (rx + ry);
            pq.add(r);
        }
        PriorityQueue<Rabbit> tpq = new PriorityQueue<>(); 
        for(int i=0;i<P;++i){
            if(visit[i]){
                Rabbit r = new Rabbit(-rid[i]);
                r.x = -rax[i];
                r.y = -ray[i];
                tpq.add(r);
            }
        }
        Rabbit r = tpq.poll();
        int id = dic.get(-r.pid);
        score[id] += S;
    }
    static boolean checkRangeX(int x){
        return (x>=1) && (x<=N);
    }
    static boolean checkRangeY(int y){
        return (y>=1) && (y<=M);
    }
    static void pickBestRabbit(){
        long max = 0;
        for(int i=0;i<P;++i){
            max = Math.max(score[i] + total,max);
        }
        System.out.println(max);
    }
    
}