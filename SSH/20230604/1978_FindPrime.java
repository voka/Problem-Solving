package other;
import java.util.Scanner;
public class _1978_FindPrime {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int count = 0;
        int [] num = new int[n];
        for(int i=0;i<n;i++)
            num[i] = scan.nextInt();

        for(int i=0;i<n;i++){
            if(num[i] == 1)
                continue;
            else if(primality(num[i]) == true)
                count+=1;
        }
        System.out.println(count);
    }
    public static boolean primality(int n){
        int i = 2;
        while(i*i<= n){
            if((n%i)==0)
                return false;
            i+=1;
        }
        return true;
    }
}
