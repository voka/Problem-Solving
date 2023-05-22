package other;
import java.util.Scanner;
public class _10820_StringAnalyze {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] num = new int[4];

        while(scan.hasNext()){
            String str = scan.nextLine();
            for(int i=0;i<str.length();i++){
                char ch = str.charAt(i);
                if(ch >= 'a' && ch <= 'z')
                    num[0]++;
                else if(ch >= 'A' && ch <= 'Z')
                    num[1]++;
                else if(ch >= '0' && ch <= '9')
                    num[2]++;
                else
                    num[3]++;
            }
            for(int i=0;i<4;i++)
                System.out.print(num[i] + " ");
            for(int i=0;i<4;i++)
                num[i]=0;
        }
    }
}
