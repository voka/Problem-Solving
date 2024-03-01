import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Pair{
    int[][] map;
    int[][] nameMap;
    public Pair(int[][] map, int[][] nameMap){
        this.map = map;
        this.nameMap = nameMap;
    }
}
public class Main {
    static int n, ans = Integer.MIN_VALUE;
    static int[] sx = {0,0,1,1}, sy = {0,1,0,1}; // 재료의 시작 위치
    static int[][][][] ingredientMap;//방향에 따른 재료
    static int[][][][] ingredientNameMap;// 재료 이름 배열
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        ingredientMap = new int[n][4][4][4];// 회전 방향에 따른 2차원 배열 
        ingredientNameMap = new int[n][4][4][4];
        visited = new boolean[n];
        StringTokenizer st;
        for(int k=0;k<n;++k){
            for(int i=0;i<4;++i){
                st = new StringTokenizer(br.readLine());
                for(int j=0;j<4;++j){
                    ingredientMap[k][0][i][j] = Integer.parseInt(st.nextToken());
                }
            }
            for(int i=0;i<4;++i){
                st = new StringTokenizer(br.readLine());
                for(int j=0;j<4;++j){
                    ingredientNameMap[k][0][i][j] = st.nextToken().charAt(0) - 'A';
                }
            }
        }
        makeTurnArray();

        int[][] map = new int[5][5];
        int[][] nameMap = new int[5][5];
        boolean[] visit = new boolean[n];
        getMaxScore(map, nameMap, 0);     
        System.out.println(ans);
    }
    static void getMaxScore(int[][] map, int[][] nameMap, int cnt){
        if(cnt == 3){
            ans = Math.max(ans,getScore(map, nameMap));
            return;
        }
        for(int i=0;i<n;++i){
            if(!visited[i]){
                visited[i] = true;

                for(int si=0;si<4;++si){
                    for(int di=0;di<4;++di){
                        Pair cur = sumArray(map, nameMap, i, di, si);
                        getMaxScore(cur.map, cur.nameMap, cnt+1);
                    }
                }
                visited[i] = false;
            }
        }

    }
    static void makeTurnArray(){
        for(int i=0;i<n;++i){
            for(int j=0;j<3;++j){
                ingredientMap[i][j+1] = turnRight(ingredientMap[i][j]);
                ingredientNameMap[i][j+1] = turnRight(ingredientNameMap[i][j]);
            }
        }
        
    }
    static int[][] turnRight(int[][] k){
        int[][] temp = new int[4][4];
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                temp[j][3-i] = k[i][j];
            }
        }
        return temp;
    }
    static int getScore(int[][] curMap, int[][] nameMap){
        int R = 0, B = 0, G = 0, Y = 0;
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                if(nameMap[i][j] == 'R'-'A'){
                    R += curMap[i][j];
                }else if(nameMap[i][j] == 'B'-'A'){
                    B += curMap[i][j];
                }else if(nameMap[i][j] == 'G'-'A'){
                    G += curMap[i][j];
                }else if(nameMap[i][j] == 'Y'-'A'){
                    Y += curMap[i][j];
                }
            } 
        }
        return 7 * R + 5 * B + 3 * G + 2 * Y;
    }
    // si : 시작 위치 인덱스 , di : 회전한 방향 인덱스, ii : 재료 인덱스
    static Pair sumArray(int[][] curMap,int[][] nameMap,  int ii, int di, int si){
        int[][] newMap = new int[5][5];
        int[][] newNameMap = new int[5][5];
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                if((i >= sx[si] && i < sx[si] + 4) && (j >= sy[si] && j < sy[si]+4)){
                    int val = curMap[i][j] + ingredientMap[ii][di][i-sx[si]][j-sy[si]];
                    if(val < 0) val = 0;
                    if(val > 9) val = 9; 
                    newMap[i][j] = val;
                    int name = ingredientNameMap[ii][di][i-sx[si]][j-sy[si]];
                    if(name != 'W' - 'A' ){
                        newNameMap[i][j] = name;
                    }else{
                        newNameMap[i][j] = nameMap[i][j];
                    }
                }else{
                    newMap[i][j] = curMap[i][j];
                    newNameMap[i][j] = nameMap[i][j];
                }
            }
        }
        return new Pair(newMap, newNameMap);
    }
}