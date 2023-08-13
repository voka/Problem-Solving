package TwoPointer;
import java.io.*;
import java.util.StringTokenizer;
public class _2003_NumberSum2 {
    public static void main(String[] args) throws IOException{
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int start = 0;
        int end = 0;
        int count = 0;
        int sum = 0;
        while(true){
            if(sum>= m){
                sum -= arr[start];
                start++;
            }
            else if(end >= n){
                break;
            }
            else{
                sum += arr[end];
                end++;
            }

            if(sum == m){
                count++;
            }
        }
        System.out.println(count);
    }
}
