package other;
import java.util.Scanner;
public class _9613_GcdSum {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t;
        t = scan.nextInt();
        for(int i=0;i<t;i++){
            int n;
            long result=0;
            n = scan.nextInt();
            int[] num = new int[n];
            for(int j=0;j<n;j++){
                num[j] = scan.nextInt();
            }
            for(int k = 0; k<n-1;k++){
                for(int l = k+1; l<n;l++){
                    result += gcd(num[k],num[l]);
                }
            }
            System.out.println(result);
        }

    }
    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}
