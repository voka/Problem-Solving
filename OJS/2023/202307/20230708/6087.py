import sys
import pprint
import heapq
ip = sys.stdin.readline
W, H = map(int, ip().split())
maps = []
dist = [[[1e9]*4 for _ in range(W)] for _ in range(H)]
c_pair = []
for i in range(H):
    temp = list(ip().rstrip())
    maps.append(temp)
    for j in range(W):
        if temp[j] == "C":
            c_pair.append((j, i))

dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]
dir = [0, 1, 2, 3]

myque = []
x, y = c_pair[0][0], c_pair[0][1]
d_x, d_y = c_pair[1][0], c_pair[1][1]
for i in range(4):
    dist[y][x][i] = 0
    heapq.heappush(myque, (0, x, y, i))
while myque:
    cost, cx, cy, c_dir = heapq.heappop(myque)
    if dist[cy][cx][c_dir] < cost:
        continue
    for i in range(4):
        nx, ny = cx + dx[i], cy + dy[i]
        n_dir = dir[i]
        # if abs(dir[i] - c_dir) == 1 and not ((dir[i] == 2 and c_dir == 3) or (dir[i] == 3 and c_dir == 2)):
        #     continue
        if 0 <= nx < W and 0 <= ny < H and maps[ny][nx] != "*":
            if c_dir != n_dir and cost + 1 <= dist[ny][nx][n_dir]:
                dist[ny][nx][n_dir] = cost + 1
                heapq.heappush(myque, (cost + 1, nx, ny, n_dir))
            elif cost < dist[ny][nx][n_dir]:
                dist[ny][nx][n_dir] = cost
                heapq.heappush(myque, (cost, nx, ny, n_dir))
# pprint.pprint(dist)
print(min(dist[d_y][d_x]))
