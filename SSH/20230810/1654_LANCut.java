package BinarySearch;
import java.io.*;
import java.util.StringTokenizer;
public class _1654_LANCut {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        long lo = 0;
        long hi = 0;
        int[] lans = new int[k];
        for(int i=0;i<k;i++){
            lans[i] = Integer.parseInt(br.readLine());
            if(hi < lans[i]){
                hi = lans[i];
            }
        }
        hi++;

        while(lo<hi){
            long middle = (lo+hi)/2;
            long sum = 0;
            for(int lan : lans){
                sum += lan/middle;
            }
            if(sum >=n){
                lo = middle+1;
            }
            else{
                hi = middle;
            }

        }
        System.out.println(lo-1);

    }
}
