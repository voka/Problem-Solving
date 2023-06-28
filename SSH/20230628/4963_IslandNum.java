package Graph;
import java.io.*;
import java.util.StringTokenizer;
public class _4963_IslandNum {
    static int w,h;
    static int[] dx = {1,1,1,0,0,-1,-1,-1};
    static int[] dy = {0,1,-1,1,-1,0,1,-1};
    static int[][] map;
    static int[][] check;
    static int count;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String input="";

        while(!(input =br.readLine()).equals("0 0")){
            st = new StringTokenizer(input);
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());
            map = new int[h][w];
            check = new int[h][w];

            for(int i=0;i<h;i++){
                st = new StringTokenizer(br.readLine());
                for(int j=0;j<w;j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }



            for(int i=0;i<h;i++){
                for(int j=0;j<w;j++){
                    if(map[i][j] != 0 && check[i][j] == 0) {
                        count++;
                        dfs(i,j);
                    }
                }
            }
            System.out.println(count);
            count=0;



        }
    }
    public static void dfs(int row, int col){
        check[row][col] = 1;
        for(int i=0;i<8;i++){
            int nx = col+dx[i];
            int ny = row+dy[i];
            if(nx>=0 && nx<w && ny>=0 && ny<h){

                if(check[ny][nx] == 0 && map[ny][nx] != 0){
                    dfs(ny,nx);
                }
            }
        }

    }
}
