import sys
import heapq
ip = sys.stdin.readline
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]
T = 0
while True:
    T += 1
    N = int(ip())
    if N == 0:
        break
    maps = [list(map(int, ip().split())) for _ in range(N)]
    dist = [[1e9 for _ in range(N)] for _ in range(N)]
    myque = []
    dist[0][0] = maps[0][0]
    heapq.heappush(myque, (0, 0, maps[0][0]))
    while myque:
        cx, cy, cost = heapq.heappop(myque)
        if dist[cy][cx] < cost:
            continue
        for i in range(4):
            nx, ny = cx + dx[i], cy + dy[i]
            if 0 <= nx < N and 0 <= ny < N:
                tcost = cost + maps[ny][nx]
                if dist[ny][nx] > tcost:
                    dist[ny][nx] = tcost
                    heapq.heappush(myque, (nx, ny, tcost))

    print("Problem {}: {}".format(T, dist[N-1][N-1]))
