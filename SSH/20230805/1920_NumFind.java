package BinarySearch;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
public class _1920_NumFind {
    static int n, m;
    static int[] arr;
    static int[] find;
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        m = Integer.parseInt(br.readLine());
        find = new int[m];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<m;i++){
            find[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0;i<m;i++){
            binarySearch(0, n-1, find[i]);
        }
    }
    public static void binarySearch(int start, int end, int x){
        int middle = (start + end)/ 2;

        if(start == middle){
            if(arr[start] == x){
                System.out.println("1");
                return;
            }
            else if(arr[end] == x){
                System.out.println("1");
                return;
            }
            else {
                System.out.println("0");
                return;
            }

        }
        if(x == arr[middle]){
            System.out.println("1");
            return;
        }
        else if(x < arr[middle]){
            binarySearch(start, middle-1,x);
        }
        else if (x > arr[middle]){
            binarySearch(middle+1,end,x);
        }
        return;
    }
}
