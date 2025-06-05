import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int N;
    static String[] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new String[5];
        arr[0] = "\"재귀함수가 뭔가요?\"";
        arr[1] = "\"잘 들어보게. 옛날옛날 한 산 꼭대기에 이세상 모든 지식을 통달한 선인이 있었어.";
        arr[2] = "마을 사람들은 모두 그 선인에게 수많은 질문을 했고, 모두 지혜롭게 대답해 주었지.";
        arr[3] = "그의 답은 대부분 옳았다고 하네. 그런데 어느 날, 그 선인에게 한 선비가 찾아와서 물었어.\"";
        arr[4] = "라고 답변하였지.";
        System.out.println("어느 한 컴퓨터공학과 학생이 유명한 교수님을 찾아가 물었다.");
        print(0);
    }   

    static void print(int n){
        if(n > N) return;
        linePrint(n);
        System.out.println(arr[0]);
        if(n == N){
            linePrint(n);
            System.out.println("\"재귀함수는 자기 자신을 호출하는 함수라네\"");
        }else{
            linePrint(n);
            System.out.println(arr[1]);
            linePrint(n);
            System.out.println(arr[2]);
            linePrint(n);
            System.out.println(arr[3]);
            print(n+1);
        }
        linePrint(n);
        System.out.println(arr[4]);
    }
    static void linePrint(int n){
        for(int i=0;i<n;++i){
            System.out.print("____");
        }
    }
}
