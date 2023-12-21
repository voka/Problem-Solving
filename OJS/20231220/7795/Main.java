import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int T, N, M; 
    static int[] A, B;

    public static void printArr(int[] a){
        for(int i=0;i<a.length;++i){
            System.out.printf("%d ",a[i]);
        }
        System.out.println();
    }
    // List A 탐색
    public static int upperBound(int target){
        int start = 0; 
        int end = N;
        while(start < end){
            int mid = (start + end) / 2;
            if(A[mid] <= target){
                start = mid + 1;
            }
            else{
                end = mid;
            }
        }
        return end;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        for(int t=0;t<T;++t){
            String[] temp = br.readLine().split(" ");
            N = Integer.parseInt(temp[0]);
            M = Integer.parseInt(temp[1]);
            A = new int[N];
            B = new int[M];
            temp = br.readLine().split(" ");
            for(int i=0;i<N;++i){
                A[i] = Integer.parseInt(temp[i]);
            }
            temp = br.readLine().split(" ");
            for(int i=0;i<M;++i){
                B[i] = Integer.parseInt(temp[i]);
            }
            Arrays.sort(A);
            Arrays.sort(B);
            //printArr(A);
            //printArr(B);
            int answer = 0;
            for(int i=0;i<M;++i){
                answer += (N - upperBound(B[i]));
            }
            System.out.println(answer);
        }
        


    }
}
