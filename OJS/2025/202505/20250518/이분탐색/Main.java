
public class Main {
    static int[] arr = {1, 3, 5, 7, 8, 10, 20, 35, 99, 100};
    public static void main(String[] args) {
        System.out.println(binarySearch(21));
        System.out.println(lowerBound(1));
        System.out.println(upperBound(3));
    }
    static int binarySearch(int target){
        int s = 0;
        int e = arr.length-1;
        while(s<=e){
            int m = (s+e)/2;
            if(target == arr[m]){
                return m;
            }else if(target < arr[m]){
                e = m - 1;
            }else{
                s = m + 1;
            }
        }
        return -1;
    }
    static int lowerBound(int target){
        int s = 0;
        int e = arr.length-1;
        while(s<e){
            int m = (s+e)/2;
            if(target <= arr[m]){
                e = m;
            }else{
                s = m + 1;
            }
        }
        return e;
    }
    static int upperBound(int target){
        int s = 0;
        int e = arr.length-1;
        while(s<e){
            int m = (s+e)/2;
            if(target >= arr[m]){
                s = m + 1;
            }else{
                e = m;
            }
        }
        return e;
    }
}
