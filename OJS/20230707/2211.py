import sys
import heapq
ip = sys.stdin.readline
N, M = map(int, ip().split())

# 최소 개수의 회선만 복구
# 슈퍼 컴퓨터와 다른 컴퓨터 사이의 최단 거리를 구해야 함

graph = [[] for _ in range(N+1)]
dist = [1e9 for _ in range(N+1)]

for i in range(M):
    a, b, c = map(int, ip().split())
    graph[a].append((c, b))
    graph[b].append((c, a))

for i in range(N):
    graph[i].sort()

dist[1] = 0

myque = []
heapq.heappush(myque, (0, 1))
answer = {}
while myque:
    cost, cur = heapq.heappop(myque)
    if dist[cur] < cost:
        continue
    for (n_cost, n_ext) in graph[cur]:
        t_cost = cost + n_cost
        if dist[n_ext] > t_cost:
            answer[n_ext] = cur
            dist[n_ext] = t_cost
            heapq.heappush(myque, (t_cost, n_ext))

print(len(answer))
for key in answer:
    print(key, answer[key])
