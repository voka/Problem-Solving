import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int R,C,N,time = 1;
    static int[][] map;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken()) + 1;
        map = new int[R][C];
        for(int i=0;i<R;++i){
            String temp = br.readLine();
            for(int j=0;j<C;++j){
                char c = temp.charAt(j);
                if(c == 'O') map[i][j] = time; // 폭탄이 심어진 시간
                else map[i][j] = 0; // 폭탄이 없는 곳
            }
        }
        time++;
        //System.out.printf("Nothing to do : %d\n", time);
        //printMaptoInt();
        while(true){
            if(time == N) break;
            time++;
            //System.out.printf("makeBoom : %d\n", time);
            process3();
            //printMaptoInt();
            if(time == N) break;
            time++;
            //System.out.printf("BOOMMMMM!! : %d\n", time);
            process4();
            //printMaptoInt();
        }
        printMap();
    }
    static void process3(){
        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                if(map[i][j] == 0){
                    map[i][j] = time;
                }
            }
        }
    }
    static void process4(){
        int[][] next = new int[R][C];

        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                next[i][j] = map[i][j];
            }
        }
        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                if(map[i][j] + 3 == time){
                    next[i][j] = 0;
                    delete(i, j, next);
                }
            }
        }
        map = next;
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<R) && (y<C);
    }
    static void delete(int x, int y, int[][] next){
        for(int i=0;i<4;++i){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(checkRange(nx, ny)){
                next[nx][ny] = 0;
            }
        }
    }
    static void printMap(){
        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                if(map[i][j] == 0){
                    System.out.print('.');
                }else{
                    System.out.print('O');
                }
            }
            System.out.println();
        }
    }
    static void printMaptoInt(){
        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
    }
}
