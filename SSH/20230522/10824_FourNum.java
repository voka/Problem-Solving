package other;
import java.util.Scanner;
public class _10824_FourNum {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] str = new String[4];

        for(int i=0;i<4;i++)
            str[i] = scan.next();

        System.out.println(Long.parseLong(str[0]+str[1]) + Long.parseLong(str[2]+str[3]));
    }
}
