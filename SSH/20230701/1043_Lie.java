package DisJointSet;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
public class _1043_Lie {
    static int n,m;
    static int[] truth;
    static int[] root;
    static int count;
    static ArrayList<ArrayList<Integer>> list = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        truth = new int[n+1];
        root = new int[n+1];
        for(int i=1;i<=n;i++){
            root[i] = i;
        }
        for(int i=1;i<=m;i++){
            list.add(new ArrayList<Integer>());
        }

        st = new StringTokenizer(br.readLine());
        int tNum = Integer.parseInt(st.nextToken());
        for(int i=0;i<tNum;i++){
            int number = Integer.parseInt(st.nextToken());
            truth[number] = 1;
        }
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int tmp = Integer.parseInt(st.nextToken());
            int previous = 0;
            int present;
            if(tmp > 0){
                previous = Integer.parseInt(st.nextToken());
                list.get(i).add(previous);
            }
            for(int j=2;j<=tmp;j++){
                present = Integer.parseInt(st.nextToken());
                union(previous,present);
                list.get(i).add(present);
                previous = present;
            }
        }
        for(int i=1;i<=n;i++){
            if(truth[i] == 1){
                truth[find(i)] = 1;
            }
        }
        for(int i=0;i<m;i++){
            int t = find(list.get(i).get(0));
            if(truth[t] == 0) count++;
        }
        System.out.println(count);
    }
    public static int find(int x){
        if(root[x] == x)
            return x;
        else{
            return find(root[x]);
        }
    }
    public static void union(int x, int y){
        x = find(x);
        y = find(y);

        if( x != y){
            if(x < y){
                root[y] = x;
            }
            else if (x > y){
                root[x] = y;
            }
        }

    }

}
