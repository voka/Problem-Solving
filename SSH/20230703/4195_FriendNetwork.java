package DisJointSet;
import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.ArrayList;
public class _4195_FriendNetwork {
    static int[] parent;
    static int[] count;
    static HashMap<String, Integer> map;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        for(int i=0;i<t;i++){
            int n = Integer.parseInt(br.readLine());
            parent = new int[2*n];
            count = new int[2*n];
            for(int j=0;j<2*n;j++){
                parent[j] = j;
                count[j] = 1;
            }
            map = new HashMap<>();
            int Number = 0;
            for(int j=0;j<n;j++){
                st = new StringTokenizer(br.readLine());
                String st1 = st.nextToken();
                String st2 = st.nextToken();

                if(!map.containsKey(st1)){
                    map.put(st1, Number);
                    Number++;
                }
                if(!map.containsKey(st2)){
                    map.put(st2,Number);
                    Number++;
                }
                union(map.get(st1),map.get(st2));
                System.out.println(count[find(map.get(st1))]);
            }
        }
    }
    public static int find(int x){
        if(parent[x] == x){
            return x;
        }
        else{
            return find(parent[x]);
        }
    }
    public static void union(int x, int y){
        x = find(x);
        y = find(y);

        if( x > y){
            parent[y] = x;
            count[x] += count[y];
        }
        else if(x < y){
            parent[x] = y;
            count[y] += count[x];
        }
    }

}
