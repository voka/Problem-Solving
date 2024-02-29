import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Pair{// 공격정보, 맨 마지막에 몬스터 짝지을때 사용
    int d;
    int cnt;
    public Pair(int d, int cnt){
        this.d = d;
        this.cnt = cnt;
    }
}
class Point{// 좌표정보
    int x; 
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean isEmpty(){
        return (this.x == -1) && (this.y == -1);
    }
}
public class Main {
    static int N;
    static int px,py;// 플레이어 좌표
    static int[][] map;// 2차원 배열
    static int[][] dirMap, reDirMap;// 다음 이동할 방향을 저장한 2차원 배열, 역방향 배열(몬스터 제거할때 사용)
    static Pair[] attackInfo; // 공격 정보 담긴 배열
    static int score = 0;// player가 획득한 점수
    static int endRound;// 종료 라운드
    static int[] dx = {0,1,0,-1}, dy = {1,0,-1,0};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        map = new int[N][N]; 
        dirMap = new int[N][N];
        reDirMap = new int[N][N];
        endRound = Integer.parseInt(st.nextToken());
        attackInfo = new Pair[endRound];
        px = py = N/2;
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0;i<endRound;++i){
            st = new StringTokenizer(br.readLine());
            attackInfo[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        map[px][py] = 7; // player가 있는 지점은 1,2,3이 되면 안됨. (점수 계산시 오류 발생할 수 있음.)
        makeDirectionMap();
        simulation();
        System.out.println(score);
    }
    static void printMonster(){ // 몬스터 배열 출력하는 함수
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void makeDirectionMap(){ // 각 칸에서 이동해야 하는 방향을 저장
        dirMap[0][0] = -1;
        reDirMap[px][py] = -1;
        int goToCount = 3;// 정해진 칸만큼 앞으로 전진하는 횟수
        int goTo = N-1;// 방향 전환 없이 앞으로 전진하는 칸 수
        int goDir = 0;// 이동 방향
        int x = 0, y = 0;
        while(true){
            for(int i=0;i<goTo;++i){
                reDirMap[x][y] = goDir;
                int nx = x + dx[goDir];
                int ny = y + dy[goDir];
                dirMap[nx][ny] = (goDir % 2 == 0) ? 2 - goDir : 4 - goDir; // 이동하는 반대 방향 저장
                x = nx;
                y = ny;
            }
            goToCount--;
            goDir = (goDir + 1) % 4; // 다음 방향으로 전환
            if(goToCount == 0){
                goToCount = 2;
                goTo -= 1;// 전진칸수 감소
            }
            if(x == px && y == py) break;
        }
    }
    static void simulation(){
        for(int i=0;i<endRound;++i){
            attack(i); // 공격
            while(true){ // 더이상 4개 이상의 몬스터가 합쳐지지 않을때까지 반복
                fillMonster();// 몬스터를 중간에 빈칸이 없도록 채운다.
                boolean result = checkAndKillMonster(); // 몬스터를 제거한다. 
                if(!result) break;
            }
            addMonster(); // 몬스터를 나열해 짝지어 준다.
        }
    }
    static void attack(int id){ // 주어진 공격정보대로 몬스터를 지운다.
        Pair cur = attackInfo[id];
        for(int i=1;i<=cur.cnt;++i){
            int nx = px + dx[cur.d]*i;
            int ny = py + dy[cur.d]*i;
            score += map[nx][ny]; // 점수 획득
            map[nx][ny] = 0;
        }
    }
    static void fillMonster(){ // player가 있는 위치부터 (0,0)까지 한번만 반복문을 돌며 빈칸을 채워 나간다.
        int x = px, y = py;
        while(true){
            if(map[x][y] == 0 ){ // 빈 공간이 있을 경우 -> 다음 몬스터가 있는 지점을 찾아 현재 몬스터가 있는 지점으로 옮긴다.
                Point next = findNextMonster(x,y);
                if(next.isEmpty()) break;
                map[x][y] = map[next.x][next.y];
                map[next.x][next.y] = 0;
            }
            // 다음 지점으로 이동
            int nx = x + dx[dirMap[x][y]];
            int ny = y + dy[dirMap[x][y]];
            x = nx;
            y = ny;
            if(x == 0 && y == 0) break;
        }
    }
    static boolean checkAndKillMonster(){
        int x = px, y = py;
        int count = 0; // 연속된 몬스터의 개수
        boolean result = false;
        while(true){
            int nx = x + dx[dirMap[x][y]];
            int ny = y + dy[dirMap[x][y]];
            if(map[x][y] != map[nx][ny] || (nx == 0) && (ny == 0)){ // 0,0에 도착했거나 (이번 몬스터 != 다음 몬스터)인 경우 
                if(count >= 4){// 연속된 숫자의 몬스터가 4개 이상이라면 제거해준다. 
                    result = true;
                    score += count * map[x][y]; // 점수 더하기
                    for(int i=0;i<count;++i){ // 반대방향으로 진행하며 몬스터 제거
                        map[x][y] = 0;
                        int tx = x + dx[reDirMap[x][y]];
                        int ty = y + dy[reDirMap[x][y]];
                        x = tx;
                        y = ty;
                    }
                }
                count = 1; // 개수를 1로 바꿔준다.
            } 
            else count++; // 이전 몬스터와 같은 몬스터라면
            x = nx;
            y = ny;
            if((x == 0 && y == 0) || map[x][y] == 0) break; // 끝점에 도착했거나 현재 몬스터 값이 0이라면 종료한다.
        }

        return result;
    }
    static void addMonster(){
        int x = px, y = py;
        int count = 0;// 연속된 몬스터의 개수
        List<Pair> monster = new ArrayList<>();// 짝지어진 몬스터 정보
        while(true){ // 배열 정보 기록
            int nx = x + dx[dirMap[x][y]];
            int ny = y + dy[dirMap[x][y]];
            if(map[x][y] != map[nx][ny] || (nx == 0) && (ny == 0)){
                // 시작점을 제외하고 몬스터를 짝지어 배열에 담는다.
                if(map[x][y] != 7) monster.add(new Pair(map[x][y], count));
                count = 1;
            } 
            else count++; // 이전 몬스터와 같은 몬스터라면
            x = nx;
            y = ny;
            if((x == 0 && y == 0) || map[x][y] == 0) break;
        }
        x = px;
        y = py;
        for(Pair p: monster){ // 짝지어진 정보는 2칸씩 담긴다.
            int n1x = x + dx[dirMap[x][y]]; 
            int n1y = y + dy[dirMap[x][y]];
            if(n1x == 0 && n1y == 0) break;
            int n2x = n1x + dx[dirMap[n1x][n1y]];
            int n2y = n1y + dy[dirMap[n1x][n1y]];
            map[n1x][n1y] = p.cnt; // 개수를 담고
            map[n2x][n2y] = p.d; // 값을 담는다.
            x = n2x;
            y = n2y;
            if(x == 0 && y == 0) break;
        }

    }
    static Point findNextMonster(int x, int y){ // map[x][y]가 0이 아닌 값을 가지는 좌표를 찾아 반환한다. 이때 방향 배열을 사용한다.
        while(true){
            int nx = x + dx[dirMap[x][y]];
            int ny = y + dy[dirMap[x][y]];
            if(map[nx][ny] != 0) return new Point(nx, ny);
            x = nx; 
            y = ny;
            if(x == 0 && y == 0) break;
        }
        return new Point(-1, -1);
    }
}

