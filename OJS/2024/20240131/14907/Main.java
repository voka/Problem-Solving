import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
public class Main {
    static int N, MAX = 27, jobSize;
    static int[] indegree,times,ans;
    static List<List<Integer>> graph = new ArrayList<>();
    static Map<Character,Integer> jobSet = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        indegree = new int[MAX];
        times = new int[MAX]; 
        ans = new int[MAX];
        while((input = br.readLine()) != null){
            String[] temps = input.split(" ");
            char job = temps[0].charAt(0);
            int jobID = getJobID(job);
            times[jobID] = Integer.parseInt(temps[1]);
            if(temps.length > 2){
                String nextJobs = temps[2];
                for(int j=0;j<nextJobs.length();++j){
                    char next = nextJobs.charAt(j);
                    int nextJobID = getJobID(next);
                    indegree[jobID]++;
                    graph.get(nextJobID).add(jobID);
                }
            }
            
        }
        jobSize = jobSet.size();
        Queue<Integer> jobQueue = new LinkedList<>();
        for(int i=0;i<jobSize;++i){
            if(indegree[i] == 0){
                jobQueue.add(i);
                ans[i] = times[i];
            }
        }
        while(!jobQueue.isEmpty()){
            int cur = jobQueue.poll();
            for(int next : graph.get(cur)){
                indegree[next]--;
                ans[next] = Math.max(ans[cur] + times[next],ans[next]);
                if(indegree[next] == 0){
                    jobQueue.add(next);
                }
            }
        }
        int answer = 0;
        for(int i=0;i<jobSize;++i){
            answer= Math.max(ans[i], answer);
        }
        System.out.println(answer);

    }
    static int getJobID(char job){
        if(!jobSet.containsKey(job)){
            jobSet.put(job, jobSet.size());
            graph.add(new ArrayList<>());
        }
        return jobSet.get(job);
    }
}
