import java.util.ArrayList;
import java.util.List;
// 조합은 백트래킹같은 방법으로
public class combination {
    public static void main(String[] args) {
        combi(5);
        
    }
    static void combi(int s){
        int[] arr = new int[s+1];
        for(int i=1;i<=s;++i){
            arr[i] = i;
        }
        boolean[] visit = new boolean[s+1];
        solve(arr, visit, 1, 0, 3);

    }
    static void solve(int[] arr, boolean[] visit, int s, int d, int r){
        if( d == r){
            for(int i=0;i<arr.length;++i){
                if(visit[i]) System.out.printf("%d ", arr[i]);
            }
            System.out.println();
            return;
        }
        for(int i=s;i<arr.length;++i){
            if(!visit[i]){
                visit[i] = true;
                solve(arr, visit, i+1, d+1, r);
                visit[i] = false;
            }
        }
    }

}
