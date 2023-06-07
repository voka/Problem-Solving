package other;
import java.util.Scanner;
public class _10872_Factorial {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n=scan.nextInt();

        System.out.println(factorial(n));
    }
    public static int factorial(int n){
        if(n==1)
            return 1;
        if(n==0)
            return 1;
        return n*factorial(n-1);
    }
}
