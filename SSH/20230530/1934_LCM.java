package other;
import java.util.Scanner;
public class _1934_LCM {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        for(int i=0;i<n;i++){
            int a, b, gcd, a1, b1;
            a = scan.nextInt();
            b = scan.nextInt();
            gcd = gcd(a,b);
            a1 = a / gcd;
            b1 = b/ gcd;
            System.out.println(a1*b1*gcd);
        }

    }
    public static int gcd(int a, int b){
        int q,r;

        if(a < b){
            int tmp = a;
            a = b;
            b = tmp;
        }

        while( (a % b) != 0){
            q = a / b;
            r = a % b;
            a = b;
            b = r;
        }

        return b;
    }
}
