import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

class Condition implements Comparable<Condition>{
    int x; 
    int y;
    int fNum;
    int empty;
    public Condition(int x, int y, int f, int e){
        this.x = x;
        this.y = y;
        this.fNum = f;
        this.empty = e;
    }
    @Override
    public int compareTo(Condition o){
        if(this.fNum < o.fNum) return 1;
        else if(this.fNum > o.fNum) return -1;
        if(this.empty < o.empty) return 1;
        else if(this.empty > o.empty) return -1;
        if(this.x > o.x) return 1;
        else if(this.x < o.x) return -1;
        if(this.y > o.y) return 1;
        else if(this.y <o.y) return -1; 
        return 0;
    }


}
public class Main {
    static int n;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static int[] input;
    static int[][] map;
    static boolean[][] check;
    static List<Set<Integer>> like = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        input = new int[n*n+1];
        map = new int[n][n];
        check = new boolean[n][n];
        for(int i=0;i<n*n + 1;++i){
            like.add(new HashSet<>());
        }
        for(int i=1;i<=n*n;++i){
            st = new StringTokenizer(br.readLine());
            input[i] = Integer.parseInt(st.nextToken());
            for(int j=0;j<4;++j){
                int cur = Integer.parseInt(st.nextToken());
                like.get(input[i]).add(cur);
            }
        }
        for(int i=1;i<=n*n;++i){
            seat(input[i]);
        }
        //printArr();
        int ans = 0;
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                int curLikeNum = getLikeNum(i, j, map[i][j]);
                if(curLikeNum == 1) ans += 1;
                else if(curLikeNum == 2) ans += 10;
                else if(curLikeNum == 3) ans += 100;
                else if (curLikeNum == 4) ans += 1000;
            }
        
        }
        System.out.println(ans);
    }    
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<n) && (y<n);
    }
    static int getLikeNum(int x, int y, int i){
        int re = 0;
        for(int j=0;j<4;++j){
            int nx = x + dx[j];
            int ny = y + dy[j];
            if(checkRange(nx, ny)){
                int val = map[nx][ny];
                if(like.get(i).contains(val)){
                    re++;
                }
            }
        }
        return re;
    }
    static int getEmptyNum(int x, int y){
        int re = 0;
        for(int j=0;j<4;++j){
            int nx = x + dx[j];
            int ny = y + dy[j];
            if(checkRange(nx, ny) && !check[nx][ny]){
                re++;
            }
        }
        return re;
    }
    static void seat(int num){
        // System.out.printf("NUM : %d\n", num);
        // System.out.print("LIKE : ");
        // for(int k : like.get(num)){
        //     System.out.printf("%d ", k);
        // }
        // System.out.println();
        PriorityQueue<Condition> pq = new PriorityQueue<>();
        // 빈 자리 중에서 찾기
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(!check[i][j]){
                    int curLikeNum = getLikeNum(i, j, num);
                    int emptyNum = getEmptyNum(i,j);
                    pq.add(new Condition(i, j, curLikeNum, emptyNum));
                    //System.out.printf("(%d, %d), like : %d, empty: %d\n", i,j,curLikeNum,emptyNum);
                }
            }
        }
        Condition bestSeat = pq.poll();
        //System.out.printf("%d => (%d, %d), like : %d, empty : %d\n", num,bestSeat.x, bestSeat.y, bestSeat.fNum, bestSeat.empty);
        map[bestSeat.x][bestSeat.y] = num;
        check[bestSeat.x][bestSeat.y] = true;
    } 
    static void printArr(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
    }
}
