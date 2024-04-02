import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n,m,k;
    static int[][] map;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<m;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //(0,0)에서 시작 ~ dx > 1 반복
        for(int q=0;q<k;++q){
            //System.out.printf("rotate : %d\n",q+1);
            int dx = n-1; // 상하 진행 수
            int dy = m-1; // 좌우 진행 수
            int sx = 0;
            int sy = 0; 
            while(true){

                int f = map[sx][sy];
                // 윗줄
                for(int i=0;i<dy;++i){
                    map[sx][sy] = map[sx][sy+1];
                    sy++;
                }
                //오른쪽
                for(int i=0;i<dx;++i){
                    map[sx][sy] = map[sx+1][sy];
                    sx++;
                }
                //밑쪽
                for(int i=0;i<dy;++i){
                    map[sx][sy] = map[sx][sy-1];
                    sy--;
                }
                //왼쪽
                for(int i=0;i<dx-1;++i){
                    map[sx][sy] = map[sx-1][sy];
                    sx--;
                }
                // 마지막 한칸
                map[sx][sy] = f;
                dx -= 2;
                dy -= 2;
                sy++;
                if(dx < 1 || dy < 1) break;
                //System.out.printf("%d, %d", dx, dy);
            }
            //printArr(map);
        }
        printArr(map);
        
        

    }
    static void printArr(int[][] temp){
        for(int i=0;i<n;++i){
            for(int j=0;j<m;++j){
                System.out.printf("%d ", temp[i][j]);
            }
            System.out.println();
        }
    }
}

/**
 * 
3 5 1
1 2 3 4 5
6 7 8 9 10
11 12 13 14 15
 * 
 */