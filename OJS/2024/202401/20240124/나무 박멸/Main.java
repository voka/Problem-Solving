import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main {
    static int n,m,k,c,score = 0;
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1}, kdx = {1,1,-1,-1}, kdy = {1,-1,1,-1};
    static int[][] map, treeMap, poisonMap;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        map = new int[n+1][n+1];
        treeMap = new int[n+1][n+1];
        poisonMap = new int[n+1][n+1];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                int cur = Integer.parseInt(st.nextToken());
                map[i][j] = cur;
            }
        }
        for(int i=0;i<m;++i){
            //System.out.printf("\nYear ===> %d", i+1);
            spread();
            //printArray(map);
            selectAndKillPoint();
            minusPoison();
            //printArray(map);
        }
        System.out.println(score);

    }

    static void printArray(int[][] arr){
        System.out.println();
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ",arr[i][j]);
            }
            System.out.println();
        }
    }
    static void minusPoison(){ //제초제를 c년 후에 없어지게 만드는 함수

        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(poisonMap[i][j] > 0){
                    poisonMap[i][j] -= 1;
                }
            }
        }

    }
    static void selectAndKillPoint(){ // 제초제를 뿌렸을때 가장 많이 나무를 없앨 수 있는 Point 찾기
        int bestKill = 0;
        int bestX = n;
        int bestY = n;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;++j){
                if(map[i][j] < 0) continue; // 벽이 아닌 곳을 제외하고는 모두 카운트
                int cur = countKill(i, j, 0);
                if(bestKill < cur){
                    bestKill = cur;
                    bestX = i;
                    bestY = j;
                }else if(bestKill == cur){
                    if(bestX > i){
                        bestX = i; 
                        bestY = j;
                    }else if(bestX == i){
                        if(bestY > j){
                            bestY = j; 
                        }
                    }
                }
            }
        }
        //System.out.printf("Best -> %d, %d, %d\n\n", bestKill, bestX, bestY);
        score += bestKill;
        kill(bestX, bestY, 0);

    }
    static int countKill(int x, int y, int cnt){
        int result = map[x][y];
        if(result == 0) return 0; // 나무가 없는 곳이라면 더이상 제초제가 확산되지 않음.
        for(int i=0;i<4;++i){
            int nx = x + kdx[i];
            int ny = y + kdy[i];
            // 범위를 벗어나지 않고, 나무가 있는곳에
            if(checkRange(nx, ny) && map[nx][ny] > 0){
                result += countOneDirKill(nx, ny, i,cnt + 1);
            }
        }
        return result;
    }
    static int countOneDirKill(int x, int y, int d, int cnt){
        int result = map[x][y];
        if(result == 0) return 0; // 나무가 없는 곳이라면 더이상 제초제가 확산되지 않음.
        if(cnt == k) return result;
        int nx = x + kdx[d];
        int ny = y + kdy[d];
        // 범위를 벗어나지 않고, 나무가 있는곳에
        if(checkRange(nx, ny) && map[nx][ny] > 0){
            result += countOneDirKill(nx, ny,d, cnt + 1);
        }
        return result;
    }
    static void kill(int x, int y, int cnt){ // 한 정점에서 뻗어나가는 제초제
        poisonMap[x][y] = c + 1; // 매년 -1 을 해주기 위해 + 1을 해줌
        if(map[x][y] <= 0) return; // 나무가 없는 곳이라면 더이상 제초제가 확산되지 않음.
        map[x][y] = 0;
        for(int i=0;i<4;++i){
            int nx = x + kdx[i];
            int ny = y + kdy[i];
            // 범위를 벗어나지 않고, 나무가 있는곳에
            if(checkRange(nx, ny) && map[nx][ny] >= 0){
                //System.out.printf("next => %d, %d\n", nx, ny);
                oneDirKill(nx, ny, i, cnt+1);
            }
        }
    }
    static void oneDirKill(int x, int y, int d, int cnt){
        if(map[x][y] >= 0) poisonMap[x][y] = c + 1;
        if(map[x][y] <= 0) return; // 나무가 없는 곳이라면 더이상 제초제가 확산되지 않음.
        map[x][y] = 0;
        if(cnt == k) return;
        int nx = x + kdx[d];
        int ny = y + kdy[d];
        // 범위를 벗어나지 않고, 나무가 있는곳에
        if(checkRange(nx, ny) && map[nx][ny] >= 0){
            //System.out.printf("next => %d, %d\n", nx, ny);
            oneDirKill(nx, ny, d, cnt+1);
        }
    }
    static boolean checkRange(int x, int y){
        return (x >= 0) && (y >=0 ) && (x < n) && (y < n);
    }
    static void spread(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(map[i][j] > 0){ // 나무가 있는 칸일 경우 증식 시작
                    int near = 0; // 증식 가능한 장소 개수
                    for(int d=0;d<4;++d){
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        // 범위를 벗어나지 않고, 나무가 있는 곳일때
                        if(checkRange(nx,ny) && map[nx][ny] > 0){
                            map[i][j] += 1;
                        }
                        // 범위를 벗어나지 않고, 나무와 벽이 없는 곳이며, 제초제의 영향이 없는 곳일 경우 증식 가능
                        if(checkRange(nx,ny) && map[nx][ny] == 0 && poisonMap[nx][ny] == 0){
                            near += 1; // 증식 가능 장소 개수 증가
                        }
                    }
                    if(near != 0){ // 증식이 가능할 경우에
                        int plus = map[i][j] / near;// 주번 증식 가능 장소 만큼 나누기
                        for(int d=0;d<4;++d){
                            int nx = i + dx[d];
                            int ny = j + dy[d];
                            // 범위를 벗어나지 않고, 나무와 벽이 없는 곳이며, 제초제의 영향이 없는 곳일 경우
                            if(checkRange(nx,ny) && map[nx][ny] == 0 && poisonMap[nx][ny] == 0){
                                treeMap[nx][ny] += plus;
                            }
                        }
                    }
                    //printArray(treeMap);
                    
                }
            }
        }

        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(treeMap[i][j] != 0 && map[i][j] == 0 ){
                    map[i][j] = treeMap[i][j]; // 증식한 나무 개수 옮기기
                    treeMap[i][j] = 0; // 초기화
                }
            }
        }

    }

}
