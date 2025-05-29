import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dx = {0,-1,0,1}, dy = {1,0,-1,0};
    static int[][] map;
    static int r1,c1,r2,c2;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r1 = Integer.parseInt(st.nextToken());
        c1 = Integer.parseInt(st.nextToken());
        r2 = Integer.parseInt(st.nextToken());
        c2 = Integer.parseInt(st.nextToken());
        int xlen = r2 - r1 + 1;
        int ylen = c2 - c1 + 1;
        map = new int[xlen][ylen];    
        
        int max = 0;
        int x = 5000, y = 5000;
        int num = 1, diff = 1, temp = 0, dir = 0, change = 0;

        while(true){
            if(!check(x,y)) break;
            if(checkRange(x,y)){
                max = Math.max(max, num);
                map[x-5000-r1][y-5000-c1] = num;
            }
            num++;
            x = x + dx[dir];
            y = y + dy[dir];
            temp++;
            if(temp == diff){
                temp = 0;
                dir = (dir+1)%4;
                change++;
                if(change == 2){
                    diff++;
                    change = 0;
                }
            }
        }

        int guide = getNum(max);
        StringBuilder sb = new StringBuilder();
        String[] list = {""," ", "  ", "   ", "    "};
        for(int i=0;i<=r2 - r1;++i){
            for(int j=0;j<=c2 - c1;++j){
                if(j==0){
                    int cur = getNum(map[i][j]);
                    int gap = guide - cur;
                    sb.append(list[gap]).append(map[i][j]);
                }else{
                    int cur = getNum(map[i][j]);
                    int gap = guide - cur + 1;
                    sb.append(list[gap]).append(map[i][j]);
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());


    }   
    static int getNum(int target){
        int t = 0;
        while(target > 0){
            target = target / 10;
            t++;
        }
        return t;
    }
    static boolean check(int x, int y){
        return (x>=0) && (y>=0) && (x<=10000) && (y<=10000);
    }
    static boolean checkRange(int x,int y){
        return (x>=r1+5000) && (y>=c1+5000) && (x<=r2+5000) && (y <=c2+5000);
    }
}
