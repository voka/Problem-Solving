import sys
from pprint import pprint
ip = sys.stdin.readline
r, c = map(int, ip().split())
maps = [list(ip().rstrip()) for _ in range(r)]
# print(maps)
visited = [[0]*c for _ in range(r)]

dx = [1, 1, 1]
dy = [-1, 0, 1]


ans = 0


def dfs(x, y):
    global ans, visited
    # pprint(visited)
    if x == c-1:
        ans += 1
        return 1
    for i in range(3):
        nx, ny = x+dx[i], y + dy[i]
        # print(nx, ny)
        if 0 <= nx < c and 0 <= ny < r and maps[ny][nx] != 'x' and visited[ny][nx] == 0:
            visited[ny][nx] = 1
            temp = dfs(nx, ny)
            # print("temp")
            # print(temp)
            if temp == 1:
                return 1


for i in range(r):
    visited[i][0] = 1
    dfs(0, i)
print(ans)
