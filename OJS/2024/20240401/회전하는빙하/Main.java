import java.io.*;
import java.util.*;
class Point{
    int x;
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int n,q,size;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static int[][] map,temp;
    static boolean[][] visited;
    static List<Integer> levelList = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        size = multi(n);
        map = new int[size][size];
        for(int i=0;i<size;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<size;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        String level = br.readLine();
        for(int i=0;i<level.length();++i){
            int cur = level.charAt(i) - '0';
            if(cur >= 0){
                //System.out.printf("Level : %d\n", cur);
                if(cur > 0) {
                    visited = new boolean[size][size];
                    temp = new int[size][size]; 
                    rotate(0,0,cur);
                    copyValue();
                }
                temp = new int[size][size];
                melt();
                copyValue();
                //printArr();
            }
        }
        long ans1 = getTotal();
        int ans2 = getBig(); 
        System.out.println(ans1);
        System.out.println(ans2);
    }
    static void printArr(){
        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
        
    }
    static void melt(){
        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                int count = 0;
                if(map[i][j] == 0) continue;
                for(int d=0;d<4;++d){
                    int nx = i + dx[d];
                    int ny = j + dy[d];
                    if(checkRange(nx,ny) && map[nx][ny] > 0){
                        count++;
                    }
                }
                if(count >= 3) temp[i][j] = map[i][j];
                else temp[i][j] = map[i][j] - 1;
            }
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<size) && (y<size);
    }
    static void rotate(int x, int y, int cur){
        if(visited[x][y]) return;
        visited[x][y] = true;
        //System.out.printf("%d, %d, %d\n",x,y,cur);
        int l = multi(cur-1);
        //왼쪽위 -> 오른쪽위
        for(int i=0;i<l;++i){
            for(int j=0;j<l;++j){
                temp[x+i][y+j+l] = map[x+i][y+j];
            }
        }
        // //오른쪽위 -> 오른 아래
        for(int i=0;i<l;++i){
            for(int j=0;j<l;++j){
                temp[x+i+l][y+j+l] = map[x+i][y+j+l];
            }
        }
        // //오른 아래 -> 왼쪽 아래
        for(int i=0;i<l;++i){
            for(int j=0;j<l;++j){
                temp[x+i+l][y+j] = map[x+i+l][y+j+l];
            }
        }

        // //왼쪽 아래 -> 왼쪽 위
        for(int i=0;i<l;++i){
            for(int j=0;j<l;++j){
                temp[x+i][y+j] = map[x+i+l][y+j];
            }
        }
        int plus = multi(cur);
        if(checkRange(x+plus,y)) rotate(x+plus,y,cur);
        if(checkRange(x,y+plus)) rotate(x,y+plus,cur);

    }
    static int multi(int l){
        int r = 1;
        for(int i=0;i<l;++i){
            r *= 2;
        }
        return r;
    }
    static long getTotal(){
        long r = 0;

        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                r += map[i][j];
            }
        }
        return r;
    }
    static int getBig(){
        int r = 0;
        visited = new boolean[size][size];
        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                if(!visited[i][j] && map[i][j] > 0){
                    r = Math.max(r,bfs(i,j));
                    //System.out.printf("%d, %d, %d\n", i,j,r);
                }
            }
        }
        
        return r;
    }
    static void copyValue(){
        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                map[i][j] = temp[i][j];
            }
        }
    }
    static int bfs(int x, int y){
        int r = 0;
        visited[x][y] = true;
        Queue<Point> que = new ArrayDeque<>();
        que.add(new Point(x, y));
        while(!que.isEmpty()){
            Point cur = que.poll(); 
            r++;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(checkRange(nx,ny) && !visited[nx][ny] && map[nx][ny] > 0){
                    visited[nx][ny] = true;
                    que.add(new Point(nx,ny));
                }
            }
        }
        return r;
    }

}