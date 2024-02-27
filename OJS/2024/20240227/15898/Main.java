import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
public class Main {
    static int n, ans = Integer.MIN_VALUE;
    static int[] sx = {0,0,1,1}, sy = {0,1,0,1}; // 재료의 시작 위치
    static int[][][][] ingredientMap;//방향에 따른 재료
    static int[][][][] ingredientNameMap;// 재료 이름 배열
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        ingredientMap = new int[n][4][4][4];// 회전 방향에 따른 2차원 배열 
        ingredientNameMap = new int[n][4][4][4];
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
                    ingredientNameMap[k][0][i][j] = st.nextToken().charAt(0)-'A';
                }
            }
        }
        makeTurnArray();
        int[][] map = new int[5][5];
        int[][] nameMap = new int[5][5];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(i == j) continue;
                for(int k=0;k<n;++k){
                    if(i == k || j == k) continue;
                    for(int id=0;id<4;++id){
                        for(int is=0;is<4;++is){
                            for(int jd=0;jd<4;++jd){
                                for(int js=0;js<4;++js){
                                    for(int kd=0;kd<4;++kd){
                                        for(int ks=0;ks<4;++ks){
                                            map = new int[5][5];
                                            nameMap = new int[5][5];
                                            sumArray(map, nameMap, i, id, is);
                                            sumArray(map, nameMap, j, jd, js);
                                            sumArray(map, nameMap, k, kd, ks);
                                            int cur = getScore(map, nameMap);
                                            // if(i==1 && id == 3 && is == 0 && j==2 && jd==0 && js==3 && k==0 && kd==3 && ks==2){
                                            //     printMap(map);
                                            //     printMap(nameMap);
                                            //     System.out.printf("%d, %d, %d, %d, %d, %d, %d, %d, %d", i,id,is,j,jd,js,k,kd,ks);
                                            //     System.out.printf(" %d\n", cur);
                                            // }
                                            if(ans < cur){
                                                ans = cur;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(ans);
    }
    static void makeTurnArray(){
        for(int i=0;i<n;++i){
            for(int j=0;j<3;++j){
                ingredientMap[i][j+1] = turnRight(ingredientMap[i][j]);
                ingredientNameMap[i][j+1] = turnRight(ingredientNameMap[i][j]);
            }
        }
        
    }
    static void printMap(int[][] map){
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static int[][] turnRight(int[][] k){
        int[][] temp = new int[4][4];
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                temp[j][3-i] = k[i][j];
                /**
                 * 0, 0 -> 0,3
                 * 0, 1 -> 1,3
                 * 0, 2 -> 2,3
                 * 0, 4 -> 3,3
                 * 
                 * 1, 0 -> 0, 2
                 */
            }
        }
        return temp;
    }
    static void printArr(int[] visit){
        for(int i=0;i<n;++i){
            System.out.printf("%d ",visit[i] );
        }
        System.out.println();
    }
    static void getMaxScore(int[][] scoreMap, int[][] colocMap, int[] visited, int cnt, int s){
        if(cnt == 4){ // 3가지 재료를 모두 넣었다면 점수 계산 후 종료
            ans = Math.max(ans,getScore(scoreMap, colocMap));
            printArr(visited);
            return;
        }
        for(int i=0;i<n;++i){
            if(visited[i] != 0) continue;
            // 재료를 넣기
            visited[i] = cnt;
            getMaxScore(scoreMap, colocMap, visited, cnt+1, s+1);
            // 넘어가기
            visited[i] = 0;
            getMaxScore(scoreMap, colocMap, visited, cnt, s+1);            
        }

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
    static void sumArray(int[][] curMap,int[][] nameMap,  int ii, int di, int si){
        for(int i=sx[si];i<sx[si]+4;++i){
            for(int j=sy[si];j<sy[si]+4;++j){
                // System.out.printf("%d, %d, %d, %d\n", ii, di, i-sx[si], j-sy[si]);
                int val = curMap[i][j] + ingredientMap[ii][di][i-sx[si]][j-sy[si]];
                if(val < 0) val = 0;
                if(val > 9) val = 9; 
                curMap[i][j] = val;
                int name = ingredientNameMap[ii][di][i-sx[si]][j-sy[si]];
                if(name != 'W' - 'A' ){
                    nameMap[i][j] = name;
                }
            }
        }
    }
} 