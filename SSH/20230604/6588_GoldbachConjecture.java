package other;
import java.io.*;
public class _6588_GoldbachConjecture {
    static int [] prime = new int[1000001];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        prime[0]=1;
        prime[1]=1;
        for(int i=2;i<=1000000;i++){
            for(int j=2*i;j<=1000000;j+=i){
                prime[j]=1;
            }
        }
        int n = Integer.parseInt(br.readLine());
        while(n != 0){
            int result = goldbach(n);
            if(result !=0){
                bw.write(n+" = "+(n-result) + " + " + (result));
            }
            else{
                bw.write("Goldbach's conjecture is wrong.");
            }
            bw.newLine();
            bw.flush();
            n = Integer.parseInt(br.readLine());

        }
        bw.close();
    }
    public static int goldbach(int n){
        for(int i=n-1;i>=3;i--){
            if(prime[i] == 0 && prime[n-i] == 0){
                return i;
            }
        }
        return 0;
    }
}
