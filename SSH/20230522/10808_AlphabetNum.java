package other;
import java.util.Scanner;
public class _10808_AlphabetNum {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        int[] alphabet_num = new int[26];

        for(int i=0;i<str.length();i++){
            alphabet_num[str.charAt(i)-'a']++;
        }

        for(int i=0;i<26;i++)
            System.out.print(alphabet_num[i] + " ");
    }
}
