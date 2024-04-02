import java.io.*;
import java.util.*;
public class Main {
    static int L,N,Q;
    static int[][] map;
    static int[] r,c,h,w,k,attack;
    static boolean[] out;
    static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        map = new int[L][L];
        for(int i=0;i<L;++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<L;++j){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        r = new int[N];
        c = new int[N];
        h = new int[N];
        w = new int[N];
        k = new int[N];
        attack = new int[N];
        out = new boolean[N];
        for(int i=0;i<N;++i){
            st = new StringTokenizer(br.readLine());
            r[i] = Integer.parseInt(st.nextToken())-1;
            c[i] = Integer.parseInt(st.nextToken())-1;
            h[i] = Integer.parseInt(st.nextToken());
            w[i] = Integer.parseInt(st.nextToken());
            k[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0;i<Q;++i){
            boolean[] visited = new boolean[N];
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken())-1;
            int d = Integer.parseInt(st.nextToken());
            if(out[id]) continue;
            canPush(id,d,visited);
            // 명령 받은 기사가 움직이지 못하면 다음 명령으로
            if(!visited[id]) continue;
            for(int j=0;j<N;++j){
                if(visited[j]) {
                    r[j] += dx[d];
                    c[j] += dy[d];
                } 
            }
            checkKnight(id,visited);

        }
        int ans = 0;
        for(int i=0;i<N;++i){
            if(!out[i]) ans += attack[i];
        }
        System.out.println(ans);
    }    
    static void checkKnight(int id, boolean[] visited){
        for(int q=0;q<N;++q){
            if(id == q || out[q] || !visited[q]) continue;
            for(int i=r[q];i<r[q] + h[q];++i){
                for(int j=c[q];j<c[q] + w[q];++j){
                    if(map[i][j] == 1) attack[q]++;
                }
            }
            if(k[q] <= attack[q]) out[q] = true;
        }
    }
    static boolean checkRange(int x, int y){
        return (x>=0) && (y>=0) && (x<L) && (y<L);
    }
    static int findNext(int id, int x, int y){
        for(int q=0;q<N;++q){
            if(id == q || out[q]) continue;
            for(int i=r[q];i<r[q] + h[q];++i){
                for(int j=c[q];j<c[q] + w[q];++j){
                    if((i == x) && (j == y)) return q;
                }
            }
        }
        return -1;
    }
    static boolean canPush(int id, int d, boolean[] visited){
        for(int i=r[id];i<r[id] + h[id];++i){
            for(int j=c[id];j<c[id] + w[id];++j){
                int nx = i + dx[d];
                int ny = j + dy[d];
                // 벽이 있다면 이동 불가
                if(!checkRange(nx,ny)) return false;
                if(map[nx][ny] == 2) return false;
                int next = findNext(id,nx,ny);
                if(next != -1){
                    if(visited[next]) continue;
                    // 기사가 있으므로 연쇄 반응
                    if(!canPush(next,d,visited)){
                        // 다음 기사가 움직일 수 없는 경우 전부 움직이지 못함
                        return visited[id] = false;
                    }
                }
            }
        }
        return visited[id] = true;
    }

}