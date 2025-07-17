
import java.io.*;
import java.util.*;
class Point{
	int x;
	int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
public class Main {
	static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
	static int R,C,N;
	static int[][] map;
	static boolean[][] visit;
	public static void main(String args[])throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new int[R][C];
		for(int i=0;i<R;++i) {
			String s = br.readLine();
			for(int j=0;j<C;++j) {
				if(s.charAt(j) == 'x') {
					map[i][j] = -1;
				}else {
					map[i][j] = 1;
				}
			}
		}
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		// System.out.println("초기맵");
		// printMap(map);
		for(int i=0;i<N;++i) {
			//System.out.println(i + "번째");
			crush(i,R - Integer.parseInt(st.nextToken()));
			//printMap(map);
		}
        printMap(map);
		
	}
	static void printMap(int[][] arr) {
		for(int i=0;i<R;++i) {
			for(int j=0;j<C;++j) {
				System.out.printf("%s",arr[i][j] == -1 ? "x" : "." );
			}
			System.out.println();
		}System.out.println();
	}
	static void crush(int i, int x) {
		if(i%2 == 0) left(x);
		else right(x);
		clean();
	}
	static void bfs(int x, int y) {
		Queue<Point> q = new ArrayDeque<>();
		q.add(new Point(x,y));
		visit[x][y] = true;
		while(!q.isEmpty()) {
			Point p = q.poll();
			for(int i=0;i<4;++i) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(!checkRange(nx,ny)) continue;
				if(visit[nx][ny]) continue;
				if(map[nx][ny] != -1) continue;
				visit[nx][ny] = true;
				q.add(new Point(nx,ny));
			}
		}
	}
	static void clean() {
		visit = new boolean[R][C];
		for(int y=0;y<C;++y) {
			if(!visit[R-1][y] && map[R-1][y] == -1) bfs(R-1,y);
		}
		int[][] nMap = new int[R][C];
		int[][] nextMap = new int[R][C];
        int maxX = 0;
		for(int x=R-2;x>=0;--x) {
			for(int y=0;y<C;++y) {
				if(!visit[x][y] && map[x+1][y] == 1) {
					nMap[x+1][y] = map[x][y]; 
                    maxX = Math.max(maxX, x);
					map[x][y] = 1;
				}
			}
		}

        // System.out.println("작업 전");
        // printMap(nMap);
        // System.out.println(R-maxX+1);
        A : for(int d=0; d<R-maxX;++d){
            nextMap = new int[R][C];
            for(int x=R-2;x>=0;--x) {
                for(int y=0;y<C;++y) {
                    if(nMap[x][y] == -1){ // 미네랄 일때
                        if(map[x+1][y] == 1){ // 밑에 칸이 남아 있다면
                            nextMap[x+1][y] = -1; // 한칸 내리기
                            // System.out.printf("(%d,%d)\n", x,y);
                            // printMap(nextMap);
                        }else {
                            break A;
                        }
                    }
                }
            }
            nMap = nextMap;
        }

        // System.out.println("작업 후");

        // printMap(nMap);
        for(int i=0;i<R;++i){
            for(int j=0;j<C;++j){
                if(nMap[i][j] == -1){
                    map[i][j] = -1;
                }
            }
        }
	}
	static void left(int x) {
		for(int y=0;y<C;++y) {
			if(map[x][y] == -1) {
				map[x][y] = 1;
				break;
			}
		}
	}
	static void right(int x) {
		for(int y=C-1;y>=0;--y) {
			if(map[x][y] == -1) {
				map[x][y] = 1;
				break;
			}
		}
	}
	static boolean checkRange(int x, int y) {
		return (x>=0) && (y>=0) && (x<R) && (y<C);
	}
	
}
/*
 * 
7 9
xx.......
.xx......
..xxx....
....xx...
.....xx..
......xx.
.......x.
2
6 4


 * 
 */