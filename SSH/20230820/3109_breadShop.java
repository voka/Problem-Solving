package Greedy;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Stack;
public class _3109_breadShop {
  static int R,C;
  static int[] dc ={1,1,1};
  static int[] dr ={1,0,-1};
  static char[][] map;
  static boolean[][] visited;

  public static void main(String[] args)throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    map = new char[R+1][C+1];
    visited = new boolean[R+1][C+1];
    for(int i=1;i<=R;i++){
      String tmp = br.readLine();
      for(int j=1;j<=C;j++){
        map[i][j] = tmp.charAt(j-1);
      }
    }
    int result = 0;
    for(int i=1;i<=R;i++){
      if(visited[i][1] == false){
        result += greedy(i,1);
      }
    }
    System.out.println(result);


  }
  public static int greedy(int row, int col){
    Stack<int[]> stack = new Stack<>();
    int[] start = new int[]{row,col};
    stack.push(start);
    while(!stack.empty()){
      int[] tmp = stack.pop();
      int cr = tmp[0];
      int cc = tmp[1];

      if(cc == C){
        visited[cr][cc] = true;
        return 1;
      }
      visited[cr][cc] = true;
      for(int i=0;i<3;i++){
        int nr = cr+dr[i];
        int nc = cc+dc[i];
        if(nr >= 1 && nr <= R && nc >= 1 && nc <= C){
          if(map[nr][nc] == '.' && visited[nr][nc] == false){
            stack.push(new int[]{nr, nc});
          }
        }
      }
    }
    return 0;

  }
}
