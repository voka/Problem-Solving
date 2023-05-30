package other;
import java.util.Scanner;
import java.io.*;
public class _1850_GCD {
    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(System.in);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        long a, b, gcd;
        a = scan.nextLong();
        b = scan.nextLong();
        gcd = gcd(a,b);

        for(long i=0;i<gcd;i++){
            bw.write("1");
        }
        bw.flush();
        bw.close();

    }
    public static long gcd(long a, long b){
        long q, r;
        if( a<b){
            long tmp = a;
            a = b;
            b = tmp;
        }

        while(( a % b ) != 0){
            q = a /b;
            r = a % b;
            a = b;
            b = r;
        }
        return b;
    }
}
