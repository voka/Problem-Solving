import java.io.*;
import java.util.*;
public class P16139 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] str = br.readLine().toCharArray();
        int N = Integer.parseInt(br.readLine()); 
        int[][] arr  = new int[str.length+1][26];
        
        for(int i=0;i<str.length;++i){
            int c = str[i] - 'a'; 
            for(int j=0;j<26;++j){
                if(c != j) arr[i+1][j] = arr[i][j];
                else arr[i+1][j] = arr[i][j] + 1;
            }
        }
        
        StringTokenizer st;

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            int alpha = st.nextToken().toCharArray()[0] - 'a';
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            //System.out.println(alpha + " " + s + " " + e);
            sb.append(arr[e+1][alpha] - arr[s][alpha]).append('\n');
        }
        System.out.println(sb.toString());



    }
    
}
