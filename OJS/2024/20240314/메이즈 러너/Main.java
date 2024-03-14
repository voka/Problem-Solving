import java.io.*;
import java.util.*;
class REC implements Comparable<REC>{
	int r;
	int c;
	int l;
	int size; 
	public REC(int r, int c, int l) {
		this.r = r;
		this.c = c;
		this.l = l;
		this.size = l*l;
	}
	@Override
	public int compareTo(REC o) {
		if(this.size > o.size) return 1;
		else if(this.size < o.size) return -1;
		if(this.r > o.r) return 1;
		else if(this.r < o.r) return -1;
		if(this.c > o.c) return 1;
		else if(this.c < o.c) return -1;
		return 0;
	}
}
public class Main {
	static int N,M,K,ex,ey,ans = 0;
	static int[][] map,pMap;
	static int[] dx = {1,-1,0,0}, dy = {0,0,1,-1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		pMap = new int[N][N];
		for(int i=0;i<N;++i) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
            }
		}
		for(int i=0;i<M;++i) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			pMap[x][y]++;
		}
		st = new StringTokenizer(br.readLine());
		ex = Integer.parseInt(st.nextToken())-1;
		ey = Integer.parseInt(st.nextToken())-1;
		map[ex][ey] = -1;
        for(int i=0;i<K;++i){
            moveAll();
            if(rotate()) break; // 모든 사람이 탈출하면 반복 종료
        }
        System.out.println(ans);
		printExit();
	}
    static void printExit(){
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                if(map[i][j] == -1){
                    System.out.printf("%d %d\n", i+1,j+1);
                }
            }
        }
    }
	static boolean checkRange(int x1, int y1) {
		return (x1>=0) && (y1>=0) && (x1<N) && (y1<N);
	}
	static int getDis(int x1,int y1, int x2, int y2) {
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}
	static void moveAll() {
        int[][] nextMap = new int[N][N];
		//참가자 이동
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                if(pMap[i][j] != 0){
                    boolean flag = false;
			        int cDis = getDis(i,j,ex,ey);
                    for(int d=0;d<4;++d) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if(!checkRange(nx,ny)) continue; // 미로 밖으로 나갈 수 없음.
                        if(map[nx][ny] > 0) continue;// 벽이 있으면 움직일 수 없음. 
                        int nDis = getDis(nx,ny,ex,ey);
                        if(cDis > nDis) { //현재 거리보다 출구에 가까워진다면 이동
                            ans += pMap[i][j];
                            if(map[nx][ny] != -1) nextMap[nx][ny] += pMap[i][j];
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        nextMap[i][j] += pMap[i][j];
                    }
                }
            }
        }
        pMap = nextMap;
	}

	static boolean rotate() {
		PriorityQueue<REC> pq = new PriorityQueue<>();
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                if(pMap[i][j]!=0){
                    int r = Math.max(i, ex); 
                    int c = Math.max(j, ey);
                    int h = Math.abs(i - ex) + 1;
                    int w = Math.abs(j - ey) + 1;
                    int l = Math.max(h, w);
                    r = Math.max(0, r-l+1);
                    c = Math.max(0, c-l+1);
                    pq.add(new REC(r,c,l));
                }
            }
        }
        if(pq.size() == 0) return true;
		REC minREC = pq.poll();
		for(int i=minREC.r;i<minREC.r + minREC.l;++i) {
			for(int j=minREC.c; j<minREC.c + minREC.l;++j) {
				if(map[i][j] > 0) { // 벽 내구도가 있는 경우에마
					map[i][j]--; // 내구도 감시키기
				}
			}
		}
		// 회전 시키기
		int l = minREC.l; 
		// 회전 하는 줄 수 (가장 바깥 줄 부터 안쪽 줄 까지)
		int count = l / 2;
		int sx = minREC.r;
		int sy = minREC.c;
		int[][] newMap = new int[N][N];
		int[][] newPMap = new int[N][N];
		// 값 복사
		for(int i=sx;i<sx+l;++i) {
			for(int j=sy;j<sy+l;++j) {
				newMap[i][j] = map[i][j];
				newPMap[i][j] = pMap[i][j];
			}
		}
		for(int cc=0;cc<count;++cc) {
			// 왼쪽 -> 윗줄
			for(int i=0;i<l;++i) {
				newMap[sx+cc][sy +i] = map[(sx+l-1) - i][sy + cc];
				newPMap[sx+cc][sy +i] = pMap[(sx+l-1) - i][sy + cc];
                
			}
			// 윗줄 -> 오른쪽
			for(int i=0;i<l;++i) {
				newMap[(sx+l-1) - i][(sy+l-1) - cc] = map[sx + cc][(sy+l-1) - i];
				newPMap[(sx+l-1) - i][(sy+l-1) - cc] = pMap[sx + cc][(sy+l-1) - i];
			}
			// 오른쪽 -> 밑쪽
			for(int i=0;i<l;++i) {
				newMap[(sx+l-1) - cc][sy+i] = map[(sx+l-1)-i][(sy+l-1) - cc];
				newPMap[(sx+l-1) - cc][sy+i] = pMap[(sx+l-1)-i][(sy+l-1) - cc];
			}
            // 밑줄 -> 왼쪽
            for(int i=0;i<l;++i){
                newMap[sx+i][sy+cc] = map[(sx+l-1)-cc][sy+i];
                newPMap[sx+i][sy+cc] = pMap[(sx+l-1)-cc][sy+i];
            }
			
		}

		// 회전한 값 복사
		for(int i=sx;i<sx+l;++i) {
			for(int j=sy;j<sy+l;++j) {
				map[i][j] = newMap[i][j];
				pMap[i][j] = newPMap[i][j];
                if(map[i][j] == -1){
                    ex = i;
                    ey = j;
                }
			}
		}
		return false;
		
	}
}