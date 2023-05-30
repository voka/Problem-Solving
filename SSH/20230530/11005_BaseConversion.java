package other;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class _11005_BaseConversion {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long n,b;
        n = scan.nextLong();
        b = scan.nextLong();
        List<Long> list = new ArrayList<Long>();

        while( (n/b)>0){
            list.add(n%b);
            n = n/b;
        }
        list.add(n);
        for(int i=list.size()-1;i>=0;i--){
            long result = list.get(i);
            if(result<10){
                System.out.print(result);
            }
            else if(result>=10){
                System.out.print((char)('A'+(result-10)));
            }
        }
    }
}
