package Divide_and_Conquer;
import java.io.*;
public class _5904_MooGame {
    static int n;
    static int m;
    static char answer;
    static int[] count;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        count = new int[10000];
        count[0] = 3;
        int i=0;
        while(n > count[i]){
            i++;
            count[i] = count[i-1]*2 + i + 3;
        }

        
        moo(i, n);
        System.out.println(answer);

    }
    public static void moo(int k, int find){

        if(k ==0){
            if(find == 1){
                answer = 'm';
                return;
            }
            else{
                answer = 'o';
                return;
            }
        }

        if(find <= count[k-1]){
            moo(k-1, find);
        }
        else if(count[k-1]< find && find <= count[k-1]+k+3){
            if(find == count[k-1]+1)
                answer = 'm';
            else
                answer = 'o';
        }
        else{
            moo(k-1, find-(count[k-1]+k+3));
        }



    }
}
