from collections import deque
import sys
ip = sys.stdin.readline
MAX_INT = 1000001
dx = [1, -1, 0, 0, 1, 1, -1, -1]
dy = [0, 0, 1, -1, 1, -1, 1, -1]
my_set = set()
N = int(ip())
sx, sy, count = 0, 0, 0
maps = [list(map(str, ip().rstrip())) for _ in range(N)]
maps_int = [list(map(int, ip().split())) for _ in range(N)]
for i in range(N):
    for j in range(N):
        cur = maps[i][j]
        if cur == 'P':
            sx, sy = j, i
        elif cur == "K":
            count += 1
        my_set.add(maps_int[i][j])

# print(maps, maps_int)
ans = MAX_INT
my_list = list(my_set)  # 고도 배열
my_list.sort()
start, end = 0, 0


def bfs(x, y):
    find_house = 0
    visited = [[0]*N for _ in range(N)]
    visited[y][x] = 1
    myque = deque()
    myque.append((x, y))
    while myque:
        cx, cy = myque.popleft()
        for i in range(8):
            nx, ny = cx + dx[i], cy + dy[i]
            if 0 <= nx < N and 0 <= ny < N:
                if visited[ny][nx] == 1:
                    continue
                tired = maps_int[ny][nx]
                if my_list[start] <= tired <= my_list[end]:
                    visited[ny][nx] = 1
                    if maps[ny][nx] == 'K':
                        find_house += 1
                    myque.append((nx, ny))
    return find_house


n = len(my_list)
while start < n:
    find_house = 0
    if my_list[start] <= maps_int[sy][sx] <= my_list[end]:
        find_house = bfs(sx, sy)
    # print(find_house)
    if find_house == count:
        ans = min(ans, my_list[end] - my_list[start])
        start += 1
    elif (end + 1) < n:
        end += 1
    else:
        break
print(ans)
