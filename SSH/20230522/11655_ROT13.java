package other;
import java.util.Scanner;
public class _11655_ROT13 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();

        for(int i=0;i<str.length();i++){
            char ch = str.charAt(i);
            if(ch >= 'a' && ch <= 'z'){
                if(ch <='m'){
                    ch +=13;
                }
                else{
                    int tmp = 12 - ('z'-ch);
                    ch = (char) ('a'+tmp);
                }
            }
            else if(ch >= 'A' && ch <= 'Z'){
                if(ch <='M'){
                    ch +=13;
                }
                else{
                    int tmp = 12 - ('Z' - ch);
                    ch = (char)('A'+tmp);
                }
            }
            System.out.print(ch);
        }
    }
}
