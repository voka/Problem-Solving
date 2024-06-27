import java.io.*;
import java.util.*;
public class Main {
    static int n;
    static long total;
    static int[] dx = {0,1,0,-1}, dy = {-1,0,1,0};
    static int[][] map, temp;
    public static void main(String[] args) throws IOException{
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        map = new int[n][n];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        total = getTotal();
        clearMap(n/2, n/2, 1, 0, 0, 1);
        //clear(n/2,n/2-2,0);
        //clear(n/2+1,n/2-1,1);
        //clear(n/2+1,n/2,2);
        //printMap(map);
        long last = getTotal();
        System.out.println(total - last);
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<n) && (y<n);
    }
    static void clearMap(int x, int y, int l, int cnt, int d, int num){
        int nx = x, ny = y;
        cnt++;
        for(int i=0;i<l;++i){
            nx += dx[d];
            ny += dy[d];
            clear(nx,ny,d);
        }
        if(l == n-1){
            if(cnt == 3) return;
            clearMap(nx, ny, l, cnt, (d+1)%4, num);
        }else if(cnt == 2){
            clearMap(nx, ny, l+1, 0, (d+1)%4, num);    
        }else{
            clearMap(nx, ny, l, cnt, (d+1)%4, num);
        }

    }
    static void clear(int x, int y, int d){
        int cur = map[x][y];
        if(cur == 0) return; 
        int one = cur / 100;
        int two = cur / 50;
        int seven = cur*7 / 100;
        int ten = cur / 10;
        int five = cur / 20;
        int a = cur - (one * 2 + two * 2 + seven * 2 + ten * 2 + five);
        //System.out.printf("%d,%d,%d, cur => %d, a=>%d \n", x,y,d, cur,a);
        int px = x - dx[d]; 
        int py = y - dy[d];
        int nx = x + dx[d];
        int ny = y + dy[d];
        int nnx = nx + dx[d];
        int nny = ny + dy[d];
        map[x][y] = 0;
        if(d % 2 == 0){
            if(checkRange(x+1,py)){ // 아래 칸
                map[x+1][py] += one;
            }
            if(checkRange(x-1,py)){ // 위 칸
                map[x-1][py] += one;
            }
            
            if(checkRange(x+2,y)){ // 2칸 아래
                map[x+2][y] += two;
            }
            if(checkRange(x+1,y)){ // 1칸 아래
                map[x+1][y] += seven;
            }
            if(checkRange(x-2,y)){ // 2칸 위
                map[x-2][y] += two;
            }
            if(checkRange(x-1,y)){ // 1칸 위
                map[x-1][y] += seven;
            }

            // 다음칸 확인
            if(checkRange(x,ny)) map[x][ny] += a;

            if(checkRange(x+1,ny)){ // 1칸 아래
                map[x+1][ny] += ten;
            }
            
            if(checkRange(x-1,ny)){ // 1칸 위
                map[x-1][ny] += ten;
            }

            if(checkRange(x,nny)){ // 2칸 앞
                map[x][nny] += five;
            }
        
        }else{
            if(checkRange(px,y+1)){ // 아래 칸
                map[px][y+1] += one;
            }
            if(checkRange(px,y-1)){ // 위 칸
                map[px][y-1] += one;
            }
            
            if(checkRange(x,y+2)){ // 2칸 오른쪽
                map[x][y+2] += two;
            }
            if(checkRange(x,y+1)){ // 1칸 오른쪽
                map[x][y+1] += seven;
            }
            if(checkRange(x,y-2)){ // 2칸 왼쪽
                map[x][y-2] += two;
            }
            if(checkRange(x,y-1)){ // 1칸 왼쪽
                map[x][y-1] += seven;
            }

            // 다음칸 확인
            if(checkRange(nx,y)) map[nx][y] += a;

            if(checkRange(nx,y+1)){ // 1칸 오른쪽
                map[nx][y+1] += ten;
            }
            
            if(checkRange(nx,y-1)){ // 1칸 왼쪽
                map[nx][y-1] += ten;
            }

            if(checkRange(nnx,y)){ // 2칸 앞
                map[nnx][y] += five;
            }
        
        }
    }
    static void printMap(int[][] arr ){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ", arr[i][j]);
            }
            System.out.println();
        }
    }
    static long getTotal(){
        long re = 0;

        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                re += map[i][j]; 
            }
        }
        return re;
    }
}