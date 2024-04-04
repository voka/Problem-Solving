import java.io.*;
import java.util.*;
public class Main {
    static int n,m,k;
    static List<Integer> x, y, mass, dir, speed;
    static int[][][] map;
    static int[] dx = {-1,-1,0,1,1,1,0,-1}, dy = {0,1,1,1,0,-1,-1,-1};
    public static void main(String[] args) throws IOException{
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        x = new ArrayList<>();
        y = new ArrayList<>();
        dir = new ArrayList<>();
        mass = new ArrayList<>();
        speed = new ArrayList<>();
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            x.add(Integer.parseInt(st.nextToken()));
            y.add(Integer.parseInt(st.nextToken()));
            mass.add(Integer.parseInt(st.nextToken()));
            speed.add(Integer.parseInt(st.nextToken()));
            dir.add(Integer.parseInt(st.nextToken()));
        }
        for(int i=0;i<k;++i){
            //System.out.printf("T : %d\n", i);
            solve();
            //printAll();
        }
        System.out.println(getTotal());
    }
    static int getTotal(){
        int re = 0;
        for(int i=0;i<mass.size();++i){
            re += mass.get(i);
        }
        return re;
    }
    static void printAll(){
        int size = x.size();
        for(int i=0;i<size;++i){
            int cx = x.get(i);
            int cy = y.get(i);
            int d = dir.get(i);
            int sp = speed.get(i);
            int ms = mass.get(i);
            System.out.printf("(%d, %d) , dir : %d, speed : %d, mass : %d\n", cx, cy, d, sp, ms);
        }
    }
    static void solve(){
        int size = x.size();
        boolean[][] check = new boolean[n][n];
        map = new int[n][n][6]; // 0 : 질량, 1: 속력, 2: 상하좌우 수, 3 : 대각선 수, 4: 방향, 5: 원자 개수
        for(int i=0;i<size;++i){
            int d = dir.get(i);
            int sp = speed.get(i);
            int ms = mass.get(i);
            int nx = (n*sp + x.get(i) + dx[d] * sp) % n;
            int ny = (n*sp + y.get(i) + dy[d] * sp) % n;
            //System.out.printf("%d, %d\n", nx,ny);
            map[nx][ny][0] += ms;
            map[nx][ny][1] += sp;
            if(d % 2 == 0){
                map[nx][ny][2]++;
            }else{
                map[nx][ny][3]++;
            }
            map[nx][ny][4] = d; 
            map[nx][ny][5]++;
            check[nx][ny] = true;
        }
        x.clear();
        y.clear();
        dir.clear();
        mass.clear();
        speed.clear();
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(check[i][j]){
                    if(map[i][j][5] >= 2){// 분열
                        int nmass = map[i][j][0] / 5;
                        if(nmass == 0) continue;
                        int nspeed = map[i][j][1] / map[i][j][5];
                        int s = 1;
                        if(map[i][j][2] == 0 || map[i][j][3] == 0){
                            s = 0;
                        }
                        for(int q=0+s;q<8;q=q+2){
                            x.add(i);
                            y.add(j);
                            mass.add(nmass);
                            dir.add(q);
                            speed.add(nspeed);
                        }
                    }else{
                        x.add(i);
                        y.add(j);
                        mass.add(map[i][j][0]);
                        speed.add(map[i][j][1]);
                        dir.add(map[i][j][4]);
                    }
                }
            }
        }
    }
}