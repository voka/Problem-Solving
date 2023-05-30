import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text;
        while (true) {
            text = br.readLine();
            if (text == null) {
                break;
            }

            StringTokenizer st = new StringTokenizer(text);
            String s = st.nextToken();
            String t = st.nextToken();
            int i = 0;
            int j = 0;
            while (i < s.length() && j < t.length()) {
                if (s.charAt(i) == t.charAt(j)) {
                    i++;
                    j++;
                } else
                    j++;
            }
            if (i == s.length()) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
    }

}
