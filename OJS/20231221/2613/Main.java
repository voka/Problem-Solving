import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int N,M; 
    static int[] list, answerList;
    
    static boolean getMax(int target){
        int cnt = 1, cur = 0;
        for(int i=0;i<N;++i){
            cur += list[i];
            if(cur > target){
                cnt++;
                cur = list[i];
            }
        }
        return cnt <= M;
    }
    static void makeAnswer(int target){
        answerList = new int[M];
        int cur = 0, cnt = 0;
        int aidx = 0;
        for(int i=0;i<N;++i){
            cur += list[i];
            if(cur > target){
                cur = list[i];
                answerList[aidx++] = cnt;
                cnt = 1;
            }else{
                cnt++;
            }
            if(M - aidx == N - i){ // 남은 그룹 개수가 납은 리스트 배열과 같으면 종료
                break;
            }
        }
        for(int i=aidx;i<M;++i){
            answerList[i] = cnt;
            cnt=1;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        N = Integer.parseInt(temp[0]);
        M = Integer.parseInt(temp[1]);
        temp = br.readLine().split(" ");
        list = new int[N];
        int s = 0, e = 300 * 100 + 1,m = 0;
        for(int i=0;i<N;++i){
            list[i] = Integer.parseInt(temp[i]);
            s = Math.max(s,list[i]);
        }
        while(s < e){
            m = (s + e) / 2;
            if(getMax(m)){
                e = m;
            }else{
                s = m + 1;
            }
        }
        makeAnswer(e);
        // System.out.println(s);
        // System.out.println(m);
        System.out.println(e);
        for(int i=0;i<M;++i){
            System.out.printf("%d ",answerList[i]);
        }
    }
}
