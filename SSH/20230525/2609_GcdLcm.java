package other;
import java.util.Scanner;
public class _2609_GcdLcm {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int a,b;
        int gcd, lcm;
        a = scan.nextInt();
        b = scan.nextInt();
        gcd = findGcd(a,b);
        lcm = findLcm(a,b,gcd);
        System.out.println(gcd);
        System.out.println(lcm);

    }

    public static int findGcd(int a, int b){
        int q, r;
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

    public static int findLcm(int a, int b, int gcd){
        int c,d;
        c = a/gcd;
        d = b/gcd;

        return gcd*c*d;
    }
}
