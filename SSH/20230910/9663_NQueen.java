package BruteForce;
import java.io.*;
import java.util.StringTokenizer;

public class _9663_NQueen {

    static int[] arr;
    static int N;
    static int count = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        findQueen(0);
        System.out.println(count);
    }

    public static void findQueen(int d){
        if(d == N){
            count++;
            return;
        }

        for(int i=0;i<N;i++){
            arr[d] = i;
            if(isPos(d)){
                findQueen(d+1);
            }
        }
    }

    public static boolean isPos(int c){
        for(int i=0;i<c;i++){
            if(arr[c] == arr[i]){
                return false;
            }
            else if(Math.abs(c-i) == Math.abs(arr[c]-arr[i])){
                return false;
            }

        }
        return true;
    }
}
