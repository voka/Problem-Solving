import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Tree{
    int i;
    int l;
    int p;
    public Tree(int i){
        this.i = i;
    }
}
public class P11437 {
    static int N,M;
    static Tree[] my;
    static List<List<Integer>> conn;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int root = 1;
        my = new Tree[N+1];
        conn = new ArrayList<>();
        for(int i=0;i<=N;++i){
            conn.add(new ArrayList<>());
            my[i] = new Tree(i);
        }
        StringTokenizer st;
        for(int i=1;i<N;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            conn.get(a).add(b);
            conn.get(b).add(a);
        }
        setTree(root, 0);
        
        M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            sb.append(lca(a, b)).append("\n");
        }
        System.out.println(sb.toString());

        
    }
    static void setTree(int id, int pid){
        my[id].p = pid;
        my[id].l = my[pid].l + 1;

        for(int cid : conn.get(id)){
            if(cid == pid) continue;
            setTree(cid, id);
        }
    }
    static int lca(int a, int b){
        if(my[a].l < my[b].l){ // level이 낮은 노드가 a가 되도록 설정
            int t = b;
            b = a;
            a = t;
        }
        // 두 노드의 level이 동일하도록 낮은 노드를 높은 노드와 같은 레벨이 될때까지 반복
        while(my[a].l != my[b].l){
            a = my[a].p;
        }
        while(a != b){ // 같은 레벨에서 한번씩 거슬러 올라가며 같은 조상을 가리킬때까지 반복
            a = my[a].p;
            b = my[b].p;
        }
        return a;
    }
}
