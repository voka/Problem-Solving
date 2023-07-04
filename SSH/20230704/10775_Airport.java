package DisJointSet;
import java.io.*;
public class _10775_Airport {
    static int[] root;
    static int count;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int g = Integer.parseInt(br.readLine());
        int p = Integer.parseInt(br.readLine());
        int[] gi = new int[p];
        root = new int[g+1];
        for(int i=1;i<=g;i++){
            root[i] = i;
        }
        for(int i=0;i<p;i++){
            gi[i] = Integer.parseInt(br.readLine());
        }
        for(int i=0;i<p;i++){
            int egate = find(gi[i]);
            if(egate == 0)
                break;
            count++;
            union(egate, egate-1);
        }
        System.out.println(count);
    }
    public static int find(int x){
        if(root[x] == x)
            return x;
        else{
            return root[x]=find(root[x]);
        }
    }
    public static void union(int x, int y){
        x = find(x);
        y = find(y);
        if(x!=y){
            root[x] = y;
        }
    }
}
