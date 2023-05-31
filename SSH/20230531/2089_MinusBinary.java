package other;
import java.util.Scanner;
public class _2089_MinusBinary {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int n =scan.nextInt();

        while(true){
            if(n==0)
                break;
            if(n < 0){
                if( (-n % 2) ==0) {
                    sb.append( n%-2);
                    n = (n/-2);
                }
                else {
                    sb.append(-n % -2);
                    n = (n / -2) + 1;
                }
            }
            else if (n>0){
                sb.append(n % -2);
                n = n/-2;
            }

        }
        if(sb.length() == 0){
            sb.append(0);
        }
        System.out.println(sb.reverse());
    }
}
