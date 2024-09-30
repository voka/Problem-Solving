import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] map;
    static boolean[][] del;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        map = new int[5][5];
        del = new boolean[5][5];
        for(int i=0;i<5;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<5;++j){
                int cur = Integer.parseInt(st.nextToken());
                map[i][j] = cur;
            }
        }
        int count = 0;
        boolean end = false;
        for(int i=0;i<5;++i){
            st = new StringTokenizer(br.readLine());
            if(end) continue;
            for(int j=0;j<5;++j){
                if(end) continue;
                int cur = Integer.parseInt(st.nextToken());
                delnum(cur);
                if(check()){
                    end = true;
                }
                count++;
                //printArr();
            }
        }
        System.out.println(count);

    }
    static void printArr(){
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                if(del[i][j]){
                    System.out.printf("o");
                }else{
                    System.out.printf("x");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    static void delnum(int target){
        for(int i=0;i<5;++i){
            for(int j=0;j<5;++j){
                if(map[i][j] == target){
                    del[i][j] = true;
                    return;
                }
            }
        }
    }
    static boolean check(){
        int bingo = 0;
        // 가로 확인
        for(int i=0;i<5;++i){
            int c = 0;
            for(int j=0;j<5;++j){
                if(del[i][j]){
                    c++;
                }else break;
            }
            if(c == 5) bingo++;
        }
        // 세로 확인
        for(int i=0;i<5;++i){
            int c = 0;
            for(int j=0;j<5;++j){
                if(del[j][i]){
                    c++;
                }else break;
            }
            if(c == 5) bingo++;
        }
        // 대각선 확인
        int c = 0;
        for(int i=0;i<5;++i){
            if(del[i][i]){
                c++;
            }else break;
        }
        if(c == 5) bingo++;
        c = 0;
        for(int i=0;i<5;++i){
            if(del[i][4-i]){
                c++;
            }else break;
        }
        if(c == 5) bingo++;
        //System.out.println(bingo);
        if(bingo >= 3) return true;
        return false;
    }   
}
/**
11 12 2 24 10
16 1 13 3 25
6 20 5 21 17
19 4 8 14 9
22 15 7 23 18
5 10 7 16 2
4 22 8 17 13
3 18 1 25 9
12 19 23 14 21
11 24 6 20 15 

 * 
 */