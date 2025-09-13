import java.io.*;
import java.util.*;
class P33869{
    static int[] key;
    static boolean[] use;
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String w = br.readLine().trim();
        String s = br.readLine().trim();
        use = new boolean[27];
        key = new int[27];
        int kid = 0;
        for(int i=0;i<w.length();++i){
            if(use[w.charAt(i) - 'A']) continue;
            use[w.charAt(i) - 'A'] = true; 
            key[kid++] = w.charAt(i) - 'A';
        }
        int id = kid;
        kid = 0;
        while(id <= 26){
            while(use[kid]){
                kid++;
            }
            key[id++] = kid;
            use[kid] = true;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();++i){
            int cur = key[s.charAt(i) - 'A'];
            sb.append((char)(cur + 'A'));
        }
        System.out.println(sb);



    }
}