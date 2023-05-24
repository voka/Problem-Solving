package other;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
public class _1158_JosephusProblem {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int k = scan.nextInt();
        Queue<Integer> queue = new LinkedList<Integer>();
        Queue<Integer> answer = new LinkedList<Integer>();
        for(int i=1;i<=n;i++)
            queue.add(i);

        while(!queue.isEmpty()){
            for(int i=1;i<k; i++){
                int num = queue.poll();
                queue.add(num);
            }
            answer.add(queue.poll());
        }

        System.out.print("<");
        while(!answer.isEmpty()){
            System.out.print(answer.poll());
            if(answer.isEmpty())
                System.out.print(">");
            else
                System.out.print(", ");
        }
    }
}
