import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int [] nodes,parents,childNum;
        while(true){
            st = new StringTokenizer(br.readLine()," ");
            int n = Integer.parseInt(st.nextToken());
            nodes = new int[n];
            parents = new int[n];
            childNum = new int[n];
            if(n == 0) break;
            //root 노드 초기화
            nodes[0] = -1;
            parents[0] = -1;
            int curParentNode = 0;
            int k = Integer.parseInt(st.nextToken());
            int kid = -1;
            st = new StringTokenizer(br.readLine()," ");
            for(int i=0;i<n;++i){
                int cval = Integer.parseInt(st.nextToken());
                if(cval == k) kid = i; 
                nodes[i] = cval;
                if(i > 1 && (nodes[i-1] + 1 !=  nodes[i])){ 
                    // 수가 연속되지 않았다면
                    // 다음으로 가장 작은 숫자를 가진 번호를 찾는다.
                    curParentNode++;
                }
                if(i > 0){
                    parents[i] = curParentNode;
                    childNum[curParentNode]++;
                }
            }
            //System.out.println(kid);
            if(parents[kid] == -1 || parents[parents[kid]] == -1) sb.append(0).append('\n');
            else{
                int num = 0;
                int tPid = parents[kid];
                int tPPid = parents[tPid];  // 부모 -> 부모
                for(int i=1;i<n;++i){
                    if(i == tPid || parents[i] == -1 ) continue;
                    if(parents[i] == tPPid){
                        num += childNum[i];
                    }
                    //System.out.printf("%d, %d\n",i,num);
                }
                //System.out.println(num);
                sb.append(num).append('\n');
            }
        }
        System.out.println(sb.toString());
    }    
}
