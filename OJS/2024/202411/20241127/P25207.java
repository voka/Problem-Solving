import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P25207 {
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        float n = 0;// 학점 수
        float total = 0;//전체 점수
        for(int i=0;i<20;++i){
            st = new StringTokenizer(br.readLine());
            st.nextToken(); // 과목명
            float score = Float.parseFloat(st.nextToken()); // 학점
            String s = st.nextToken(); // 등급
            switch (s) {
                case "A+":
                    total += score * 4.5;
                    n += score;
                    break;
                case "A0":
                    total += score * 4;
                    n += score;
                    break;
            
                case "B+":
                    total += score * 3.5;
                    n += score;
                    break;
            
                case "B0":
                    total += score * 3;
                    n += score;
                    break;
            
                case "C+":
                    total += score * 2.5;
                    n += score;
                    break;
            
                case "C0":
                    total += score * 2;
                    n += score;
                    break;
            
                case "D+":
                    total += score * 1.5;
                    n += score;
                    break;
            
                case "D0":
                    total += score * 1;
                    n += score;
                    break;
                case "F":
                    n += score;
                    break;
            
                default:
                    break;
            }
        }
        float result = total / n;
        System.out.printf("%.6f\n",result);

    }
}
