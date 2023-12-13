import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;

class Rabbit implements Comparable<Rabbit>{
    public int x;
    public int y;
    public int jump;
    public long pid;
    public Rabbit(int x, int y, int jump, long pid){
        this.x = x;
        this.y = y;
        this.jump = jump;
        this.pid = pid;
    }
    @Override
    public int compareTo(Rabbit rabbit){
        if(this.jump > rabbit.jump){
            return 1;
        }
        else if(this.jump < rabbit.jump){
            return -1;
        }
        if(this.x + this.y > rabbit.x + rabbit.y){
            return 1;
        }
        else if(this.x + this.y < rabbit.x + rabbit.y){
            return -1;
        }
        if(this.y > rabbit.y){
            return 1; 
        }
        else if(this.y < rabbit.y){
            return -1; 
        }
        if(this.x > rabbit.x){
            return 1; 
        }
        else if(this.x < rabbit.x){
            return -1;
        }
        if(this.pid > rabbit.pid){
            return 1;
        }
        else{
            return -1;
        }
    }
}

class Point implements Comparable<Point>{
    public int x;
    public int y;
    public long pid;
    public Point(int x, int y, long pid){
        this.x = x;
        this.y = y;
        this.pid = pid;
    }
    @Override
    public int compareTo(Point point){
        if(this.x + this.y < point.x + point.y){
            return 1;
        }
        else if(this.x + this.y > point.x + point.y){
            return -1;
        }
        if(this.y < point.y){
            return 1; 
        }
        else if(this.y > point.y){
            return -1; 
        }
        if(this.x < point.x){
            return 1; 
        }
        else if(this.x > point.x){
            return -1;
        }
        if(this.pid < point.pid){
            return 1;
        }
        else{
            return -1;
        }
    }
}


public class Main {
    static int Q,N,M,P;
    static long totalSum = 0;
    static int [] x; // 토끼 현재 x 좌표;
    static int [] y; // 현재 y 좌표; 
    static long [] score;// i 번째 토끼의 점수; 
    static boolean [] flag;// 이번턴에 뽑혔는지 검사하는 배열;
    static long [] rdis; // 고유 이동 거리
    static long [] rpid; // 고유 번호
    // 이동방향 -> 오른쪽, 밑쪽, 왼쪽, 위쪽
    static int[] dx = {1,0,-1,0};
    static int[] dy = {0,1,0,-1};
    static PriorityQueue<Rabbit> pq = new PriorityQueue<>();
    static Map<Long,Integer> idMap = new HashMap<>();

    // x,y 는 무조건 0 부터 시작
    // public static int moveX(long move){
    //     move = move % ((M-1)*2); // 왕복하는 거리를 제외한 이동거리
    //     if(move <= M-1){ // 진행 방향
    //         return (int)move;
    //     }
    //     else{ // 돌아오는 방향
    //         return (M-1) - (int)move%(M-1);
    //     }
    // }
    // public static int moveY(long move){
    //     move = move % ((N-1)*2);
    //     if(move <= N-1){
    //         return (int)move;
    //     }
    //     else{ // 돌아오는 방향
    //         return (N-1) - (int)move%(N-1);
    //     }
    // }
    public static int move(long moveDistance, int range){
        moveDistance = moveDistance %((range-1)*2);
        return moveDistance <= (range-1) ?  (int)moveDistance : (range-1) - (int)moveDistance % (range-1);
    }

    public static Point getNextDestination(int x, int y, int i, long dis){
        int nx = x,ny = y;
        if(i == 0){ // 오른쪽인 경우
            nx = move(x + dx[i] * dis, M);
        } 
        if(i == 1){ // 아래쪽인 경우
            ny = move(y + dy[i] * dis, N);
        } 
        if(i == 2){  // 왼쪽인 경우
            // 오른쪽으로 이동하는 지점까지 이동 후 작동
            nx = move(Math.abs(x + dx[i] * dis), M);
        }
        if(i == 3){ // 아래쪽인 경우
            ny = move(Math.abs(y + dy[i] * dis), N);
        } 
        //System.out.printf("nx : %d, ny : %d, dis : %d\n",nx,ny,dis);
        return new Point(nx, ny, 0);
    }
    public static void process(int k, int best){
        PriorityQueue<Point> seleted = new PriorityQueue<>();
        flag = new boolean[P+1];
        for(int i=0;i<k;++i){
            Rabbit cur = pq.poll();
            int myid = idMap.get(cur.pid);
            PriorityQueue<Point> myPoint = new PriorityQueue<>();
            for(int j=0;j<4;++j){
                Point next = getNextDestination(x[myid],y[myid],j,rdis[myid]);
                myPoint.add(next);
            }
            Point dest = myPoint.poll();
            pq.add(new Rabbit(dest.x, dest.y, cur.jump+1, cur.pid));
            score[myid] -= (dest.x + dest.y + 2);
            totalSum += (dest.x + dest.y + 2);
            flag[myid] = true;
            // 배열 업데이트
            x[myid] = dest.x;
            y[myid] = dest.y;
            //printRabbit();
        }
        // 다 끝난 뒤 우선순위가 가장 높은 토끼를 뽑는다. 
        // 주의! 이번턴에 한 번이라도 뽑힌 토끼가 뽑혀야 한다.
        for(int j=0;j<P;++j){
            if(flag[j]){
                seleted.add(new Point(x[j], y[j], rpid[j]));
            }
        }
        Point curBest = seleted.poll();
        score[idMap.get(curBest.pid)] += best; // 최고한테 점수 더해주기
        //printRabbit();
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Q = Integer.parseInt(br.readLine());
        for(int q = 0; q<Q;++q){
            String[] command = br.readLine().split(" ");
            switch (Integer.parseInt(command[0])) {
                case 100:
                    N = Integer.parseInt(command[1]);
                    M = Integer.parseInt(command[2]);
                    P = Integer.parseInt(command[3]);
                    x = new int[P+1];
                    y = new int[P+1];
                    score = new long[P+1]; // 초기값 0 
                    rdis = new long[P+1]; 
                    rpid = new long[P+1]; 
                    for(int i=0;i<P;++i){
                        long pid = Long.parseLong(command[4+i*2]);
                        long dis = Long.parseLong(command[5+i*2]);
                        rpid[i] = pid;
                        idMap.put(pid,i);
                        rdis[i] = dis;
                        x[i] = 0; 
                        y[i] = 0;
                        //우선순위 큐 삽입
                        pq.add(new Rabbit(0, 0, 0, pid ));
                    }
                    break;
                case 200:

                    //printRabbit();
                    int k = Integer.parseInt(command[1]);
                    int bestScore = Integer.parseInt(command[2]);
                    process(k, bestScore);
                    break;
                
                case 300:
                    long pid = Long.parseLong(command[1]);
                    int L = Integer.parseInt(command[2]);
                    int idx = idMap.get(pid);
                    rdis[idx] = L * rdis[idx]; // L 배 증가시켜 준다.
                    break;
                case 400:
                    long answer = 0; 
                    for(int i=0;i<P;++i){
                        answer = Math.max(answer,score[i] + totalSum);
                    }
                    System.out.println(answer);
                    break;
                default:
                    
                    break;
            }
        }

    }

    public static void printRabbit(){
        long[][] map = new long[N][M];
        System.out.println("start ===================");
        for(int i=0;i<P;++i){
            map[y[i]][x[i]] = rpid[i];
            System.out.printf("pid : %d, x : %d, y : %d, score : %d, dis : %d\n", rpid[i],x[i],y[i],score[i],rdis[i]);
        }
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
    }

}
