import java.io.*;
import java.util.*;
class Rabbit implements Comparable<Rabbit>{
    int idx;
    int x;
    int y;
    int sum;
    int jump;
    int pid;
    public Rabbit(int idx,int x, int y, int j, int id){
        this.idx = idx;
        this.x = x;
        this.y = y;
        this.sum = x+y;
        this.jump = j;
        this.pid = id;
    }
    @Override
    public int compareTo(Rabbit o){
        if(this.jump>o.jump) return 1;
        else if(this.jump < o.jump) return -1;
        if(this.sum>o.sum) return 1;
        else if(this.sum<o.sum) return -1;
        if(this.x>o.x) return 1;
        else if(this.x<o.x) return -1;
        if(this.y>o.y) return 1;
        else if(this.y<o.y) return -1;
        if(this.pid>o.pid) return 1;
        else if(this.pid<o.pid) return -1;
        return 0;
    }
}
class Point implements Comparable<Point>{
    int x;
    int y;
    int sum;
    int pid;
    public Point(int x, int y, int pid){
        this.x = x;
        this.y = y;
        this.sum = x+y;
        this.pid = pid;
    }
    @Override
    public int compareTo(Point o){
        if(this.sum<o.sum) return 1;
        else if(this.sum>o.sum) return -1;
        if(this.x<o.x) return 1;
        else if(this.x>o.x) return -1;
        if(this.y<o.y) return 1;
        else if(this.y>o.y) return -1;
        if(this.pid<o.pid) return 1;
        else if(this.pid>o.pid) return -1;
        return 0;
    }
}
public class Main {
    static int N, M, Q, P;
    static int[] x, y, pid;
    static long total = 0;
    static long[] dis,score;
    static int[] dx = {0,1,0,-1}, dy = {1,0,-1,0};
    static Map<Integer,Integer> hash = new HashMap<>();
    static PriorityQueue<Rabbit> pq = new PriorityQueue<>();
    public static int move(long moveDistance, int range){
        moveDistance = moveDistance %((range-1)*2);
        return moveDistance <= (range-1) ?  (int)moveDistance : (range-1) - (int)moveDistance % (range-1);
    }

    public static Point getNext(int x, int y, int i, long dis){
        int nx = x,ny = y;
        if(i == 0){ // 오른쪽인 경우
            ny = move(y + dy[i] * dis, M);
        } 
        if(i == 1){ // 아래쪽인 경우
            nx = move(x + dx[i] * dis, N);
        } 
        if(i == 2){  // 왼쪽인 경우
            ny = move(Math.abs(y + dy[i] * dis), M);
        }
        if(i == 3){ // 위쪽인 경우
            nx = move(Math.abs(x + dx[i] * dis), N);
        } 
        return new Point(nx, ny, 0);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Q = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        st.nextToken();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        x = new int[P];
        y = new int[P];
        pid = new int[P];
        dis = new long[P]; 
        score = new long[P];
        for(int i=0;i<P;++i){
            pid[i] = Integer.parseInt(st.nextToken());
            dis[i] = Long.parseLong(st.nextToken());
            pq.add(new Rabbit(i,0,0,0,pid[i]));
            hash.put(pid[i],i);
        }
        for(int q=0;q<Q-1;++q){
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            if(command == 200){
                int K = Integer.parseInt(st.nextToken());
                int S = Integer.parseInt(st.nextToken());
                race(K,S);
            }else if(command == 300){
                int pid_t = Integer.parseInt(st.nextToken());
                int L = Integer.parseInt(st.nextToken());
                changeDis(pid_t,L);
            }else if(command == 400){
                long ans = 0;
                for(long cur : score){
                    ans = Math.max(ans,cur+total);
                }
                System.out.println(ans);
            }
        }
    }
    static void race(int k, int s){
        boolean[] check = new boolean[P];
        PriorityQueue<Point> pq2 = new PriorityQueue<>();
        for(int i=0;i<k;++i){
            PriorityQueue<Point> pq3 = new PriorityQueue<>();
            Rabbit cur = pq.poll();
            check[cur.idx] = true; // 이번 경주에 뽑힘
            for(int d=0;d<4;++d){
                pq3.add(getNext(cur.x,cur.y,d,dis[cur.idx]));
            }
            Point go = pq3.poll();
            x[cur.idx] = go.x;
            y[cur.idx] = go.y;
            total += (go.x + go.y + 2);
            score[cur.idx] -= (go.x + go.y + 2);
            pq.add(new Rabbit(cur.idx,go.x,go.y,cur.jump+1,cur.pid));
            //printMap();
        }
        for(int i=0;i<P;++i){
            if(check[i]) pq2.add(new Point(x[i],y[i],pid[i]));
        }
        Point F = pq2.poll();
        score[hash.get(F.pid)] += s;
    }
    static void changeDis(int pid, int L){
        int idx = hash.get(pid);
        dis[idx] = dis[idx] * L;
    }
}
