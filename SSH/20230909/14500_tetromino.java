package BruteForce;
import java.io.*;
import java.util.StringTokenizer;

public class _14500_tetromino {
    static int N,M;
    static int[][] map;

    static int[][][] block = new int[][][]{
            {{0,0},{1,0},{0,1},{1,1}},

            {{0,0},{1,0},{2,0},{3,0}},
            {{0,0},{0,1},{0,2},{0,3}},

            {{0,0},{1,0},{2,0},{2,1}},
            {{0,0},{0,1},{0,2},{-1,2}},
            {{0,0},{-1,0},{-2,0},{-2,-1}},
            {{0,0},{0,-1},{0,-2},{1,-2}},

            {{0,0},{1,0},{2,0},{2,-1}},
            {{0,0},{0,1},{0,2},{1,2}},
            {{0,0},{-1,0},{-2,0},{-2,1}},
            {{0,0},{0,-1},{0,-2},{-1,-2}},

            {{0,0},{0,1},{0,2},{1,1}},
            {{0,0},{0,1},{0,2},{-1,1}},
            {{0,0},{1,0},{2,0},{1,1}},
            {{0,0},{1,0},{2,0},{1,-1}},

            {{0,0},{1,0},{1,1},{2,1}},
            {{0,0},{1,0},{1,-1},{2,-1}},
            {{0,0},{0,-1},{1,-1},{1,-2}},
            {{0,0},{0,1},{1,1},{1,2}},

    };
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                for(int k=0;k<block.length;k++){
                    int sum = 0;
                    boolean flag = true;
                    for(int z=0;z<4;z++){
                        int nx = i+block[k][z][0];
                        int ny = j+block[k][z][1];
                        if(nx<0 || ny < 0 || nx >=N || ny >=M){
                            flag = false;
                            break;
                        }
                        sum +=map[nx][ny];
                    }
                    if(!flag) continue;
                    ans = Math.max(ans,sum);
                }
            }
        }

        System.out.println(ans);
    }
}
