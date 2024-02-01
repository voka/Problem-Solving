import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

class Point{
    int x;
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return String.format("x : %d,  y : %d", x,y);
    }
}

public class Main {
    static int R, C;
    static int [][] water, maps, dp;
    static int [] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static boolean rangeCheck(Point p){
        return (p.x >= 0) && (p.y >= 0) && (p.x < C) && (p.y < R); 
    }
    static boolean mapCheck(Point p){ //물 채울때 사용
        return (maps[p.y][p.x] != -1);// 돌이 아니면 true 반환
    }
    static void printArr(int[][] array){
        System.out.println();
        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                System.out.printf("%d ", array[i][j]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        StringTokenizer st = new StringTokenizer(br.readLine()); 
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        water = new int[R][C];
        maps = new int[R][C];
        dp = new int[R][C];
        List<Point> initWaters = new ArrayList<>();
        for(int i=0;i<R;++i){ // init 
            for(int j=0;j<C;++j){
                dp[i][j] = Integer.MAX_VALUE;
                water[i][j] = -1;
            }
        }
        int sx=0,sy=0,ex=0,ey=0;
        for(int i=0;i<R;++i){
            String temp = br.readLine().strip();
            for(int j=0;j<C;++j){
                switch (temp.charAt(j)) {
                    case 'D':
                        water[i][j] = 7777;
                        maps[i][j] = 7;
                        ex = j;
                        ey = i;
                        break;
                    case 'S':
                        maps[i][j] = 1;
                        water[i][j] = 7777;
                        dp[i][j] = 0;
                        sx = j;
                        sy = i;
                        break;
                    case '.':
                        maps[i][j] = 0;
                        break;
                    case 'X':
                        maps[i][j] = -1;
                        break;
                    case '*':
                        water[i][j] = 0;
                        initWaters.add(new Point(j, i));
                        break;
                    default:
                        break;
                }
            }
        }
        Queue<Point> myque = new LinkedList<>();
        for(int i=0;i<initWaters.size();++i){
            myque.add(initWaters.get(i));
        }
        //물 채우기
        while (!myque.isEmpty()) {
            Point cur = myque.poll() ;
            for(int i=0;i<4;++i){
                Point np = new Point(cur.x + dx[i], cur.y + dy[i]);
                if(rangeCheck(np) && mapCheck(np) && water[np.y][np.x] == -1 ){
                    water[np.y][np.x] = water[cur.y][cur.x] + 1;
                    myque.add(np);
                }
            }
            //printArr(water);
        }
        myque.add(new Point(sx,sy));
        while(!myque.isEmpty()){
            Point cur = myque.poll() ;
            //System.out.printf("cur -> %s\n", cur.toString());
            if((cur.x == ex) && (cur.y == ey)) break; 
            for(int i=0;i<4;++i){
                Point np = new Point(cur.x + dx[i], cur.y + dy[i]);
                //System.out.printf("next -> %s\n", np.toString());
                // 범위 and 돌  and (현재 시간보다 늦게 물이 차는 칸 || 물이 차지않는 칸) and 현재까지 구한 최소 시간 보다 더 빠른 시간인 경우 
                if(rangeCheck(np) && mapCheck(np) && ((water[np.y][np.x] > dp[cur.y][cur.x] + 1) || water[np.y][np.x] == -1) && (dp[np.y][np.x] > dp[cur.y][cur.x] + 1)){
                    dp[np.y][np.x] = dp[cur.y][cur.x] + 1;
                    myque.add(np);
                }
            }
            //printArr(dp);
        }
        if(dp[ey][ex] == Integer.MAX_VALUE) System.out.println("KAKTUS");
        else System.out.println(dp[ey][ex]);

    }
}
