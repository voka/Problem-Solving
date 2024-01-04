import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    static int n,k;
    static int[] parents;
    public static int findParent(int x){
        if(parents[x] != x){
            parents[x] = findParent(parents[x]);
        }
        return parents[x];
    }

    public static void unionParents(int a, int b){
        int fa = findParent(a);
        int fb = findParent(b);
        if(fa != fb) parents[fa] = fb;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int t = 1;
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        while(t <= T){
            n = Integer.parseInt(br.readLine());
            k = Integer.parseInt(br.readLine());
            //초기화
            parents = new int [n];
            for(int i=0;i<n;++i){
                parents[i] = i; 
            } 
            // 친구 입력 받기
            for(int i=0;i<k;++i){
                st = new StringTokenizer(br.readLine()," ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                unionParents(a, b);
            }
            int m = Integer.parseInt(br.readLine());
            sb.append("Scenario ").append(t).append(':').append('\n');
            for(int q=0;q<m;++q){
                st = new StringTokenizer(br.readLine()," ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                if(findParent(a) == findParent(b)) sb.append(1).append('\n');
                else sb.append(0).append('\n');
            }
            sb.append('\n');

            t++;
        }
        System.out.print(sb.toString());

    }
}