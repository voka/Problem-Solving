package other;
import java.util.Scanner;
public class _1929_FindPrime2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int m = scan.nextInt();
        int n = scan.nextInt();

        int [] prime = new int[n+1];
        prime[0] = 1;
        prime[1] = 1;
        for(int i=2;i<=n;i++){
            for(int j=2*i;j<=n;j+=i){
                prime[j] = 1;

            }
        }
        for(int i=m;i<=n;i++){
            if(prime[i]!=1)
                System.out.println(i);
        }

    }
}
