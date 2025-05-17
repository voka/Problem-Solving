import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<String, Integer> map = new HashMap<>();
        int N = Integer.parseInt(br.readLine());
        for(int i=0;i<N;++i){
            String[] input = br.readLine().split("[.]"); 
            map.put(input[1],1 + map.getOrDefault(input[1], 0));
        }
        String[] keys = map.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for(String s : keys){
            System.out.printf("%s %d\n",s, map.get(s));
        }
    }
}
