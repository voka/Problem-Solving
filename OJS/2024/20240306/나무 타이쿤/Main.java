import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n,m;
    static int[][] map;
    static boolean[][] medicine;
    static int[] dx = {0,-1,-1,-1,0,1,1,1}, dy = {1,1,0,-1,-1,-1,0,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        medicine = new boolean[n][n];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // 약 배포
        medicine[n-2][0] = true;
        medicine[n-2][1] = true;
        medicine[n-1][0] = true;
        medicine[n-1][1] = true;

        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken());
            moveMedcine(d, c);
            growTree();
            spreadMedicine();
        }
        System.out.println(getTotalTree());

    }
    // d : 방향
    // cnt : 이동할 칸 수
    static void moveMedcine(int d, int cnt){
        boolean[][] next = new boolean[n][n];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(medicine[i][j]){
                    next[(i + n + dx[d]*cnt)%n][(j + n + dy[d]*cnt)%n] = true;
                    medicine[i][j] = false;
                    
                }
            }
        }
        medicine = next;
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x < n) && (y < n);
    }
    static void growTree(){

        // 영양제 있는 칸 나무 높이 1 증가시키기
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(medicine[i][j]) map[i][j] += 1;
            }
        }
        // 인접한 대각선에 나무가 있는지 확인 후 있는 만큼 증가시키기
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                // 대각선 방향만 검사
                if(medicine[i][j]){ 
                    int cnt = 0;
                    for(int k=1;k<8;k = k + 2){
                        int nx = i + dx[k];
                        int ny = j + dy[k];
                        if(checkRange(nx, ny) && map[nx][ny] > 0){
                            cnt++;
                        }
                    }
                    map[i][j]+=cnt;
                }
            }
        }
    }
    static void spreadMedicine(){
        boolean[][] next = new boolean[n][n];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(!medicine[i][j] && map[i][j] >= 2){
                    next[i][j] = true;
                    map[i][j] -= 2;
                    medicine[i][j] = false;
                }
            }
        }
        medicine = next;

    } 
    static int getTotalTree(){
        int result = 0;
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                result += map[i][j];
            }
        }
        return result;
    }
    static void printM(){
        System.out.println("\n영양제\n");
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%b ",medicine[i][j]);
            }
            System.out.println();
        }
    }
    static void printT(){
        System.out.println("\n나무\n");
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
    }
}
 