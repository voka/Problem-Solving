import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ20061 {
    static boolean[][] green = new boolean[6][4];
    static boolean[][] blue = new boolean[4][6];

    public static void printGreen(){
        System.out.println("GREEN=========");
        for(int y = 0;y <6;++y){
            for(int x = 0;x<4;++x){
                System.out.printf("%b, ",green[y][x]);
            }
            System.out.println();
        }
    }
    public static void printBlue(){
        System.out.println("BLUE=========");
        for(int y = 0;y <4;++y){
            for(int x = 0; x<6;++x){
                System.out.printf("%b, ",blue[y][x]);
            }
            System.out.println();
        }
    }
    //green x 축 초기화
    public static void cleanGreenY(int y){
        // 0으로 변경하기
        for(int x = 0;x<4;++x){
            green[y][x] = false;
        }
    }
    public static void cleanGreen(int y, int c){
        // y 위쪽에 있는 값들 한칸씩 밑으로 내리기
        for(int i=y-c;i>=0;i--){
            for(int x = 0; x < 4;++x){
                if(green[i][x]){
                    green[i+c][x] = green[i][x];
                    green[i][x] = false;
                }
            }
        }
    }
    // 점수 계산
    public static int checkGreen(){
        int score = 0; 
        for(int y = 0;y <6;++y){
            boolean flag = false;
            for(int x = 0;x<4;++x){
                if(!green[y][x]){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                score++;
                cleanGreenY(y);
                cleanGreen(y, 1);
            }
        }
        
        return score;
    }
    // green의 특별행 행에 박스가 있는지 확인
    public static void updateGreen(){
        int cnt = 0;
        for(int y=0;y<2;++y){
            for(int x=0;x<4;++x){
                if(green[y][x]){
                    cnt++;
                    break;
                }
            }
        }
        if(cnt == 1){
            cleanGreenY(5);
            cleanGreen(5, 1);
        }
        else if(cnt == 2){
            cleanGreenY(4);
            cleanGreenY(5);
            cleanGreen(5, 2);
        }
    }

    //blue y 열 초기화
    public static void cleanBlueX(int x){
        for(int y = 0;y<4;++y){
            blue[y][x] = false;
        }
    }
    public static void cleanBlue(int x, int c){
          // x 오른쪽에 있는 값들 한칸씩 오른쪽으로 옮기기
        for(int i=x-c;i>=0;i--){
            for(int y = 0; y < 4;++y){
                if(blue[y][i]){
                    blue[y][i+c] = blue[y][i];
                    blue[y][i] = false;
                }
            }
        }
    }
    // 점수 계산
    public static int checkBlue(){
        int score = 0; 
        for(int x = 0;x <6;++x){
            boolean flag = false;
            for(int y = 0;y<4;++y){
                if(!blue[y][x]){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                score++;
                cleanBlueX(x);
                cleanBlue(x,1);
            }
        }
        return score;
    }
    // Blue의 특별행 행에 박스가 있는지 확인
    public static void updateBlue(){
        int cnt = 0;
        for(int x=0;x<2;++x){
            for(int y=0;y<4;++y){
                if(blue[y][x]){
                    cnt++;
                    break;
                }
            }
        }
        if(cnt == 1){
            cleanBlueX(5);
            cleanBlue(5, 1);
        }
        else if(cnt == 2){
            cleanBlueX(4);
            cleanBlueX(5);
            cleanBlue(5, 2);
        }
    }

    public static int countTileInGreen(){
        int cnt = 0;
        for(int y=0;y<6;++y){
            for(int x=0;x<4;++x){
                if(green[y][x]){
                    cnt++;
                }
            }
        }
        
        return cnt;
    }
    
    public static int countTileInBlue(){
        int cnt = 0;
        for(int y=0;y<4;++y){
            for(int x=0;x<6;++x){
                if(blue[y][x]){
                    cnt++;
                }
            }
        }
        
        return cnt;
    }
    public static void redToGreen(int t, int y, int x){
        if(t == 1){
            for(int i=0;i<=5;++i){
                if(i == 5||(!green[i][x]&&green[i+1][x])){
                    green[i][x] = true;
                    break;
                }
            }
        }
        else if(t==2){
            for(int i=0;i<=5;++i){
                if(i == 5 || (!green[i][x] && !green[i][x+1])&&(green[i+1][x]||green[i+1][x+1])){
                    green[i][x] = true;
                    green[i][x+1] = true;
                    break;
                }
            }
        }
        else if(t==3){
            for(int i=0;i<=4;++i){
                if(i == 4 || ((!green[i+1][x] && !green[i][x] )&&(green[i+2][x]))){
                    green[i][x] = true;
                    green[i+1][x] = true;
                    break;
                }
            }
        }
    }

    public static void redToBlue(int t, int y, int x){
        if(t == 1){
            for(int i=0;i<=5;++i){
                if(i == 5 || (!blue[y][i] && blue[y][i+1])){
                    blue[y][i] = true;
                    break;
                }
            }
        }
        else if(t==2){
            for(int i=0;i<=4;++i){
                if(i == 4 || ((!blue[y][i+1] || !blue[y][i] )&& (blue[y][i+2]))){
                    blue[y][i] = true;
                    blue[y][i+1] = true;
                    break;
                }
            }
        }
        else if(t==3){
            for(int i=0;i<=5;++i){
                if(i == 5 || (!blue[y][i] && !blue[y+1][i]) && (blue[y][i+1] || blue[y+1][i+1])){
                    blue[y][i] = true;
                    blue[y+1][i] = true;
                    break;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String Ntemp= br.readLine();
        int N = Integer.parseInt(Ntemp);
        int answer = 0, cnt = 0;
        for(int i=0;i<N;++i){
            String[] temp = br.readLine().split(" ");
            int t,x,y;
            t = Integer.parseInt(temp[0]);
            y = Integer.parseInt(temp[1]);
            x = Integer.parseInt(temp[2]);
            //System.out.printf("t : %d, y : %d, x: %d\n",t,y,x);
            redToGreen(t, y, x);
            redToBlue(t, y, x);
            //printBlue();
            // 점수 계산 및 4칸이 찬 타일 옮기기
            answer += checkGreen();
            answer += checkBlue();
            // 특별 칸 확인하기
            updateGreen();
            updateBlue();
            //printGreen();
            //printBlue();
        }
        cnt += countTileInBlue();
        cnt += countTileInGreen();

        System.out.println(answer);
        System.out.println(cnt);
    }   

}
