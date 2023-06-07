package other;
import java.util.Scanner;
import java.math.*;

public class _2004_Combination0Num {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long n = scan.nextInt();
        long m = scan.nextInt();

        long count2 = two_pow(n)-two_pow(n-m)-two_pow(m);
        long count5 = five_pow(n)-five_pow(n-m)-five_pow(m);
        System.out.println(Math.min(count2, count5));
    }
    public static long five_pow(long num){
        int count = 0;
        while(num >=5){
            count += num/5;
            num /=5;
        }
        return count;
    }
    public static long two_pow(long num){
        int count = 0;
        while(num >=2){
            count += num/2;
            num /=2;
        }
        return count;
    }
}
