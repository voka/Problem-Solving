import java.io.*;
import java.util.*;
public class Main {
    static int n, size, k;
    static int[] point, rail;
    static boolean[] people;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        size = 2*n;
        rail = new int[size];
        point = new int[size];
        people = new boolean[size];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<size;++i){
            rail[i] = Integer.parseInt(st.nextToken());
            point[i] = i;
        }
        int ans = 0;
        while(k>0){
            //System.out.println("work");
            work();
            //printAll();
            //System.out.println("move");
            move();
            //printAll();
            //System.out.println("put");
            put();
            //printAll();
            ans++;
            //k--;
        }
        System.out.println(ans);

    }
    static void printAll(){
        for(int i=0;i<n;++i){
            int c = point[i];
            System.out.printf("%b ", people[c]);
        }
        System.out.println();
        // for(int i=0;i<size;++i){
        //     System.out.printf("point : %d, rail : %d, p: %b\n", point[i], rail[point[i]],people[point[i]]);
        // }
    }
    // 레일이 한칸씩 움직임
    static void work(){
        int[] temp = new int[size];
        temp[0] = point[size-1];
        for(int i=0;i<size-1;++i){
            temp[i+1] = point[i];
        }
        point = temp;
    }
    static void move(){
        // 맨 마지막에 사람이 있다면 내리기
        int l = point[n-1];
        if(people[l]){
            people[l] = false;
        }
        for(int i=n-2;i>=0;--i){
            int c = point[i];
            if(people[c]){
                int nc = point[i+1];
                if(canPutOrMove(nc)){
                    putOrMove(nc); 
                    people[c] = false;
                    if(i+1 == n-1) people[nc] = false;
                    else people[nc] = true;
                }
            }
        }
    }
    static boolean canPutOrMove(int idx){
        return (!people[idx] && rail[idx] > 0);
    }
    static void putOrMove(int idx){
        rail[idx]--;
        if(rail[idx] == 0){
            k--;
        }
    }
    static void put(){
        int f = point[0];
        if(canPutOrMove(f)){
            putOrMove(f);
            people[f] = true;
        }
    }
}