import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
    static int root;
    static long DNum = 0;
    static long WNum = 0;
    static int[] indegree;
    static List<Point> line = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        indegree = new int[n+1];
        StringTokenizer st;
        for(int i=1;i<n;++i){
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            indegree[p]++;
            indegree[c]++;
            line.add(new Point(p,c));
        }
        getWNum();
        getDNum();
        // System.out.println(DNum);
        // System.out.println(WNum);
        if(WNum * 3 < DNum){
            System.out.println("D");
        }else if(WNum * 3 > DNum){
            System.out.println("G");
        }else{
            System.out.println("DUDUDUNGA");
        }
    }

    static void getWNum(){
        for(int i=1;i<=n;++i){
            int cSize = indegree[i];
            if(cSize >= 3) WNum += (long)cSize * (cSize-1) *(cSize-2) / 6;
        }
    }

    static void getDNum(){
        for(Point cur : line) // 연결된 노드를 확인할때 서로에게 연결된 개수를 빼고 곱하면 ㄷ 모양 개수를 구할 수 있음.
            DNum += (indegree[cur.x] - 1) *(long)(indegree[cur.y] - 1);
    }
}

