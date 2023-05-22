package other;
import java.util.Scanner;
public class _10809_AlphabetFound {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        int[] alphabet_position = new int[26];

        for(int i=0;i<26;i++)
            alphabet_position[i] = -1;

        for(int i=0;i<str.length();i++){
            if(alphabet_position[str.charAt(i)-'a'] == -1)
                alphabet_position[str.charAt(i)-'a'] = i;  
        }
        for(int i=0;i<26;i++)
            System.out.print(alphabet_position[i] + " ");
    }
}
