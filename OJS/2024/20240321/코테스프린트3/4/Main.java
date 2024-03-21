import java.io.*;
import java.util.*;
public class Main {
    static int[] col;
    static int ans = 0, n;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        col = new int[n];
        solve(0);
        System.out.println(ans);
    }
    static void solve(int num){
        if(n == num){
            ans++; 
            return;
        }
        for(int i=0;i<n;++i){
            col[num] = i;
            if(check(num)){
                solve(num+1);
            }
        }
    }
    static boolean check(int c){
        for(int i=0;i<c;++i){
            if(col[c] == col[i]) return false;
            if(Math.abs(c-i) == Math.abs(col[c] - col[i])) return false;
        }
        return true;
    }
}