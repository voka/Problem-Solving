/*
 * 기출 문제 / 삼성 SW 역량테스트 2024 하반기 오전 1번 문제 / 미지의 공간 탈출
 * 
 * 1. 3차원, 2차원 지도의 장애물 입력 확인 (0: 빈공간, 1:장애물)
 * 2. 시간 이상 현상 확산 
 * 3. 길찾기
 * 
 */
import java.io.*;
import java.util.*;
class My implements Comparable<My>{
    int x; int y; int t;
    public My(int x, int y, int t){
        this.x = x;
        this.y = y;
        this.t = t;
    }
    @Override
    public int compareTo(My o) {
        if(this.t < o.t) return -1;
        return 1;
    }

}
public class Main {
    static int N,M,F;
    static int sx,sy,tx,ty; // 타임머신 위치, 탈출구 위치
    static int[] dy = {1,-1,0,0}, dx = {0,0,1,-1}; // 2차원 평면 길찾기
    static int[][] map = new int[20][20];
    static int[][] tdmap;
    /* 
    정육면체 전개도 생각해서 

    00  11  00
    11  11  11
    00  11  00
    
    위에서 11 되어 있는 부분만 입력받고 00 있는 부분을 지나갈때는 시간 안지나게 하는거지

    */ 
    static int[] r,c,d,v; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        F = Integer.parseInt(st.nextToken());
        tdmap = new int[3*M][3*M];
        for(int i=0;i<3*M;++i){
            for(int j=0;j<3*M;++j){
                tdmap[i][j] = 7;
            }
        }
        r = new int[F]; c = new int[F]; d = new int[F]; v = new int[F];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;++j){
                int temp = Integer.parseInt(st.nextToken());
                map[i][j] = temp;
                if(temp == 4){ //탈출구
                    tx = i; ty = j;
                }
                if(temp == 2){ // 타임머신 위치 -> 출발지
                    sx = i; sy = j;
                }
            }
        }
        for(int k=0;k<5;++k){
            for(int i=0;i<M;++i){
                st = new StringTokenizer(br.readLine());
                for(int j=0;j<M;++j){
                    if(k == 0){ // 동쪽
                        tdmap[M + i][2*M + j] = Integer.parseInt(st.nextToken());
                    }else if(k == 1){ // 서쪽
                        tdmap[M + i][j] = Integer.parseInt(st.nextToken());
                    }else if(k == 2){ // 남쪽
                        tdmap[2*M + i][M + j] = Integer.parseInt(st.nextToken());
                    }else if(k == 3){ // 북쪽
                        tdmap[i][M + j] = Integer.parseInt(st.nextToken());
                    }else{
                        tdmap[M + i][M + j] = Integer.parseInt(st.nextToken());
                    }
                }
            }
        }
        for(int i=0;i<F;++i){
            st = new StringTokenizer(br.readLine());
            r[i] = Integer.parseInt(st.nextToken());
            c[i] = Integer.parseInt(st.nextToken());
            d[i] = Integer.parseInt(st.nextToken());
            v[i] = Integer.parseInt(st.nextToken());
        }

        solve();
        
    }
    static void print3d(){
        for(int i=0;i<3*M;++i){
            for(int j=0;j<3*M;++j){
                System.out.printf("%d ", tdmap[i][j]);
            }
            System.out.println();
        }
    }
    static void solve(){
        PriorityQueue<My> pq = new PriorityQueue<>();
        pq.add(new My(sx,sy,0));
        pq.add(new My(sx,sy,0));
        pq.add(new My(sx,sy,0));
        pq.add(new My(sx,sy,0));
        pq.add(new My(sx,sy,2));
        pq.add(new My(sx,sy,4));
        while(!pq.isEmpty()){
            My temp = pq.poll();
            System.out.println(temp.t);
        }


    }
    static void spread(){

    }
}
