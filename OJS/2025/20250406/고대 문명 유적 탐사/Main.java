import java.io.*;
import java.util.*;

class Point{
    int x;
    int y;
    public Point(int x,int y){
        this.x = x;
        this.y = y;

    }
}

public class Main {
    // 개수 변수들
    static int K,M;

    // 탐색할 격자
    static int[][] map;
    // 체크용 격자
    static int[][] check;
    // 탐색용 격자
    static boolean[][] visited;

    //회전 각도
    static int[] dr = {0,1,2};

    //벽면
    static int aidx;// 벽면 인덱스
    static int[] arr;// 벽면 수

    //탐색용 변수
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[5][5];
        for(int i=0;i<5;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<5;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        arr = new int[M];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<M;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        aidx = 0;// 벽면 인덱스 0부터 시작

        //회전 함수 테스트

        //rotateTest(); 완
        for(int t=0;t<K;++t){
            //System.out.printf("%d 번째 턴\n", t+1);
            int ans = 0;
            int maxX = -1;
            int maxY = -1;
            int maxR = -1;
            int maxValue = -1;
            //유물 탐색
            for(int j=1;j<=3;++j){
                for(int i=1;i<=3;++i){
                    for(int r=0;r<3;++r){
                        int[][] newMap = rotate(i, j, r);
                        int curMaxValue = totalNumber(newMap);
                        if(maxValue < curMaxValue){
                            maxValue = curMaxValue;
                            maxX = i;
                            maxY = j; 
                            maxR = r;
                        }else if(maxValue == curMaxValue){ //유물의 가치가 같다면 회전하는 반경이 작은 곳이 우선순위가 높다
                            if(maxR > r){
                                maxX = i;
                                maxY = j;
                                maxR = r;
                            }
                        }
                    }
                }
            }
            //System.out.printf("회전 방향 : %d,  회전 중심 : (%d, %d), 총 유물 획득 : %d\n\n", maxR,maxX,maxY,maxValue);
            if(maxValue == -1) break;
            int value = 0;
            map = rotate(maxX, maxY, maxR);
            while(true){
                //System.out.println("획득 전");
                //print2dArr(map);
                value = findTarget(); // 유물 획득하기
                if(value == 0) break; // 유물을 더이상 획득하지 못한다면 반복문 종료
                ans += value;
                fill(); // 획득 했다면 빈자리 채우기
                //System.out.printf("획득 %d\n",value );
                //print2dArr(map);
            }
            System.out.printf("%d ",ans);
        }
    }
    static void print2dArr(int[][] arr){
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                System.out.printf("%d ",arr[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        
    }
    static void rotateTest(){
        print2dArr(map);
        int[][] map90 = rotate(1, 1, 0);
        print2dArr(map90);
        int[][] map180 = rotate(1, 1, 1);
        print2dArr(map180);
        int[][] map270 = rotate(1, 1, 2);
        print2dArr(map270);
        
    }
    // 3x3 격자 회전하는 함수 (90, 180, 270)
    static int[][] rotate(int x, int y, int r){
        if(r == 0){ // 90도
            return rotate90(x, y);
        }else if(r == 1){ // 180도
            return rotate180(x, y);
        }else{ // 270도
            return rotate270(x, y);
        }
    }

    static int[][] copyMap(){
        int[][] temp = new int[5][5];

        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                temp[i][j] = map[i][j];
            }
        }
        return temp;
    }
    static int[][] rotate90(int x, int y){
        int[][] temp = copyMap();
        //윗줄
        temp[x-1][y-1] = map[x+1][y-1];
        temp[x-1][y] = map[x][y-1];
        temp[x-1][y+1] = map[x-1][y-1];
        //중간줄
        temp[x][y-1] = map[x+1][y];
        temp[x][y+1] = map[x-1][y];
        //아래줄
        temp[x+1][y-1] = map[x+1][y+1];
        temp[x+1][y] = map[x][y+1];
        temp[x+1][y+1] = map[x-1][y+1];

        return temp;
    }

