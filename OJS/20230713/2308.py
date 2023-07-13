import sys
from collections import deque
import heapq
ip = sys.stdin.readline
# 출발 -> 1 , 도착 -> N
# 다익스트라 시간 복잡도 -> O(E * log V) -> 5000 * 5000 * log 1000 -> 5 * 5 * 1000 * 1000 * 10 -> 25 * 10000000 -> 2.5초
INF = sys.maxsize
N, M = map(int, ip().split())

graph = [[] for _ in range(N)]
for _ in range(M):
    a, b, t = map(int, ip().split())
    graph[a-1].append((b-1, t))
    graph[b-1].append((a-1, t))


def solve():
    result = [INF for _ in range(N)]
    result[0] = 0
    myque = []
    heapq.heappush(myque, (0, 0))
    while myque:
        cost, cur = heapq.heappop(myque)
        if result[cur] < cost:
            continue
        for (n_idx, n_cost) in graph[cur]:
            t_cost = n_cost + cost
            if result[n_idx] > t_cost:
                result[n_idx] = t_cost
                heapq.heappush(myque, (t_cost, n_idx))
    return result


dist = solve()
roads = set()


def get_shortest_road_list():
    myque = deque()
    myque.appendleft(N-1)
    while myque:
        last = myque.pop()
        if last == 0:
            continue
        for (n_idx, n_cost) in graph[last]:
            if (dist[n_idx] + n_cost == dist[last]):
                roads.add((n_idx, last, n_cost))
                myque.appendleft(n_idx)


get_shortest_road_list()

# print(dist)
# print(graph)
# print(roads)
origin = dist[N-1]
ans = 0
for (a, b, t) in roads:
    graph[a].remove((b, t))
    graph[b].remove((a, t))
    dist = solve()
    if dist[N-1] == INF:
        break
    ans = max(ans, dist[N-1] - origin)  # 늦출 수 있는 최대 시간
    graph[a].append((b, t))
    graph[b].append((a, t))

if dist[N-1] == INF:
    print(-1)
else:
    print(ans)
