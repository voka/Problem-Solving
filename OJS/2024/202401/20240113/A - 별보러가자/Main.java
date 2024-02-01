import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Point{
    int x; 
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int n,m;
    static long[][] dp,distance,maxDistance;
    static List<Point> stars; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        maxDistance = new long[m][m];
        distance = new long[m][m];
        dp = new long [n][m];
        stars = new ArrayList<>();
        for(int i=0;i<m;++i){
            st = new StringTokenizer(br.readLine());
            stars.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        for(int i=0;i<m;++i){
            distance[i][i] = 1;
            for(int j=i+1;j<m;++j){
                distance[i][j] = cal(stars.get(i),stars.get(j));
            }
        }
        for(int i=0;i<m;++i){
            maxDistance[i][i] = distance[i][i];
            for(int j=i+1;j<m;++j){
                maxDistance[i][j] = maxDistance[i][j-1];
                for(int k=i;k<j;++k){
                    maxDistance[i][j] = Math.max(maxDistance[i][j],distance[k][j]);
                    //System.out.printf("%d, %d, %d \n",i,j,k);
                }
            }
        }
        for(int i=0;i<m;++i){
            for(int j=0;j<m;++j){
                System.out.printf("%d ",maxDistance[i][j]);
            }
            System.out.println();
        }
        for(int i=0;i<n;++i){
            for(int j=i;j<m;++j){
                if(i == j) dp[i][j] = i;
                if(i == 0) dp[0][m] = maxDistance[0][m];
            }
        }


    }
    static long cal(Point A, Point B){
        return (long)Math.abs(A.x - B.x) + Math.abs(A.y - B.y);
    }
}
