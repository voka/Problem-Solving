import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
class Point{
    int idx; 
    boolean robot;
    int hp;
    public Point(int idx,int hp){
        this.idx = idx;
        this.hp = hp;
        this.robot = false;
    }
}

public class BOJ20055 {
    
    /** 주의사항
     * 로봇은 올리는 위치에 올려지며 내리는 위치에서 즉시 내려 진다.
     * 앞쪽 로봇부터 이동을 시작해야 하며 내리는 칸에 도착한 로봇들은 즉시 내려 다음 로봇이
     * 이동할 수 있을 것이다.
     * 로봇을 올리거나, 로봇을 이동할 칸의 내구도는 그 즉시 감소한다.
     * 1 단께 에서는 컨베이어 벨트가 회전하고 난 뒤에 로봇이 올라간다. 
     */
    static Point[] belt = new Point[200];
    public static int checkHp(int N){
        int cnt = 0;
        for(int i=0;i<=2*N-1;++i){
            if(belt[i].hp <= 0){
                cnt++;
            }
        }
        return cnt;
    }
    public static void printarr(int N){
        for(int i=0;i<=2*N-1;++i){
            System.out.printf("(%d,%b) ,  ",belt[i].hp,belt[i].robot);
        }
        System.out.println();
    }
    public static void main(String[] args) throws IOException{
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] N_K = br.readLine().split(" ");
        int N,K;
        N = Integer.parseInt(N_K[0]);
        K = Integer.parseInt(N_K[1]);
        String[] hpList = br.readLine().split(" ");
        for(int i=0;i<hpList.length;++i){
            belt[i] = new Point(i, Integer.parseInt(hpList[i]));
        }
        int round = 1;
        int start = 0, out;
        //printarr(N);
        while(true){
            start--; // 회전
            if(start < 0) start = 2*N -1;
            //System.out.printf("start ==> %d\n",start);
            // 내리는 지점 확인
            if(start > N){
                out = start - N - 1;
                //System.out.printf("out: %d\n",out);
                // 내리는 칸의 로봇 내리기
                if(belt[out].robot){
                    belt[out].robot = false;
                } 
                //순방향 탐색
                int cnt = 0, i = out, pre = out;
                while(cnt < N){
                    cnt++;
                    i--;
                    if(i < 0) i = 2*N-1;
                    if(belt[i].robot && !belt[pre].robot && belt[pre].hp >= 1){
                        //이번 칸에 로봇이 있고, 다음칸에 로봇이 없으며, 다음칸의 내구도가 1이상인 겨우
                        belt[i].robot = false;
                        belt[pre].robot = true;
                        belt[pre].hp--;
                    }
                    pre = i;
                }
                // 내리는 칸의 로봇 내리기
                if(belt[out].robot){
                    belt[out].robot = false;
                } 
            }
            else{
                out = start + N - 1 ;
                //System.out.printf("out: %d\n",out);
                // 내리는 칸의 로봇 내리기
                if(belt[out].robot){
                    belt[out].robot = false;
                } 
                
                int cnt = 0, i = out, pre = out;
                //역방향 탐색
                while(cnt < N){
                    cnt++;
                    i--;
                    if(i < 0) i = 2*N-1;
                    if(belt[i].robot && !belt[pre].robot && belt[pre].hp >= 1){
                        //이번 칸에 로봇이 있고, 다음칸에 로봇이 없으며, 다음칸의 내구도가 1이상인 겨우
                        belt[i].robot = false;
                        belt[pre].robot = true;
                        belt[pre].hp--;
                    }
                    pre = i;
                }
                // 내리는 칸의 로봇 내리기
                if(belt[out].robot){
                    belt[out].robot = false;
                } 
            }
            // 올리는 위치 확인
            if(belt[start].hp > 0){
                belt[start].robot = true;
                belt[start].hp--;
            }
            //printarr(N);
            // 내구도 검사 ;
            int cur = checkHp(N);
            if(cur >= K){
                break;
            }
            // round 증가
            round++;
        }
        System.out.println(round);
    }
}