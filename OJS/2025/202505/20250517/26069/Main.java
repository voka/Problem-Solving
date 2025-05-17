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
        set.add("ChongChong");
        for(int i=0;i<N;++i){
            String[] input = br.readLine().split(" ");
            if(set.contains(input[0])){
                set.add(input[1]);
            }
            if(set.contains(input[1])){
                set.add(input[0]);
            }
            
        }
        System.out.println(set.size());

    }
}
