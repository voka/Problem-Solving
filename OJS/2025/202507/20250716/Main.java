import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        Stack<Character> st = new Stack<>();
        String s = br.readLine();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();++i){
            if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'){
                sb.append(s.charAt(i));
            }else{
                if(s.charAt(i) == '('){
                    st.add(s.charAt(i));
                }else if(s.charAt(i) == '*' || s.charAt(i) == '/'){ // 
                    while(!st.empty() && (st.peek() == '*' || st.peek() == '/')){
                        sb.append(st.pop());
                    }
                    st.add(s.charAt(i));
                }else if(s.charAt(i) == '+' || s.charAt(i) == '-'){
                    while (!st.empty() && st.peek() != '(') {
                        sb.append(st.pop());
                    }
                    st.add(s.charAt(i));
                }else if(s.charAt(i) == ')'){
                    while(!st.empty() && st.peek() != '('){
                        sb.append(st.pop());
                    }
                    st.pop();
                }
            }
            
        }
        while(!st.empty()){
            sb.append(st.peek());
            st.pop();
        }
        System.out.println(sb.toString());
    }
}
