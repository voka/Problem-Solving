import sys
from pprint import pprint 
ip = sys.stdin.readline 
sys.setrecursionlimit(10000)
"""
goal: 청소하는 영역의 개수 구하기
map : N x M
    - map[i][j] == 0 : 청소되지 않은 상태 
    - map[i][j] == 1 : 벽이 있어서 통과 X 
    - 모든 테두리는 벽으로 되어 있음. 
direction : O (상하좌우)
    0 : 위
    1 : 오른쪽 
    2 : 아래쪽
    3 : 왼쪽 
todo
    - 청소 X된 칸 -> 청소 
        -  주변 4칸 중 청소 x된 칸이 없는 경우 
            - 방향 유지, 후진 가능 -> 후진 후 1번 재실행 
            - 뒤쪽 벽이면 작동 멈춤
        - 주변 4칸 중 청소 x된 칸이 있는 경우 
            - 반시계 90도 회전 
            - 앞쪽 칸이 청소되지 않은 빈칸 -> 전진 
            - 1번으로 돌아가기 
"""
N,M = map(int,ip().split())
y,x,sd = map(int,ip().split())
maps = [list(map(int,ip().split())) for _ in range(N)]
dx = [0,1,0,-1]
dy = [-1,0,1,0]
turn = {0:3,1:0,2:1,3:2} # 반시계 방향 회전 정보
def go_to_back(x,y,dir):
    nx,ny = x - dx[dir], y - dy[dir]
    if 0 <= ny < N and 0 <= nx < M and maps[ny][nx] != 1:
        return nx,ny,True
    else:
        return x,y,False
ans = 0
def turn_and_clean_if_dirty(x,y,dir):
    global ans
    ndir = turn[dir]
    nx,ny = x + dx[ndir], y + dy[ndir]
    if 0 <= ny < N and 0 <= nx < M and maps[ny][nx] == 0:
        ans += 1
        maps[ny][nx] = 2 #  청소한 칸
        return nx,ny,ndir
    return x,y,ndir
def check_there_is_dirty_room(x,y):
    for i in range(4):
        nx,ny = x + dx[i], y + dy[i]
        if 0 <= ny < N and 0 <= nx < M and maps[ny][nx] == 0:
            return True
    return False
 
def cleaning(x,y,dir):
    global ans 
    #print(x,y,dir)
    pprint(maps)
    if maps[y][x] == 0:
        maps[y][x] = 2
        ans += 1
    elif check_there_is_dirty_room(x,y):
        x,y,dir = turn_and_clean_if_dirty(x,y,dir)    
    else:
        x,y,result = go_to_back(x,y,dir)
        if result == False:
            return            
    cleaning(x,y,dir)

cleaning(x,y,sd)
#pprint(maps)
print(ans)       
        
