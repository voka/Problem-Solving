import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int N, K;
    static int[] scores;
    // 최소 점수가 기준 점 이상이 되는지 확인 하는 함수 -> 그룹을 k개로 나눌 수 있는지
    public static boolean getMaxScore(int minScore){
        int cnt = 0;// 그룹 개수
        int score = 0;// 점수 저장할 곳
        for(int i=0;i<N;++i){
            score += scores[i];
            if(score >= minScore){
                cnt += 1; 
                score = 0;
            }
        }
        return cnt >= K ;
    }
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        N = Integer.parseInt(temp[0]);
        K = Integer.parseInt(temp[1]);
        temp = br.readLine().split(" ");
        scores = new int[N];
        for(int i=0;i<N;++i){
            scores[i] = Integer.parseInt(temp[i]);
        }
        int start = 0; // 얻을 수 있는 최소 점수
        int mid = 0;
        int end = 2000001; // 얻을 수 있는 최대 점수
        while(start <= end){
            mid = (start + end) / 2; // 중간 값
            if(getMaxScore(mid)){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        //System.out.printf("\nstart : %d, mid : %d, end : %d\n", start, mid, end);
        System.out.println(end);
    }   
}
