import sys
import heapq
ip = sys.stdin.readline
V, E, P = map(int, ip().split())
graph = [[] for _ in range(V+1)]
for _ in range(E):
    a, b, c = map(int, ip().split())
    graph[a].append((b, c))
    graph[b].append((a, c))


def solve(start):
    dist = [1e9 for _ in range(V+1)]
    dist[start] = 0
    myque = []
    heapq.heappush(myque, (0, start))
    while myque:
        cost, cur = heapq.heappop(myque)
        if dist[cur] < cost:
            continue
        for (gi, ncost) in graph[cur]:
            tcost = cost + ncost
            if tcost < dist[gi]:
                if cur == P or gi == P:
                    print(1)
                dist[gi] = tcost
                heapq.heappush(myque, (tcost, gi))
    return dist


result_1 = solve(1)
result_2 = solve(P)

if result_1[V] >= result_1[P]+result_2[V]:
    print("SAVE HIM")
else:
    print("GOOD BYE")
