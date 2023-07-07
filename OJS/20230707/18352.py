import sys
import heapq
ip = sys.stdin.readline
N, M, K, X = map(int, ip().split())
graph = [[] for _ in range(N+1)]
for _ in range(M):
    a, b = map(int, ip().split())
    graph[a].append((b, 1))
dist = [1e9 for _ in range(N+1)]
dist[X] = 0
myque = []
heapq.heappush(myque, (0, X))
while myque:
    cost, cur = heapq.heappop(myque)
    if dist[cur] < cost:
        continue
    for (gi, ncost) in graph[cur]:
        tcost = cost + ncost
        if tcost < dist[gi]:
            dist[gi] = tcost
            heapq.heappush(myque, (tcost, gi))
cnt = 0
for i in range(1, N+1):
    if dist[i] == K:
        cnt += 1
        print(i)

if cnt == 0:
    print(-1)
