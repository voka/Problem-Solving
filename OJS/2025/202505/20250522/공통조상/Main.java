import java.io.*;
import java.util.*;

class Edge{
    int v;
    int left;
    int right;
    int p;
    public Edge(){
        v = -1;
        left = -1;
        right = -1;
        p = -1;
    }
    public Edge(int v){
        this.v = v;
        this.left = -1;
        this.right = -1;
        this.p = -1;
    }
    public Edge(int v, int p){
        this.v = v;
        this.p = p;
        this.left = -1;
        this.right = -1;
    }
    public void addChild(int child){
        if(left == -1){
            left = child;
        }else{
            if(left > child){
                right = left;
                left = child;
            }else{
                right = child;
            }
        }
    }

}
public class Main{
    static int V, E;
    static int a,b;
    static Edge[] err;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int t = 0;
        StringBuilder sb = new StringBuilder();
        while(T > t){
            t++;
            StringTokenizer st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            err = new Edge[V+1];
            for(int i=1;i<=V;++i){
                err[i] = new Edge();
            }
            st = new StringTokenizer(br.readLine());
            for(int i=0;i<E;++i){
                int p = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                err[p].v = p;
                err[c].v = c;
                err[p].addChild(c);
                err[c].p = p;
            }
            int de_a = getDegree(a);
            int de_b = getDegree(b);
            int pid = -1;
            
            if(de_a < de_b){
                pid = findCommonParent(a, b);
            }else{
                pid = findCommonParent(b, a);
            }
            int count = countChild(pid);
            //int l = err[pid].left;
            //int r = err[pid].right;
            //System.out.printf("degree -> %d : %d, %d: %d\n", l,getDegree(l), r,getDegree(r));
            //System.out.printf("%d : %d,  %d: %d\n", l,countChild(l), r,countChild(r));
            sb.append("#").append(t).append(" ").append(pid).append(" ").append(count).append("\n");
        }
        System.out.println(sb.toString());
        
        
    }
    static int getDegree(int id){
        int result = 0;
        while(err[id].p > 0){
            result++;
            id = err[id].p;
        }
        return result;
    }
    static int countChild(int id){
        int answer = 1;
        int l = err[id].left;
        int r = err[id].right;
        if(l!=-1){
            answer += countChild(l);
        }
        if(r!=-1){
            answer += countChild(r);
        }
        return answer;
    }
    static int findCommonParent(int small, int big){
        Set<Integer> parents = new HashSet<>(); 
        int sid = small;
        parents.add(sid);
        while(err[sid].p > 0){
            int spid = err[sid].p;
            parents.add(spid);
            sid = spid;
        }
        int answer = -1;
        int bid = big;
        while(err[bid].p > 0){
            int bpid = err[bid].p;
            if(parents.contains(bpid)){
                answer = bpid;
                break;
            }
            bid = bpid;
        }
        return answer;
    }
}
