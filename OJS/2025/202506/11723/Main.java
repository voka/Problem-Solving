import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int M = Integer.parseInt(br.readLine());
        int x;
        Set<Integer> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        while(M>0){
            M--;
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            switch (command) {
                case "add":
                    x = Integer.parseInt(st.nextToken());
                    if(!set.contains(x)){
                        set.add(x);
                    }
                    break;
                case "remove":
                    x = Integer.parseInt(st.nextToken());
                    if(set.contains(x)){
                        set.remove(x);
                    }
                    break;
                case "check":
                    x = Integer.parseInt(st.nextToken());
                    if(!set.contains(x)){
                        sb.append(0).append("\n");
                    }else{
                        sb.append(1).append("\n");
                    }
                    break;
                case "toggle":
                    x = Integer.parseInt(st.nextToken());
                    if(!set.contains(x)){
                        set.add(x);
                    }else{
                        set.remove(x);
                    }
                    break;
                case "all":
                    set.clear();
                    for(int i=1;i<=20;++i){
                        set.add(i);
                    }
                    break;
                case "empty":
                    set.clear();
                    break;
            
                default:
                    break;
            }

        }
        System.out.println(sb.toString());
        
    }    
}
