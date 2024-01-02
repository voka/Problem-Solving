import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

class Point{
    int x;
    int y;
    int count;
    public Point(int x, int y, int count){
        this.x = x;
        this.y = y;
        this.count = count;
    }
}

public class CodeTreeBread {
    static int n,m,bnum = 0, answer = -1;
    static int[][] map;
    static int[] storeX, storeY;
    static boolean[] base, people;
    static int[] baseX, baseY;
    static int[] peopleX, peopleY;
    static int[] dx = {0,-1,1,0};
    static int[] dy = {-1,0,0,1};
    static int[][] minDistance;
    static boolean[][] visited;
    static Queue<Point> myQueue = new LinkedList<>();
    public static boolean canMove(int x, int y){
        return inRange(x, y) && isNotLock(x, y);
    }

    public static boolean inRange(int x, int y){
        return (x >= 0) && (y >= 0) && (x < n) && (y < n);
    }
    public static boolean isNotLock(int x, int y){
        return map[y][x] != -1;
    }
    public static void initMaxDistance(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                minDistance[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    public static void getAllDistancefromXY(int x, int y){
        initMaxDistance();
        minDistance[y][x] = 0;
        myQueue.add(new Point(x, y, 0));
        while(!myQueue.isEmpty()){
            Point cur = myQueue.poll();
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(canMove(nx, ny) && (minDistance[ny][nx] > (cur.count + 1)) ){
                    minDistance[ny][nx] = cur.count + 1;
                    myQueue.offer(new Point(nx, ny, cur.count + 1));
                }
            }
        }
    }
    public static void selectBase(int t){
        int minI = -1, minDis = Integer.MAX_VALUE;
        getAllDistancefromXY(storeX[t], storeY[t]);
        for(int i=0;i<bnum;++i){
            if(!base[i]){
                int cur = minDistance[baseY[i]][baseX[i]] ;
                if(minDis > cur){
                    minDis = cur;
                    minI = i;
                }
            }
        }
        base[minI] = true;
        peopleX[t] = baseX[minI];
        peopleY[t] = baseY[minI];
        map[baseY[minI]][baseX[minI]] = -1;
    }

    public static void move(int t){
        int cx = peopleX[t];
        int cy = peopleY[t];
        int nnx = -1, nny = -1, minDis = Integer.MAX_VALUE;
        getAllDistancefromXY(storeX[t], storeY[t]);
        for(int i=0;i<4;++i){
            int nx = cx + dx[i];
            int ny = cy + dy[i];
            if(canMove(nx, ny)){
                if( minDistance[ny][nx] == 0){
                    people[t] = true; 
                    peopleX[t] = nx;
                    peopleY[t] = ny;
                    return;
                }
                else if(minDis > minDistance[ny][nx]){
                    minDis = minDistance[ny][nx];
                    nny = ny;
                    nnx = nx;
                }
            }
        }
        peopleX[t] = nnx;
        peopleY[t] = nny;
    }

    public static void lockStore(int t){
        int max = Math.min(t, m);
        for(int i=0;i<max;++i){
            if(people[i]){
                map[peopleY[i]][peopleX[i]] = -1;
            }
        }
    }
    public static boolean moveAll(int t){
        int cnt = 0;
        int max = Math.min(t, m);
        for(int i=0;i<max;++i){
            if(!people[i]){
                cnt += 1;
                move(i);
            }
        }
        return cnt == 0;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm = br.readLine().split(" ");
        n = Integer.parseInt(nm[0]);
        m = Integer.parseInt(nm[1]);
        map = new int[n][n];
        storeX = new int[m];
        storeY = new int[m];
        baseX = new int[n*n - m];
        baseY = new int[n*n - m];
        base = new boolean[n*n - m];
        people = new boolean[m];
        peopleX = new int[m];
        peopleY = new int[m];
        minDistance = new int[n][n];
        for(int i=0;i<n;++i){
            String[] temp = br.readLine().split(" ");
            for(int j=0;j<n;++j){
                map[i][j] = Integer.parseInt(temp[j]);
                if(map[i][j] == 1){
                    baseY[bnum] = i;
                    baseX[bnum++] = j;
                } 
            }
        }
        for(int i=0;i<m;++i){
            String[] temp = br.readLine().split(" ");
            storeY[i] = Integer.parseInt(temp[0]) - 1;
            storeX[i] = Integer.parseInt(temp[1]) - 1;
        }
        int time = 1;
        while(true){
            lockStore(time);
            if(time <= m) selectBase(time-1);
            if(moveAll(time)){
                break;
            }
            time++;
        }
        System.out.println(time);

    }


    public static void printPeopleMap(int t){
        t = Math.min(t,m);
        int[][] k = new int[n][n];
        for(int i=0;i<t;++i){
            k[peopleY[i]][peopleX[i]] = i + 1;
        }
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ",k[i][j]);
            }
            System.out.println();
        }
    }

    public static void print2Arr(int[][] arr){
        for(int i=0;i<arr.length;++i){
            for(int j=0;j<arr[i].length;++j){
                System.out.printf("%d ",arr[i][j]);
            }
            System.out.println();
        }
    }
}