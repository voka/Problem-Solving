package other;
import java.util.Scanner;

import java.io.*;
public class _1373_BinaryToOctal {
    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String n = scan.next();
        int length = n.length();
        int start = 0;

        if((length % 3) == 1){
            sb.append(n.charAt(0));
            start = 1;
        }
        else if((length %3) == 2){
            sb.append( (n.charAt(0) - '0')*2 +(n.charAt(1) - '0'));
            start = 2;
        }

        for(int i=start ; i<length;i+=3){
            sb.append( (n.charAt(i)-'0')*4 + (n.charAt(i+1)-'0')*2 + (n.charAt(i+2)-'0'));
        }
        System.out.println(sb);
    }
}