    static int[][] rotate180(int x, int y){
        int[][] temp = copyMap();
        //윗줄
        temp[x-1][y-1] = map[x+1][y+1];
        temp[x-1][y] = map[x+1][y];
        temp[x-1][y+1] = map[x+1][y-1];
        //중간줄
        temp[x][y-1] = map[x][y+1];
        temp[x][y+1] = map[x][y-1];
        //아래줄
        temp[x+1][y-1] = map[x-1][y+1];
        temp[x+1][y] = map[x-1][y];
        temp[x+1][y+1] = map[x-1][y-1];
        
        return temp;
        
    }

    static int[][] rotate270(int x, int y){        
        int[][] temp = copyMap();

        //윗줄
        temp[x-1][y-1] = map[x-1][y+1];
        temp[x-1][y] = map[x][y+1];
        temp[x-1][y+1] = map[x+1][y+1];
        //중간줄
        temp[x][y-1] = map[x-1][y];
        temp[x][y+1] = map[x+1][y];
        //아래줄
        temp[x+1][y-1] = map[x-1][y-1];
        temp[x+1][y] = map[x][y-1];
        temp[x+1][y+1] = map[x+1][y-1];

        return temp;
        
    }

    // 탐색시 회전 후 획득 가능한 개수만 확인하는 함수
    static int totalNumber(int[][] newMap){
        visited = new boolean[5][5];
        int cnt = 0;
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                if(visited[i][j]) continue;
                int cur = onlyfindNum(newMap,i,j);
                if(cur >= 3) cnt += cur;
            }
        }
        if(cnt == 0) return -1;
        return cnt;
    }
    static int onlyfindNum(int[][] newMap, int x, int y){
        int target = newMap[x][y];
        int re = 0;
        visited[x][y] = true;
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(x, y));
        while(!q.isEmpty()){
            Point cur = q.poll();
            re++;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(!checkRange(nx, ny)) continue; // 범위를 벗어난다면 탐색 종료
                if(visited[nx][ny]) continue; // 이미 탐색한 곳이라면 탐색 종료
                if(newMap[nx][ny] != target) continue;// 원하는 수가 아닌 경우 탐색 종료
                visited[nx][ny] = true; // 탐색함.
                q.add(new Point(nx, ny));
            }
        }
        return re;
    }


    // 유물 획득용 함수
    static int findTarget(){
        check = new int[5][5]; // 유물 획득 전 체크용 map 초기화
        int cnt = 0;
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                if(check[i][j] != 0) continue;
                int cur = bfs(i,j);
                if(cur >= 3) cnt += cur;
            }
        }
        //print2dArr(check);
        return cnt;
    }
    static int bfs(int x, int y){
        int target = map[x][y];
        boolean[][] temp = new boolean[5][5];
        int re = 0;
        check[x][y] = 1;
        temp[x][y] = true;
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(x, y));
        while(!q.isEmpty()){
            Point cur = q.poll();
            re++;
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(!checkRange(nx, ny)) continue;
                if(check[nx][ny] != 0) continue;
                if(map[nx][ny] != target) continue;// 원하는 수가 아닌 경우 탐색 종료
                check[nx][ny] = 1; // 탐색함.
                temp[nx][ny] = true;
                q.add(new Point(nx, ny));
            }
        }
        if(re >= 3){ // 3개 이상인 경우 temp에 있는 이번 탐색 과정에서 지나친 블럭들을 모두 2로 만들어 준다
            for(int i=0;i<5;++i){
                for(int j=0;j<5;++j){
                    if(temp[i][j]){
                        check[i][j] = 2;
                    }
                }
            }
        }
        return re;
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<=4) && (y<=4);
    }
    // 없어진 유물 채워주는 함수  (획득한 유물이 있는 경우에만 실행되는 함수)
    static void fill(){
        
        for(int j=0;j<5;++j){
            for(int i=4;i>=0;--i){
                if(check[i][j] == 2){ // 이번에 획득한 유물 자리인 경우
                    map[i][j] = arr[aidx++];
                }
            }
        }
    }
}
