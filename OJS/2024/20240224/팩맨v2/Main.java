import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][][] monster, egg; 
    static int[][] dieMap;  
    static int score;
    static int px, py, pd;
    static int[] mdx = {-1,-1,0,1,1,1,0,-1}, mdy = {0,-1,-1,-1,0,1,1,1}; // 위, 왼쪽위,, 왼쪽, 왼쪽 아래, 아래쪽, 오른쪽 아래, 오른쪽, 오른쪽 위 순
    static int[] pdx = {-1,0,1,0}, pdy = {0,-1,0,1}; // 상 - 좌 - 하 - 우
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int m, t;
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        px = Integer.parseInt(st.nextToken())-1;
        py = Integer.parseInt(st.nextToken())-1;
        monster = new int[4][4][8];
        egg = new int[4][4][8];
        dieMap = new int[4][4];
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            int d = Integer.parseInt(st.nextToken())-1;
            monster[x][y][d]++;
        }
        //printMap();
        for(int i=0;i<t;++i){
            System.out.printf("T : %d ===========\n", i+1);
            // 1. 몬스터 복제 시도
            makeEgg();
            //printMap();
            moveMonster();
            //printMap();
            moveF();
            //printMap();
            dieMapUpdate();
            printMap();
            eggToMonster();
            printMap();
        }
        System.out.println(getAllMonster());
    }
    static int getAllMonster(){
        int result = 0;
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                result += getMonsterNum(i, j);
            }
        }
        return result;
    }
    static void makeEgg(){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                for(int k=0;k<8;++k){
                    egg[i][j][k] = monster[i][j][k];
                }
            }
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<4) && (y<4);
    }
    static boolean canMove(int x, int y){
        return checkRange(x, y) && (dieMap[x][y] == 0) && !((x == px) && (y == py));
    }
    static void moveMonster(){
        int[][][] temp  = new int[4][4][8];
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                for(int k=0;k<8;++k){
                    if(monster[i][j][k] == 0) continue;
                    boolean move = false;
                    for(int d=0;d<8;++d){
                        int dd = (k + d) % 8;
                        int nx = i + mdx[dd];
                        int ny = j + mdy[dd];
                        if(canMove(nx, ny)){
                            move = true;
                            temp[nx][ny][dd] += monster[i][j][k];
                            break;
                        }
                    }
                    if(!move){
                        temp[i][j][k] += monster[i][j][k];
                    }
                }
            }
        }
        // 몬스터 상태 변경
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                for(int k=0;k<8;++k){
                    monster[i][j][k] = temp[i][j][k];
                }
            }
        }
    }
    static void moveF(){
        // 팩맨이 이동할 경로 탐색
        int maxMonster = -1;
        int m1x=-1,m1y=-1,m2x=-1,m2y=-1,m3x=-1,m3y=-1;
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                for(int k=0;k<4;++k){
                    int n1x = px + pdx[i];
                    int n2x = n1x + pdx[j];
                    int n3x = n2x + pdx[k];
                    int n1y = py + pdy[i];
                    int n2y = n1y + pdy[j];
                    int n3y = n2y + pdy[k];
                    if(checkRange(n1x, n1y) && checkRange(n2x, n2y) && checkRange(n3x, n3y)){
                        int eatMonster = getMonsterNum(n1x, n1y) + getMonsterNum(n2x, n2y) + getMonsterNum(n3x, n3y);
                        if((n1x == n3x) && (n1y == n3y)){
                            eatMonster -= getMonsterNum(n1x, n1y);
                        }
                        if(eatMonster > maxMonster){
                            maxMonster = eatMonster;
                            m1x = n1x;
                            m1y = n1y;
                            m2x = n2x;
                            m2y = n2y;
                            m3x = n3x;
                            m3y = n3y;
                        }
                    }
                }
            }
        }
        System.out.printf("(%d,%d) -> (%d,%d) -> (%d,%d) -> (%d,%d)\n",px,py,m1x,m1y,m2x,m2y,m3x,m3y);
        // 경로에 있는 몬스터 죽이기 
        if(getMonsterNum(m1x, m1y) > 0) {
            dieMap[m1x][m1y] = 3;
            killMonster(m1x, m1y);
        }
        if(getMonsterNum(m2x, m2y) > 0) {
            dieMap[m2x][m2y] = 3;
            killMonster(m2x, m2y);
        }
        if(getMonsterNum(m3x, m3y) > 0) {
            dieMap[m3x][m3y] = 3;
            killMonster(m3x, m3y);
        }
        // 팩맨 마지막 이동한 곳으로 팩맥 좌표 업데이트
        px = m3x;
        py = m3y;

    }
    static void printMap(){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                System.out.printf("%d ",getMonsterNum(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }
    static void killMonster(int x, int y){
        for(int i=0;i<8;++i){
            monster[x][y][i] = 0;
        }
    }
    static int getMonsterNum(int x, int y){
        int result = 0;
        for(int i=0;i<8;++i){
            result += monster[x][y][i];
        }
        return result;
    }
    static void dieMapUpdate(){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                if(dieMap[i][j] > 0) dieMap[i][j] -= 1;
            }
        }
    }
    static void eggToMonster(){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                for(int k=0;k<8;++k){
                    monster[i][j][k] += egg[i][j][k];
                }
            }
        }
    }

}
