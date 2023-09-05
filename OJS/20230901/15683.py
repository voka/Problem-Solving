import sys
from copy import deepcopy
from pprint import pprint
from collections import deque
ip = sys.stdin.readline 
N,M = map(int,ip().split())
rotate = [4,2,4,4,1]
dx = [1,0,-1,0] # 우, 하 , 좌 , 상 순
dy = [0,-1,0,1]
maps = []
cctv_point = []
total = N * M
visited_origin = [[0]*M for _ in range(N)] 
for i in range(N):
    temp = list(map(int,ip().split())) 
    for j in range(M):
        if temp[j] == 6:
            total -= 1
            visited_origin[i][j] = 1
        elif temp[j] != 0:
            visited_origin[i][j] = 1
            total -= 1
            cctv_point.append((j,i,temp[j]))
    maps.append(temp)
cctv_n = len(cctv_point)

results = []
def make_product(idx,temp):
    if idx == cctv_n:
        results.append(temp)
        return
    x,y,cur = cctv_point[idx]
    for i in range(rotate[cur-1]):
        make_product(idx+1,temp + [i])
make_product(0,[])
#print(len(results))


def fill_line(x,y,dir,visited):
    cnt = 0
    while True:
        nx,ny = x + dx[dir],y+dy[dir]
        if 0 <= nx < M and 0 <= ny < N:
            if maps[ny][nx] == 6:
                break
            if visited[ny][nx] == 0:
                visited[ny][nx] = 1
                cnt += 1
        else:
            break
        x,y = nx,ny
    return visited, cnt
ans = M*N
for cur in results:
    visited = deepcopy(visited_origin)
    cur_total = 0
    for i in range(cctv_n):
        x,y,num = cctv_point[i]
        cidx = cur[i]
        if num == 1 : # 한쪽 방향으로만 진행
            visited,tmp = fill_line(x,y,cidx,visited)
        elif num == 2 : # 좌, 우 혹은 상, 하 로만 진행
            visited,tmp1 = fill_line(x,y,cidx,visited)
            visited,tmp2 = fill_line(x,y,cidx+2,visited)
            tmp = tmp1 + tmp2
        elif num == 3: # 현재 방향 + 다음 방향으로 진행
            visited,tmp1 = fill_line(x,y,cidx,visited)
            visited,tmp2 = fill_line(x,y,(cidx+1)%4,visited)
            tmp = tmp1 + tmp2
        elif num == 4: # 다음 방향만 뺴고 진행
            visited,tmp1 = fill_line(x,y,cidx,visited)
            visited,tmp2 = fill_line(x,y,(cidx+2)%4,visited)
            visited,tmp3 = fill_line(x,y,(cidx+3)%4,visited)
            tmp = tmp1 + tmp2 + tmp3
        elif num == 5: #  모든 방향 다 진행
            visited,tmp1 = fill_line(x,y,0,visited)
            visited,tmp2 = fill_line(x,y,1,visited)
            visited,tmp3 = fill_line(x,y,2,visited)
            visited,tmp4 = fill_line(x,y,3,visited)
            tmp = tmp1 + tmp2 + tmp3 + tmp4
        cur_total += tmp
    #pprint(visited)
    ans = min(ans,total - cur_total)
print(ans)