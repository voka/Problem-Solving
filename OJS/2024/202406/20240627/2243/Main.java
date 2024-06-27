import java.io.*;
import java.util.*;
public class Main {
    static int T = 1000001;
    static int n;
    static int[] candy;
    static int[] tree;
    public static void main(String[] args) throws IOException{
        candy = new int[T];
        tree = new int [4* T];
        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;++i){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            if(a == 1){
                int b = Integer.parseInt(st.nextToken());
                sb.append(getCandy(0,T-1,1,b)).append('\n');
            }
            if(a == 2){
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                update(0,T-1,1,b,c);

            }

        }
        System.out.println(sb.toString());
    }    
    static void update(int s, int e, int node, int taste, int num){
        if(taste < s || taste > e) return;
        tree[node] += num;
        if(s == e) return;
        int m = (s+e)/2;
        update(s,m,node*2,taste,num);
        update(m+1,e,node*2+1,taste,num);
    }
    static int getCandy(int s, int e, int node, int rank){
        if(s == e){
            update(0,T-1,1,s,-1);
            return s;
        }
        int m = (s+e)/2;
        if(rank <= tree[node*2]){ //rank 번째 사탕을 왼쪽 자식 트리에서 찾을 수 있는 경우
            return getCandy(s,m, node * 2, rank);
        }
        else {// 사탕을 오른쪽 자식 트리에서 찾아야 하는 경우 -> rank 값을 왼쪽 서브트리의 사탕 개수만큼 감소시켜 찾게 한다.
            return getCandy(m+1,e,node*2+1, rank - tree[node*2]);
        }
    }
}
