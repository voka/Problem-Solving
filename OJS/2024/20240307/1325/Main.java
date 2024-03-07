import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int N,M, curMAX = Integer.MIN_VALUE, count;
    static int[] p; //각 노드에서 탐색 가능한 노드 수 저장
    static boolean[] check; // 검사 배열
    static List<List<Integer>> tree = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        p = new int[N+1];
        for(int i=0;i<=N;++i) tree.add(new ArrayList<>());
        for(int i=0;i<M;++i){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            tree.get(b).add(a);
        }
        check = new boolean[N+1]; // 탐색한 배열인지 검사할때 사용
        for(int i=1;i<=N;++i) {
            if(!check[i]){
                count = 0;// i번째 노드가 탐색 가능한 숫자 -> 0 으로 초기화 후 시작
                getChild(i);
            }
            curMAX = Math.max(curMAX, p[i]); // 탐색 가능한 최댓값 찾기 
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i=1;i<=N;++i) {
            if(p[i] == curMAX) sb.append(i).append(" ");
        }
        System.out.println(sb.toString());
        
    }
    static void getChild(int x){
        check[x] = true; // x번 노드 검사 시작
        for(int c : tree.get(x)){
            if(!check[c]){// 탐색한 적이 없는 노드일 경우에만 자식 노드 탐색 시작
                getChild(c); 
                // count는 전역 변수이므로 자식 -> 자식 -> ... 이여도 개수가 증가함
                count++;
            }else count += p[c];
        }
        p[x] = count; // p[x] 업데이트
    }
}
