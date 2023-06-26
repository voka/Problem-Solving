package Graph;
import java.io.*;
import java.util.StringTokenizer;
public class _2331_RepeatSequence {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] mem = new int[260000];
        int a,p;
        a = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        int i=1, count=0;
        mem[a]=-1;
        while(true){
            int tmp = calc(a,p);
            if(mem[tmp] == -1){
                break;
            }
            else if(mem[tmp] != 0) {
                count = mem[tmp];
                break;
            }
            else{
                mem[tmp] = i;
                a = tmp;
                i++;
            }
        }

        System.out.println(count);
    }
    public static int calc(int num, int exp){
        int res=0;
        while(num > 0){
            res+=Math.pow(num % 10, exp);
            num /=10;
        }
        return (int)res;
    }
}
