package other;
import java.util.Scanner;
public class _11656_SuffixArray {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String S = scan.nextLine();
        int length = S.length();
        String[] str = new String[length];

        for(int i=0;i<length;i++){
            str[i] = S.substring(i,length);
        }

        for(int i=0;i<length-1;i++){
            String tmp;
            for(int j=i+1;j<length;j++) {
                if (str[i].compareTo(str[j]) > 0) {
                    tmp = str[i];
                    str[i] = str[j];
                    str[j] = tmp;
                }
            }
        }

        for(int i=0;i<length;i++)
            System.out.println(str[i]);
    }
}
