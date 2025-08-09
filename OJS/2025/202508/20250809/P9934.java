import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class P9934 {
    static int K,size;
    static int[] arr;
    static ArrayList<ArrayList<Integer>> list;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        size = (int)Math.pow(2,K) - 1;
        arr = new int[size];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<size;++i){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        list = new ArrayList<>();
        for(int i=0;i<K;++i){
            list.add(new ArrayList<>());
        }
        solve(0, size, 0);

        for(int i=0;i<K;++i){
            for(int id : list.get(i)){
                System.out.print(arr[id] +" ");
            }
            System.out.println();
        }

    }   
    static void solve(int l, int r,int level){
        if(level == K) return;
        int next = l + (r-l)/ 2;
        list.get(level).add(next);
        solve(l, next - 1, level + 1); // 왼쪽 자식
        solve(next + 1, r, level + 1); // 오른쪽 자식
    }
}
