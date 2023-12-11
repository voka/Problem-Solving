import java.util.*;
class Point{
    public int x;
    public int y;
    
    public Point(int x,int y){
        this.x = x;
        this.y = y;
    }
}


class Solution {
    
    public int[] dx = {1,0,-1,0};
    public int[] dy = {0,1,0,-1};
    public HashMap<Integer,Integer> mymap = new HashMap<>();
    public boolean ValidArea(int x, int y, int n, int m){
        return x >= 0 && x < m && y >= 0 && y < n;
    }
    
    public void printArray(boolean[][] visited){
        for(int i=0;i<visited.length;++i){
            for(int j=0;j<visited[0].length;++j){
                System.out.printf("%b ",visited[i][j]);
            }
            System.out.println();
        }
    }
    public void bfs(boolean[][] visited, int[][] land, int x, int y,int n, int m){
        //System.out.printf("%d, %d\n", x, y);
        Queue<Point> myque = new LinkedList<>();
        Point start = new Point(x,y);
        myque.add(start);
        visited[y][x] = true;
        int max_size = 0;
        int[] x_visited = new int[501];
        x_visited[x] = 1;
        while(myque.size() > 0){
            Point cur = myque.poll();
            max_size++;
            for(int i=0;i<4;++i){
                int nx = cur.x + this.dx[i];
                int ny = cur.y + this.dy[i];
                if(ValidArea(nx,ny,n,m) && visited[ny][nx] != true && land[ny][nx] == 1){
                    visited[ny][nx] = true;
                    x_visited[nx] = 1;
                    myque.add(new Point(nx,ny));
                }
            }
        }
        //System.out.printf("size == > %d\n",max_size);
        if(max_size>0){
            for(int i=0;i<501;++i){
                if(x_visited[i] == 1){
                    //System.out.printf("%d ",i);
                    if(this.mymap.containsKey(i)){
                        int cur = mymap.get(i);
                        this.mymap.put(i,max_size + cur);
                    }
                    else{
                        this.mymap.put(i,max_size);
                    }
                }
            }
            //System.out.println();
        }
    }
    public int solution(int[][] land) {
        int answer = 0;
        int n = land.length;
        int m = land[0].length;
        
        //System.out.printf("%d, %d\n", n, m);
        boolean[][] visited = new boolean[n][m];
        for(int i=0;i<n;++i){
            for(int j=0;j<m;++j){
                if(visited[i][j] != true && land[i][j] == 1){
                    bfs(visited,land,j,i,n,m);
                    //printArray(visited);
                }
            }
        }
        for(int i=0;i<m;++i){
            if(this.mymap.containsKey(i)){
                answer = Math.max(answer,this.mymap.get(i));
            }
        }
        
        return answer;
    }
}