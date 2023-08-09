package BinarySearch;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
public class _2805_TreeCut {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] trees = new int[n];
        int lo = 0;
        int hi = 0;
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++) {
            trees[i] = Integer.parseInt(st.nextToken());
            if(hi < trees[i]){
                hi = trees[i];
            }
        }



        int result = 0;
        while(lo<hi){
            int middle = (lo+hi)/2;


            long sum = 0;
            for(int t : trees){
                if(t > middle)
                    sum += (t - middle);
            }

            if(sum >=m){
                lo = middle+1;

            }
            else if(sum < m ){
                hi = middle;

            }
        }
        result = lo-1;
        System.out.println(result);
    }
}
