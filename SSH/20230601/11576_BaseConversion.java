package other;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class _11576_BaseConversion {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        List<Integer> list = new ArrayList<Integer>();
        int a, b, a_length, sum=0;
        a = scan.nextInt();
        b = scan.nextInt();
        a_length = scan.nextInt();
        int[] a_digit = new int[a_length];
        for(int i=0;i<a_length;i++){
            a_digit[i] = scan.nextInt();
        }
        for(int i=a_length-1;i>=0;i--){
            sum += a_digit[i]*Math.pow(a,a_length-1-i);
        }
        while((sum / b) >0){
            list.add(sum%b);
            sum /=b;
        }
        list.add(sum);
        for(int i = list.size()-1;i>=0;i--) {
            if (i == 0)
                System.out.print(list.get(i));
            else {
                System.out.print(list.get(i) + " ");
            }
        }

    }
}
