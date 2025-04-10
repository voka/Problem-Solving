import java.util.*;
import java.io.*;

class Point{
    int x;
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}

// class Turret implements Comparable<Turret>{
//     int x; 
//     int y;
//     int power;
//     public Turret

// }
public class Main {
    static int N,M,K;
    //우, 하, 좌, 상
    static int[] dx = {0,1,0,-1,1,1,-1,-1}, dy = {1,0,-1,0,1,-1,-1,1};
    static int[][] map,attack;

    // 포탑
    static int die, wx,wy,sx,sy;
    static boolean[][] status, visit;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        attack = new int[N][M];
        status = new boolean[N][M];
        die = 0;
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 0){
                    status[i][j] = true;
                    die++;
                }
            }
        }
       //printAllTurret();
        int TOTAL = N*M;
        for(int t=1;t<=K;++t){
            //System.out.printf("\n%d 번째, 죽은 터렛 수 : %d \n",t, die);
            if(die == TOTAL - 1) break;
            visit = new boolean[N][M];
            findWeakTurret();
            findStrongTurret();
            //System.out.printf("약한 곳 : (%d,%d), 강한 곳 : (%d,%d)\n", wx,wy,sx,sy);
            attack[wx][wy] = t; // 현재 턴에 공격하는 정보 업데이트
            map[wx][wy] += (N+M); // 공격력 증가
            //printAllTurret();
            attack();
            //printVisit();
            repair();
            //printAllTurret();
        }



        printStrongTurret();

    }
    static void printAllTurret(){
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void findWeakTurret(){
        int maxAttack = 5001;
        int maxX = -1;
        int maxY = -1;
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                int cur = map[i][j];
                if(cur == 0) continue; // 부서진 포탑 스킵
                if(maxAttack > cur){
                    maxAttack = cur;
                    maxX = i;
                    maxY = j;
                }else if(maxAttack == cur){
                    if(attack[i][j] > attack[maxX][maxY]){ // 공격한게 가장 최근이라면
                        maxX = i;
                        maxY = j;
                    }else if(attack[i][j] == attack[maxX][maxY] && (maxX + maxY) < (i + j)){
                        maxX = i;
                        maxY = j;
                    }else if(attack[i][j] == attack[maxX][maxY] && (maxX + maxY) == (i + j) && maxY < j){
                        maxX = i;
                        maxY = j;
                    }
                }
            }
        }
        wx = maxX;
        wy = maxY;
    }

    static void findStrongTurret(){
        int minAttack = -1;
        int minX = -1;
        int minY = -1;
        for(int i=N-1;i>=0;--i){
            for(int j=M-1;j>=0;--j){
                int cur = map[i][j];
                if(cur == 0) continue; // 부서진 포탑 스킵
                if(minAttack < cur){
                    minAttack = cur;
                    minX = i;
                    minY = j;
                }else if(minAttack == cur){
                    if(attack[i][j] < attack[minX][minY]){ // 공격한게 가장 오래됐다면
                        minX = i;
                        minY = j;
                    }else if(attack[i][j] == attack[minX][minY] && (minX + minY) > (i + j)){
                        minX = i;
                        minY = j;
                    }else if(attack[i][j] == attack[minX][minY] && (minX + minY) == (i + j) && minY > j){
                        minX = i;
                        minY = j;
                    }
                }
            }
        }
        sx = minX;
        sy = minY;
    }
    static void attack(){
        int dmg = map[wx][wy];
        visit[sx][sy] = true;
        visit[wx][wy] = true;
        if(!laser()){
            cannon();
        }
        if(map[sx][sy] - dmg <= 0){
            map[sx][sy] = 0;
            die++;
        }else map[sx][sy] -= dmg;
    }
    static boolean laser(){
        int[][] dist = new int[N][M];
        int MAX_VALUE = Integer.MAX_VALUE;
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                dist[i][j] = MAX_VALUE;// 초기화    
            }
        }
        Queue<Point> q = new ArrayDeque<>();
        q.add(new Point(wx,wy));
        dist[wx][wy] = 0;
        int[][] backX = new int[N][M];
        int[][] backY = new int[N][M];
        while(!q.isEmpty()){
            Point c = q.poll();
            if(c.x == sx && c.y == sy) break;
            for(int i=0;i<4;++i){
                int nx = (N + c.x + dx[i]) % N;
                int ny = (M + c.y + dy[i]) % M;
                if(map[nx][ny] == 0) continue;// 부서진 포탑은 못지나감
                if(dist[nx][ny] > dist[c.x][c.y] + 1){
                    dist[nx][ny] = dist[c.x][c.y] + 1;
                    backX[nx][ny] = c.x;
                    backY[nx][ny] = c.y;
                    q.add(new Point(nx,ny));
                }
            }
        }
        // for(int i=0;i<N;++i){
        //     for(int j=0;j<M;++j){
        //         System.out.printf("%d ",dist[i][j]);
        //     }
        //     System.out.println();
        // }
        if(dist[sx][sy] == MAX_VALUE){
            return false;// 최단경로 찾기 실패
        }else{
            //System.out.println("Lasor");
            int end = dist[sx][sy];
            int cx = backX[sx][sy];
            int cy = backY[sx][sy];
            int dmg = map[wx][wy] / 2;
            while(end > 1){
                end--;
                if(map[cx][cy] - dmg <= 0){
                    die++;
                    map[cx][cy] = 0;
                }else map[cx][cy] -= dmg;
                visit[cx][cy] = true;
                int nx = backX[cx][cy];
                int ny = backY[cx][cy];
                cx = nx;
                cy = ny;
            }
            //printVisit();
            return true;
        }
    }
    static void printVisit(){
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                System.out.printf("%b ", visit[i][j]);
            }System.out.println();
        }
    }
    static void cannon(){
        //System.out.println("Canon");
        int dmg = map[wx][wy] / 2;
        for(int i=0;i<8;++i){
            int nx = (N + sx + dx[i]) % N ;
            int ny = (M + sy + dy[i]) % M ;
            if(nx == wx && ny == wy) continue;
            if(map[nx][ny] == 0) continue;
            if(map[nx][ny] - dmg <= 0){
                die++;
                map[nx][ny] = 0;
            }else map[nx][ny] -= dmg;
            visit[nx][ny] = true;
        }
    }
    static void repair(){
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(!visit[i][j] && map[i][j] > 0){
                    map[i][j]++; 
                }
            }
        }
    }
    // for(int i=0;i<N;++i){
    //     for(int j=0;j<M;++j){
            
    //     }
    // }

    static void printStrongTurret(){
        int ans = -1;
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                if(!status[i][j]) ans = Math.max(ans,map[i][j]);
            }
        }
        System.out.println(ans);
    }


}
/**
4 4 1
0 1 4 4
8 0 10 13
8 0 11 26
0 0 0 0

4 4 1
0 1 1 1
8 0 10 13
8 1 11 13
0 0 1 13

4 4 1
6 8 0 1
0 0 0 8
0 0 0 0
0 0 8 0

4 4 1
1 1 1 1
1 1 1 1
1 1 1 1
1 1 1 1

10 6 10
3362 3908 4653 3746 4119 3669
4174 0 0 868 1062 854
633 51 759 0 4724 1474
2735 365 1750 3382 498 1672
141 3700 0 436 2752 974
3494 0 4719 2016 3870 0
3357 0 4652 3468 0 3758
4610 3125 0 2364 3303 1904
0 0 0 0 3959 3324
3187 0 105 2821 3642 160

 */