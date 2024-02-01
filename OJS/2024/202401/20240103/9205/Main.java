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
}
public class Main {
    static int n;
    static Point sp, ep;
    static Point[] stores;
    public static boolean checkDistance(Point s, Point e){
    
        return (Math.abs(s.x-e.x) + Math.abs(s.y-e.y)) <= 1000;
    }
    public static boolean BFS(){
        Queue<Point> myque = new LinkedList<>();
        boolean[] visited = new boolean[n];
        myque.add(sp);
        while(!myque.isEmpty()){
            Point c = myque.poll();
            if(checkDistance(c, ep)) return true;
            for(int i=0;i<n;++i){
                if(visited[i]) continue;
                if(checkDistance(c,stores[i])){
                    visited[i] = true;
                    myque.add(stores[i]);
                }
            }
        }
        return false;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int idx = 0;
        String[] answer = new String[T];
        while(T > 0){
            // Test case 개수 줄이기
            T--;
            n = Integer.parseInt(br.readLine());
            String[] temp = br.readLine().split(" ");
            stores = new Point[n];
            sp = new Point(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
            for(int i=0;i<n;++i){
                temp = br.readLine().split(" ");
                stores[i] = new Point(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
            }
            temp = br.readLine().split(" ");
            ep = new Point(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
            // 입력 끝
            if(BFS()) answer[idx++] = "happy";
            else answer[idx++] = "sad";
        }
        for(int i=0;i<answer.length;++i){
            System.out.println(answer[i]);
        }
    }    
}
