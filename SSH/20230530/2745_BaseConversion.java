package other;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class _2745_BaseConversion {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String n;
        long b, result=0;
        List<Character> list = new ArrayList<Character>();
        n = scan.next();
        b = scan.nextLong();

        for(int i=0;i<n.length();i++){
            list.add(n.charAt(i));
        }
        int length = list.size();
        for(int i=0;i<length;i++){
            char ch = list.get(i);
            if('0'<=ch && ch <='9'){
                result += (ch - '0') * (long)Math.pow(b,length-1-i);
            }
            else if('A' <= ch && ch <= 'Z'){
                result += (ch - 'A' + 10) * (long)Math.pow(b,length-1-i);
            }
        }
        System.out.println(result);
    }
}
