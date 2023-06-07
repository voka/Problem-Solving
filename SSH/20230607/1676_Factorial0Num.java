package other;
import java.util.Scanner;
public class _1676_Factorial0Num {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int count2 = 0, count5 = 0;
        for(int i=n;i>=1;i--){
            int num = i;
            while((num%5) == 0){
                count5++;
                num /=5;
            }
        }
        System.out.println(count5);
    }
}
