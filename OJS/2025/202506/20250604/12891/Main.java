import java.io.*;
import java.util.*;
public class Main {
    static int[] arr;
    static int a,c,g,t;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int S = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        String input = br.readLine();
        st = new StringTokenizer(br.readLine());
        a = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        g = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        Queue<Character> data = new ArrayDeque<>();
        int l = 0;
        int r = P - 1;
        arr = new int[4];
        for(int i=l; i<=r;++i){
            char cur = input.charAt(i);
            calcul(cur, 1);
            data.add(cur);
        }
        int result = 0;
        while(r < S){
            //System.out.printf("%d ~ %d\n", l, r);
            if(check()){
                result++;
            }
            l++;
            r++;
            if(r == S) break;
            char pre = data.poll();
            calcul(pre, -1);
            char next = input.charAt(r);
            calcul(next, 1);
            data.add(next);
        }
        System.out.println(result);
    }
    static boolean check(){
        if(a <= arr[0] && c <= arr[1] && g <= arr[2] && t <= arr[3]){
            return true;
        }
        return false;
    }
    static void calcul(char cur, int id){
        if(cur == 'A') arr[0] += id;
        if(cur == 'C') arr[1] += id;
        if(cur == 'G') arr[2] += id;
        if(cur == 'T') arr[3] += id;
    }
}

