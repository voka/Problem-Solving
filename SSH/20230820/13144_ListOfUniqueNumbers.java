package TwoPointer;
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;
public class _13144_ListOfUniqueNumbers {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        int[] arr = new int[n+1];
        for(int i=1;i<=n;i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int s = 1;
        int e = 0;
        long count = 0;
        boolean[] checkList = new boolean[100001];
        while(s<=n){
            while(e+1<=n && checkList[arr[e+1]]==false){
                checkList[arr[e+1]]=true;
                e+=1;
            }
            count += e - s + 1;
            checkList[arr[s]] = false;
            s++;
        }
        System.out.println(count);
    }
}
