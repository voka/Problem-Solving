import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Point{
    int x;
    int y;
    public Point(int x, int y){
        this.x = x; 
        this.y = y;
    }
}
public class Sub {
    static int n;
    static int[] parents;
    
    public static boolean checkDistance(Point s, Point e){
    
        return (Math.abs(s.x-e.x) + Math.abs(s.y-e.y)) <= 1000;
    }
    public static int fp(int x){
        if(parents[x] != x){
            parents[x] = fp(parents[x]);
        }
        return parents[x];
    }

    public static void up(int a, int b){
        int ap = fp(a);
        int bp = fp(b);
        if(ap < bp){
            parents[b] = ap;
        }else{
            parents[a] = bp;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int idx = 0;
        String[] answer = new String[T];
        while(T > 0){
            // Test case 개수 줄이기
            T--;
            List<Point> stores = new ArrayList<>();
            n = Integer.parseInt(br.readLine());
						// 시작점, 도착점, 편의점들
            parents = new int[n+2];

            String[] temp = br.readLine().split(" ");
            Point sp = new Point(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
            
					  for(int i=0;i<n;++i){
                parents[i] = i; // parents 배열 초기화
                temp = br.readLine().split(" ");
                stores.add(new Point(Integer.parseInt(temp[0]),Integer.parseInt(temp[1])));
            }
            parents[n] = n;
            parents[n+1] = n+1;
            temp = br.readLine().split(" ");
            Point ep = new Point(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
            // 입력 끝
            
						// 시작점, 끝점 union
            if(checkDistance(sp, ep)) {
                answer[idx++] = "happy";
                continue;
            }
						// 각 편의점들끼리 union
            for(int i=0;i<n;++i){
                for(int j=i+1;j<n;++j){
                    if(fp(i+1) != fp(j+1) && checkDistance(stores.get(i), stores.get(j))){
                        up(i+1,j+1);
                    }
                }
            }
            
            for(int i=0;i<n;++i){
								// 시작점과 각 편의점들 union
                if(fp(0) != fp(i+1) && checkDistance(sp,stores.get(i))){
                    up(0, i+1);
                }
								// 끝점과 각 편의점들 union
                if(fp(n+1) != fp(i+1) && checkDistance(ep,stores.get(i))){
                    up(n+1, i+1);
                }
            }
						// parents 배열 업데이트 안된 경우를 대비
            for(int i=0;i<n+2;++i){
                fp(i);
            }
			for(int i=0;i<n+2;++i){
                fp(i);
            }
            if(parents[0] == parents[n+1]) answer[idx++] = "happy";
            else answer[idx++] = "sad";
        }
        for(int i=0;i<answer.length;++i){
            System.out.println(answer[i]);
        }
    }    
}