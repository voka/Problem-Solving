import java.io.*;
import java.util.*;
public class Main {
    static int n;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        // 여기에 코드를 작성해주세요.
        Stack<Integer> my = new Stack();
        int ans = 0;
        for(int i=0;i<n;++i){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            while(!my.isEmpty() && my.peek() > y){
                my.pop();
                ans++;
            }
            if(y == 0) continue;
            if(my.isEmpty() || my.peek() != y) my.push(y);
        }
        System.out.println(ans+my.size());
    }
}