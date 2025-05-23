import java.io.*;
import java.util.*;
class Edge{
    int p;
    int v;
    int degree;
    ArrayList<Integer> child;

    public Edge(int v){
        this.p = -1;
        this.v = v;
        this.degree = -1;
        child = new ArrayList<>();
    }
    public void setP(int p){
        this.p = p;
    }
    public void addChild(int c){
        child.add(c);
    }
    public void root(){
        this.degree = 0;
    }
}
public class Main {
    static int N;
    static int MaxDepth;
    static Edge[] edges;
    static int parent[][]; //dp로 LCA 빠르게 구하기
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int t = 0;
        StringBuilder sb = new StringBuilder();
        while(t < T){
            t++;
            N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int root = 1;
            edges = new Edge[N+1];
            edges[root] = new Edge(root);
            edges[root].root();
            MaxDepth = getTreeHeight();
            parent = new int[N+1][MaxDepth];
            for(int i=2;i<=N;++i){
                edges[i] = new Edge(i);
                int p = Integer.parseInt(st.nextToken());
                edges[i].setP(p);
                edges[p].addChild(i);
                edges[i].degree = edges[p].degree + 1;
                parent[i][0] = p;
            }
            // 부모 노드 채우기
            for(int i=1;i<MaxDepth;++i){
                for(int j=2;j<=N;++j){ // 2 ^ i-1 번째의 노드 채우기 (root노드는 부모가 없으므로 pass)
                    parent[j][i] = parent[parent[j][i-1]][i-1];
                }
            }
            //setDegree();
            sb.append("#").append(t).append(" ").append(bfs()).append("\n");
        }
        System.out.println(sb.toString());
    }
    static int getTreeHeight() {
        return(int)Math.ceil(Math.log(N)/Math.log(2)) +1;
    }
    static int findCommonParent(int a, int b){
        // a or b == > root
        if(a == 1 || b == 1) return 1;

        int la = edges[a].degree;
        int lb = edges[b].degree;
        // la > lb 로 맞추기
        if(la < lb){
            int t = a;
            a = b;
            b = t;
        }
        // 1. a,b의 높이 맞추기 -> a의 높이를 낮춘다
        for(int i=MaxDepth-1; i>=0 ; --i){
            if(Math.pow(2, i) <= edges[a].degree - edges[b].degree){
                a = parent[a][i]; 
            }
        }
        // 높이를 맞췄을때 a == b이면 LCA는 a or b가 된다. 
        if(a == b) return a;

        for(int i=MaxDepth-1; i>=0; --i){
            if(parent[a][i] != parent[b][i]){
                a = parent[a][i];
                b = parent[b][i];
            }
        }
        return parent[a][0];
    }
    // static int getDegree(int id){
    //     if(id == 1) return 0;
    //     int p = edges[id].p;
    //     if(edges[p].degree == -1){
    //         edges[id].degree = 1 + getDegree(p);
    //     }else{
    //         edges[id].degree = 1 + edges[p].degree; 
    //     }
    //     return edges[id].degree;
    // }    
    static long bfs(){
        long answer = 0;
        int root = 1;
        int a = root;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(root);
        boolean[] visit = new boolean[N+1];
        visit[root] = true;
        while(!q.isEmpty()){
            int cur = q.poll();
            for(int c : edges[cur].child){
                if(visit[c]) continue;
                visit[c] = true;
                int fca = findCommonParent(a, c);
                answer += edges[c].degree + edges[a].degree - 2*edges[fca].degree;
                q.add(c);
                a = c;

            }
        }
        return answer;
    }
}
