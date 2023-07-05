package DisJointSet;
import java.io.*;
import java.util.StringTokenizer;
public class _4803_Tree {
    static int n,m, count,caseNum=0;
    static int[] root;
    static int[] cyclecount;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String input = "";

        while(!((input = br.readLine()).equals("0 0"))){
            st = new StringTokenizer(input);
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            count = 0;
            caseNum++;
            root = new int[n+1];
            cyclecount = new int[n+1];
            for(int i=1;i<=n;i++){
                root[i] = i;
            }
            for(int i=0;i<m;i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                union(a,b);
            }


            for(int i=1;i<=n;i++){
                if(root[i] ==i && cyclecount[i] == 0){
                    count++;
                }
            }
            if(count >1){
                System.out.println("Case "+caseNum+": A forest of "+count+" trees.");
            }
            else if (count == 1){
                System.out.println("Case "+caseNum+": There is one tree.");
            }
            else if(count == 0){
                System.out.println("Case "+caseNum+": No trees.");
            }

        }

    }
    public static int find(int x){
        if(root[x] == x)
            return x;
        else
            return find(root[x]);
    }
    public static boolean union(int x, int y){
        x = find(x);
        y = find(y);

        if(x != y){
            if(x < y) {
                if(cyclecount[x]==1) cyclecount[y] = 1;
                root[x] = y;
            }
            else{
                if(cyclecount[y]==1) cyclecount[x] = 1;
                root[y] = x;

            }
            return true;
        }
        else{
            cyclecount[x] = 1;
            return false;
        }
    }
}
