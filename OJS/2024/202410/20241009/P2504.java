import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
public class P2504 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        Stack<Character> stack = new Stack<>();
        int answer = 0;
        int tmp = 1;
        boolean re = false;
        char pre = '1';
        for(char c : s.toCharArray()){
            if(c == '('){
                tmp *=2;
                stack.add(c);
            }
            else if(c == '['){
                tmp *=3;
                stack.add(c);
            }
            else if(c == ')'){
                if(stack.empty() || stack.peek() == '['){
                    re = true;
                    break;
                }
                if(pre == '(') answer += tmp;
                stack.pop();
                tmp /= 2;
            }
            else if(c == ']'){

                if(stack.empty() || stack.peek() == '('){
                    re = true;
                    break;
                }
                if(pre == '[') answer += tmp;
                stack.pop();
                tmp /= 3;
            }
            pre = c;
        }
        if ( re || !stack.isEmpty()) System.out.println(0);
        else System.out.println(answer);    
    }
}
