import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

class Pair{
    int id; 
    int h;
    public Pair(int id, int h){
        this.id = id;
        this.h = h;
    }
}

public class P2493 {
    static Stack<Pair> stack;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        stack = new Stack<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;++i){
            int t = Integer.parseInt(st.nextToken());
            while(!stack.isEmpty()){
                if(stack.peek().h >= t){
                    System.out.print(stack.peek().id + " ");
                    break;
                }
                stack.pop(); // 이전 탑이 현재 레이저를 받을 수 없으므로 더 앞의 높은 탑를 찾는다
            }
            if(stack.isEmpty()){ // 레이저를 받아줄 탑이 없으므로 
                System.out.print("0 ");
            }
            stack.add(new Pair(i, t));// 현재 탑을 등록
        }
        System.out.println();
    }
}
