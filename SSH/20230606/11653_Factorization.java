package other;
import java.util.Scanner;
public class _11653_Factorization {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int tmp = n;
        for(int i=2;i<=n;i++){
            while((tmp % i) == 0){
                System.out.println(i);
                tmp /= i;
            }
        }
    }
}
