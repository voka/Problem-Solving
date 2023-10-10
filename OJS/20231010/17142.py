"""
바이러스 퍼뜨리기 !!!

활성 상태인 바이러스는 상하좌우 인접한 곳으로 !동시에! 퍼저나감 !!!
- 1초 걸림

M 개의 바이러스를 활성 상태로 변경하려고 함. 

어떻게 M개를 골라야 바이러스가 최소 시간만에 퍼질까 ?? 
 
"""
import sys
from copy import deepcopy
from itertools import combinations
from collections import deque
from pprint import pprint
ip = sys.stdin.readline 
N,M = map(int,ip().split())
init_virus = []
maps = []
dx = [1,0,-1,0]
dy = [0,1,0,-1]
for i in range(N):
    temp = list(map(int,ip().split()))
    for j in range(N):
        if temp[j] == 2:
            temp[j] = N*N
            init_virus.append((j,i))
        elif temp[j] == 1:
            temp[j] = -2
        
    maps.append(temp)

def get_answer(mymaps):
    ans = 0
    for i in range(N):
        for j in range(N):
            if mymaps[i][j] == 0:
                return -1
            elif mymaps[i][j] != N*N:
                ans = max(mymaps[i][j],ans)
    return ans

def spread(_list):
    #print(_list)
    temp = deepcopy(maps)
    visited = [[0]*N for _ in range(N)]
    myque = deque()
    for x,y in _list:
        myque.appendleft((x,y,0))
    while myque:
        cx,cy,t = myque.pop()
        for i in range(4):
            nx,ny = cx + dx[i], cy + dy[i]
            if 0 <= nx < N and 0 <= ny < N and temp[ny][nx] != -2:
                if temp[ny][nx] == 0:
                    temp[ny][nx] = t+1
                    myque.appendleft((nx,ny,t+1))
                else:
                    if temp[ny][nx] == N * N and visited[ny][nx] == 0 : # 바이러스를 통과하는 이동은 1번만 할 수 있음
                        visited[ny][nx] = 1
                        myque.appendleft((nx,ny,t+1))
                    elif temp[ny][nx] > t+1 and temp[ny][nx] != N * N:
                        temp[ny][nx] = t+1
                        myque.appendleft((nx,ny,t+1))
                        
    #pprint(temp)
    return get_answer(temp)

    
def solve():
    ans = N * N 
    mylist = combinations(init_virus,M)
    for _list in mylist:
        cur = spread(_list)
        if cur != -1:
            ans = min(ans,cur)
    if ans == N * N :
        print(-1)
    else:
        print(ans)
            
solve()