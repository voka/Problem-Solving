package other;
import java.util.Scanner;
public class _1212_OctalToBinary {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String str = scan.next();

        for(int i=0;i<str.length();i++){
            int n = str.charAt(i) -'0';
            StringBuilder tmp = new StringBuilder();
            if(i==0){
                while((n /2)>0){
                    tmp.append(n%2);
                    n /=2;
                }
                tmp.append(n);
                sb.append(tmp.reverse());
            }
            else {
                if (n <= 1) {
                    tmp.append('0').append('0').append(n);
                } else if (n <= 3) {
                    tmp.append('0').append(n / 2).append(n % 2);
                } else if (n <= 7) {
                    tmp.append(n / 4).append((n % 4) / 2).append((n % 4) % 2);
                }
                sb.append(tmp);
            }
        }
        System.out.println(sb);
    }
}
