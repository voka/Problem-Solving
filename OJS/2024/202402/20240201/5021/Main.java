import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static double[] score;
    static String king;
    static List<Integer> indegree = new ArrayList<>();
    static List<List<Integer>> graph = new ArrayList<>();
    static Map<String, Integer> people = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        king = br.readLine().strip();
        int kingId = getID(king);
        score = new double[N*N];
        for(int i=0;i<N;++i){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            String child = st.nextToken();
            String p1 = st.nextToken();
            String p2 = st.nextToken();
            int cId = getID(child);
            int p1Id = getID(p1);
            int p2Id = getID(p2);
            graph.get(p1Id).add(cId);
            graph.get(p2Id).add(cId);
            indegree.set(cId, indegree.get(cId)+ 2);
        }
        score[kingId] = 100;
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<indegree.size();++i){
            if(indegree.get(i) == 0){
                q.add(i);
            }
        }
        while (!q.isEmpty()) {
            int cur = q.poll();
            for(int c : graph.get(cur)){
                score[c] += (score[cur]/2);
                indegree.set(c, indegree.get(c)-1);
                if(indegree.get(c) == 0){
                    q.add(c);
                }
            }
        }
        String ans = "";
        double ansScore = -1.0;
        for(int i=0;i<M;++i){
            String name = br.readLine().strip();
            int id = getID(name);
            if(ansScore < score[id]){
                ans = name;
                ansScore = score[id];
            }
        }
        System.out.println(ans);
    }
    static int getID(String name){
        if(!people.containsKey(name)){
            people.put(name, people.size());
            graph.add(new ArrayList<>());
            indegree.add(0);
        }
        return people.get(name);
    }
}
