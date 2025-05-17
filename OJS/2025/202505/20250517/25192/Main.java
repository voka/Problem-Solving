import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Set<String> set = new HashSet<>();
        int N = Integer.parseInt(br.readLine());
        int answer = 0;
        for(int i=0;i<N;++i){
            String input = br.readLine();
            if(input.equals("ENTER")) {
                answer += set.size();
                set.clear();
            }
            else{
                set.add(input);
            }
        }
        answer += set.size();
        System.out.println(answer);

    }
}
