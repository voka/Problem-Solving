import java.util.*;
import java.io.*;

/**
 * 
 * 게임은 NxN 정사각 보드위에서 진행되고, 몇몇 칸에는 사과가 놓여져 있다. 보드의 상하좌우 끝에 벽이 있다.
 * 게임이 시작할때 뱀은 맨위 맨좌측에 위치하고 뱀의 길이는 1 이다. 뱀은 처음에 오른쪽을 향한다.
 * 
 * 뱀은 매 초마다 이동을 하는데 다음과 같은 규칙을 따른다.
 * 
 * 먼저 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.
 * 만약 벽이나 자기자신의 몸과 부딪히면 게임이 끝난다.
 * 만약 이동한 칸에 사과가 있다면, 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
 * 만약 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 즉, 몸길이는 변하지 않는다.
 * 
 * 사과의 위치와 뱀의 이동경로가 주어질 때 이 게임이 몇 초에 끝나는지 계산하라.
 */
public class Snake_3190 {

    static class Point{
        int x;
        int y;

        public Point(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    static int[] dx = { 1, 0, -1, 0 }, dy = { 0, 1, 0, -1 }, times, idx_plus; // 오른쪽은 idx + 1 왼쪽은 idx -1
    static int N, L, K, apple_count = 0, dir_idx = 0, dir_r = 0, dir_c = 0, cur_time = 0, cur_x = 1, cur_y = 1, tail_x = 1,tail_y = 1;
    static int[][] maps;

    static Queue<Point> tail_dir = new LinkedList<>();
    
    public static int change_dir(int cur_dir, int plus){
        int dir;
        if(cur_dir + plus < 0){
            dir = 3;
        }
        else if(cur_dir + plus == 4){
            dir = 0;
        }
        else{
            dir = cur_dir + plus;
        }
        return dir;
    }
    public static void printArr(){
        System.out.println("cur_time =============================" + cur_time);
        for(int i=1;i<=N;++i){
            for(int j=1;j<=N;++j){
                System.out.print(maps[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void move() {
        while(true){
            int next_x = cur_x + dx[dir_r];
            int next_y = cur_y + dy[dir_c];
            //printArr();
            if ((next_x < 1 || next_x > N) || (next_y < 1 || next_y > N)){
                break;
            }
            if (maps[next_y][next_x] == 1){
                break;
            }
            tail_dir.add(new Point(dx[dir_r],dy[dir_c]));//꼬리 방향 넣기
            if (maps[next_y][next_x]!= 2){ // 사과가 머리쪽에 없는 경우
                maps[tail_y][tail_x] = 0; // 꼬리를 줄인다.
                Point tmp = tail_dir.poll(); // 꼬리 방향 빼서 반영
                tail_x += tmp.x;
                tail_y += tmp.y;
            }
            maps[next_y][next_x] = 1; // 머리를 늘린다.
            cur_time ++;
            cur_x = next_x;
            cur_y = next_y;
            if(dir_idx < L && cur_time == times[dir_idx]){
                dir_r = change_dir(dir_r,idx_plus[dir_idx]);
                dir_c = change_dir(dir_c,idx_plus[dir_idx]);
                dir_idx++;
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());
        maps = new int[N + 2][N + 2];
        maps[1][1] = 1;
        for (int i = 0; i < K; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            maps[y][x] = 2; // apple
        }
        L = Integer.parseInt(br.readLine());
        times = new int[L];
        idx_plus = new int[L];
        for (int i = 0; i < L; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            times[i] = Integer.parseInt(st.nextToken());
            idx_plus[i] = ("D".equals(st.nextToken())) ? 1 : -1;
        }
        move();
        System.out.println(cur_time+1);
    }
}
