import sys
from pprint import pprint
from collections import deque
ip = sys.stdin.readline 
N,M = map(int,ip().split())
maps = [list(ip().rstrip()) for _ in range(N)]
dx = [1,0,-1,0]
dy = [0,1,0,-1]
#print(maps)
def bfs(x,y):
    myque = deque()
    myque.append((x,y,0))
    ans = 0
    visited = [[-1]*M for _ in range(N)]
    visited[y][x] = 0 
    while myque:
        cx,cy,t = myque.pop()
        for i in range(4):
            nx,ny = cx + dx[i], cy + dy[i]
            if 0 <= nx < M and 0 <= ny < N and maps[ny][nx] == 'L' and visited[ny][nx] == -1 :#or visited[ny][nx] > t):
                visited[ny][nx] = t+1
                ans = max(ans,t+1)
                myque.appendleft((nx,ny,t+1))
    #pprint(visited)
    #print(ans)
    return ans 
ans = -1
for i in range(N):
    for j in range(M):
        if maps[i][j] == 'L':
            ans = max(ans,bfs(j,i))
print(ans)

