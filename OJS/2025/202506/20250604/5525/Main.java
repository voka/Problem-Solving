import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());
        String s = br.readLine();
        int cnt = 0;
        int result = 0;
        for(int i=1;i<M-1;){
            if(s.charAt(i) == 'O' && s.charAt(i+1) == 'I'){
                cnt++;
                if(cnt == N){
                    if(s.charAt(i - cnt*2 + 1) == 'I'){
                        result++;
                    }
                    cnt--;
                }
                i+=2;
            }else{
                cnt = 0;
                i++;
            }
        }
        System.out.println(result);
    }
}
