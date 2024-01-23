import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair{
    int x;
    int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int n, m, k; // 판 크기, 팀 수, 라운드 수
    static int[][] board, teamX, teamY;
    static int[] score, dx = {1,0,-1,0}, dy = {0,1,0,-1}, members;
    static boolean[] teamIsReversed, teamIsFull;
    static boolean[][] visited;

    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x <n) &&(y <n);
    }
    static void initBfs(int x, int y, int teamNumber){
        int memberIndex = 1;
        Queue<Pair> my = new LinkedList<>();
        my.add(new Pair(x,y));
        while(!my.isEmpty()){
            Pair cur = my.poll();
            for(int i=0;i<4;++i){
                int nx = cur.x + dx[i];
                int ny = cur.y+ dy[i];
                if(checkRange(nx, ny) && board[nx][ny] != 0 && !visited[nx][ny]){
                    visited[nx][ny] = true;
                    if(board[nx][ny] == 2){
                        teamX[teamNumber][memberIndex] = nx;
                        teamY[teamNumber][memberIndex] = ny; 
                        memberIndex++;
                        my.add(new Pair(nx, ny));
                    }
                    if(board[nx][ny] == 3){
                        if(board[cur.x][cur.y] != 1){
                            teamX[teamNumber][memberIndex] = nx;
                            teamY[teamNumber][memberIndex] = ny;
                            members[teamNumber] = memberIndex;
                        }else{
                            teamIsFull[teamNumber] = true;
                            visited[nx][ny] = false;
                        }
                    }
                }
            }
        }   
    }
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken()); 
        int teamNumber = 0;
        teamIsReversed = new boolean[m];
        teamX = new int[m][n*n + 1];
        teamY = new int[m][n*n + 1];
        board = new int[n][n];
        score = new int[m];
        members = new int[m];
        teamIsFull = new boolean[m];
        visited = new boolean[n][n];
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n;++j){
                board[i][j] = Integer.parseInt(st.nextToken()); 
            }
        }
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(board[i][j] == 1 && !visited[i][j]){
                    visited[i][j] = true;
                    teamX[teamNumber][0] = i;
                    teamY[teamNumber][0] = j;
                    initBfs(i, j, teamNumber);
                    teamNumber += 1;
                }
            }
        }
        // for(int i=0;i<m;++i){
        //     System.out.printf("tema is full ==> %b\n",teamIsFull[i]);
        // }
        //printMember();s
        // 라운드 진행
        for(int rk = 1; rk <= k; ++rk ){
            //System.out.printf("\nROUND ==========> %d\n",rk);
            int round = (rk-1) % (4*n) + 1;
             // 1. 팀별 이동
            for(int num=0;num<m;++num){
                teamMove(num);
            }

            // 2. 공 던지기
            if(round <= n){
                for(int i=0;i<n;++i){
                    if(memberGetBall(round - 1, i)) break; // 공을 잡았다.
                }

            }else if(round <= 2 * n){
                for(int i=n-1;i>=0;--i){
                    if(memberGetBall(i , (round - n - 1))) break;
                }

            }else if(round <= 3 * n){
                for(int i=n-1;i>=0;--i){
                    if(memberGetBall(3*n - round, i)) break;
                }
            }else{
                for(int i=0;i<n;++i){
                    if(memberGetBall(i,4*n - round)) break;

                }
            }
            //printBoard();
            //printScore();
        }
        int totalScore = 0;
        for(int i=0;i<m;++i){
            totalScore += score[i];
        }
        System.out.println(totalScore);
    }
    static void printScore(){
        int total = 0; 
        for(int i=0;i<m;++i){
            total += score[i];
        }
        System.out.printf("%d\n",total);
    }
    static void printMember(){
        for(int i=0;i<m;++i){
            System.out.printf("Team number ==> %d, memberNums => %d\n", i, members[i]);
            for(int j=0;j<=members[i];++j){
                System.out.printf("member Index -> %d, x: %d, y : %d\n",j, teamX[i][j], teamY[i][j]);
            }
        }
    }
    static void printBoard(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%d ",board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printVisited(){
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                System.out.printf("%b ",visited[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    static void teamMove(int num){
        visited = new boolean[n][n];
        if(!teamIsReversed[num]){ // 방향이 바뀌지 않았다면 머리사람 따라 이동
            for(int i=0;i<=members[num];++i){
                int cx = teamX[num][i];
                int cy = teamY[num][i];
                for(int j=0;j<4;++j){
                    int nx = cx + dx[j];
                    int ny = cy + dy[j];
                    if(checkRange(nx, ny)){
                        if(board[nx][ny] == -1 || (board[nx][ny] == 4 && i == 0) || (board[nx][ny] == 3 && i == 0)){
                            if(i == 0) board[nx][ny] = 1; 
                            else if(i == members[num]) board[nx][ny] = 3;
                            else board[nx][ny] = 2;
                            if(i != members[num]) board[cx][cy] = -1;
                            else {
                                if(teamIsFull[num]) board[cx][cy] = 1;
                                else board[cx][cy] = 4;
                            }
                            teamX[num][i] = nx;
                            teamY[num][i] = ny; 
                            break;
                        }
                    }
                }
            }
        }else{// 방향이 바꼈다면 꼬리 사람 따라서 움직임.
            for(int i=members[num];i>=0;--i){
                int cx = teamX[num][i];
                int cy = teamY[num][i];
                for(int j=0;j<4;++j){
                    int nx = cx + dx[j];
                    int ny = cy + dy[j];
                    if(checkRange(nx, ny)){
                        if(board[nx][ny] == -1 || (board[nx][ny] == 4 && i == members[num]) || (board[nx][ny] == 1 && i == members[num])){
                            if(i == 0) board[nx][ny] = 1; 
                            else if(i == members[num]) board[nx][ny] = 3;
                            else board[nx][ny] = 2;
                            if(i != 0) board[cx][cy] = -1;
                            else {
                                if(teamIsFull[num]) board[cx][cy] = 3;
                                else board[cx][cy] = 4;
                            }
                            teamX[num][i] = nx;
                            teamY[num][i] = ny; 
                            break;
                        }
                    }
                }
            }
            
        }
    }
    static boolean memberGetBall(int x, int y){
        for(int num = 0; num < m ;++num){
            for(int i=0;i<=members[num];++i){
                if(teamX[num][i] == x && teamY[num][i] == y){
                    if(teamIsReversed[num]){// 바뀐 상태라면
                        int cur = members[num] - i + 1;
                        score[num] += cur * cur;
                    }else score[num] += (i+1) * (i+1); // 점수 획득
                    teamIsReversed[num] = !teamIsReversed[num];// 방향 전환
                    return true;
                }
            }
        }
        return false;
    }
}
