import sys
from pprint import pprint
ip = sys.stdin.readline
N = int(ip())
maps = [list(map(int,ip().split())) for _ in range(N)]
# (1,2) -> (N,N)
dp = [[[0]*3 for _ in range(N+1)] for _ in range(N+1)]

def can(y,x):
    return 0 <= y < N and 0 <= x < N 
# 우, 우하단, 하 순
dy = [0,1,1]
dx = [1,1,0]

dp[0][1][0] = 1

# y,x,놓인 방향
def dfs(y,x,i):
    if i == 0: # 가로로 놓여 있을 경우
        # 가로 -> 가로
        if can(y,x+1) and maps[y][x+1] != 1:
            dp[y][x+1][0] += dp[y][x][0]
            dfs(y,x+1,0) # 가로 방향 
        # 가로 -> 대각선
        if can(y+1,x+1) and maps[y][x+1] != 1 and maps[y+1][x] != 1 and maps[y+1][x+1] != 1:
            dp[y+1][x+1][1] += dp[y][x][0]
            dfs(y+1,x+1,1)
    elif i == 2: # 세로 방향인 경우
        # 세로 -> 세로
        if can(y+1,x) and maps[y+1][x] != 1 :
            dp[y+1][x][2] += dp[y][x][2]
            dfs(y+1,x,2)
        #세로 -> 대각선
        if can(y+1,x+1) and maps[y][x+1] != 1 and maps[y+1][x] != 1 and maps[y+1][x+1] != 1:
            dp[y+1][x+1][1] += dp[y][x][2]
            dfs(y+1,x+1,1)
    else: # 대각선 방향인 경우
        # 대각선 -> 가러
        if can(y,x+1) and maps[y][x+1] != 1:
            dp[y][x+1][0] += dp[y][x][1]
            dfs(y,x+1,0) # 가로 방향 
        # 대각선 -> 세로
        if can(y+1,x) and maps[y+1][x] != 1 :
            dp[y+1][x][2] += dp[y][x][1]
            dfs(y+1,x,2)
        # 대각선 -> 대각선        
        if can(y+1,x+1) and maps[y][x+1] != 1 and maps[y+1][x] != 1 and maps[y+1][x+1] != 1:
            dp[y+1][x+1][1] += dp[y][x][1]
            dfs(y+1,x+1,1)

dfs(0,1,0)
#pprint(dp)
print(dp[N-1][N-1])
        
            