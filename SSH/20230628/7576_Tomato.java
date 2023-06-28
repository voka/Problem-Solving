package Graph;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
public class _7576_Tomato {
    static int m,n,day, flag;
    static int[] dx = {1, 0, 0, -1};
    static int[] dy = {0, 1, -1, 0};
    static int[][] tomato;
    static int[][] check;
    static Queue<Integer[]> queue = new LinkedList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        tomato = new int[n][m];
        check = new int[n][m];
        for(int i=0;i<n;i++){
            st= new StringTokenizer(br.readLine());
            for(int j=0;j<m;j++){
                int tmp = Integer.parseInt(st.nextToken());
                tomato[i][j] = tmp;
                if(tmp == 1) {
                    queue.add(new Integer[]{i,j});
                }
            }

        }
        bfs();

        if(flag == 1){
            System.out.println(-1);
        }
        else {
            System.out.println(day);
        }



    }
    public static void bfs(){
        while(!queue.isEmpty()){
            Integer[] t = queue.poll();
            int row = t[0];
            int col = t[1];
            for(int i=0;i<4;i++){
                int nx = col + dx[i];
                int ny = row + dy[i];
                if(nx>=0 && ny>=0 && nx <m && ny<n){
                    if(tomato[ny][nx] == 0){
                        tomato[ny][nx] = tomato[row][col]+1;
                        queue.add(new Integer[]{ny,nx});
                    }
                }
            }
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(tomato[i][j] == 0){
                    flag = 1;
                    break;
                }
                if(day<tomato[i][j]){
                    day = tomato[i][j];
                }
            }
        }
        day -=1;
    }
}
