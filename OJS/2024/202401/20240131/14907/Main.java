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
    static Map<Character,Integer> jobSet = new HashMap<>(); // jobName : jobID;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        indegree = new int[MAX];
        times = new int[MAX]; 
        ans = new int[MAX];
        // EOF가 나올때 까지 while 문 반복하며 입력받기
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
                indegree[next]--; // 진입차수 감소시키기 
                // 각 지점에서 작업이 가장 늦게 끝나는 시간을 찾는다.
                // next 작업은 cur 작업 중 가장 늦게 끝나는 작업이 완료된 뒤에 실행돼야 하기 때문. 
                ans[next] = Math.max(ans[next], ans[cur] + times[next]);
                if(indegree[next] == 0){
                    jobQueue.add(next);
                }
            }
        }
        int answer = 0;
        for(int i=0;i<jobSize;++i){ // 가장 늦게 끝나는 작업 찾기
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
