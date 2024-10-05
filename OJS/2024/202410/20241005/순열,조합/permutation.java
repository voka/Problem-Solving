
public class permutation {
    // 순열도 조합과 마찬가지로 백트래킹 사용
    public static void main(String[] args) {
        permu(5);
        
    }
    static void permu(int s){
        int[] arr = new int[s+1];
        for(int i=1;i<=s;++i){
            arr[i] = i;
        }
        int[] out = new int[s+1];
        boolean[] visit = new boolean[s+1];
        solve(arr,out, visit, 0, 3);

    }
    static void solve(int[] arr, int[] out, boolean[] visit, int d, int r){
        if( d == r){
            for(int i=0;i<r;++i){
                System.out.printf("%d ", out[i]);
            }
            System.out.println();
            return;
        }
        for(int i=0;i<arr.length;++i){
            if(!visit[i]){
                visit[i] = true;
                out[d] = arr[i];
                solve(arr, out, visit, d+1, r);
                visit[i] = false;
            }
        }
    }

}
