import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P18116 {
    static int MAX = 1000001;
    static boolean[] check;
    static int[] parent;
    static int[] arr;
    static int N;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()); 
        N = Integer.parseInt(st.nextToken());
        parent = new int[MAX];
        check = new boolean[MAX];
        arr = new int[MAX];
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<MAX;++i){
            parent[i] = i;
            arr[i] = 1;
        }
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            char C = st.nextToken().charAt(0);
            if(C == 'I'){
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken()); 
                if(findP(a) != findP(b)) mergeP(a, b);
            }
            if(C == 'Q'){
                int a = Integer.parseInt(st.nextToken());
                int ap = findP(a);
                //System.out.printf("\n robot(%d) -> %d\n", a, ap);
                sb.append(arr[ap]).append('\n');
            }
        }
        // printArr(parent, N);
        // printArr(arr, N);
        System.out.println(sb.toString());
    }    
    static int findP(int x){
        if(parent[x] != x){
            parent[x] = findP(parent[x]);
        }
        return parent[x];
    }
    static void mergeP(int a, int b){
        int ap = findP(a);
        int bp = findP(b);
        if(ap < bp){
            solve(bp,ap);
            parent[bp] = ap;
        }else{
            solve(ap,bp);
            parent[ap] = bp;
        }
    }
    static void solve(int a, int ap){
        if(!check[a]){
            check[a] = true;
            arr[ap]+=arr[a];
            arr[a] = 0;
        }
    }
    static void printArr(int[] a, int n){
        for(int i=0;i<n;++i){
            System.out.printf("%d ",a[i]);
        }
        System.out.println();
    } 
}

/**
 * 
 * 
6
I 1 2
I 3 2
Q 1
Q 4
I 4 10
Q 10
 */