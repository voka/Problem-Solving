import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        int[] arr = {3,1,4,2,5,5,5,5,5,5,7,6,7,7,7,7,7};
        Arrays.sort(arr);
        printArr(arr);
        System.out.println(Arrays.binarySearch(arr,1));
        Map<Integer,Integer> map = new HashMap<>();
        map.putIfAbsent(1, 2);
        int get =  map.getOrDefault(1, 3);
        System.out.println(get);
    }
    static void printArr(int[] arr){
        for(int i=0;i<arr.length;++i){
            System.out.printf("%d ",arr[i]);
        }
        System.out.println();
    }
}
