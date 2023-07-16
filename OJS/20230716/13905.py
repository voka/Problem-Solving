import sys
from collections import deque
ip = sys.stdin.readline
N, M = map(int, ip().split())
s, e = map(int, ip().split())

parent = [i for i in range(N+1)]
graph = [[] for _ in range(N+1)]


def find_p(x):
    if parent[x] != x:
        parent[x] = find_p(parent[x])
    return parent[x]


def union_p(a, b):
    pa = find_p(a)
    pb = find_p(b)
    if pa < pb:
        parent[pb] = pa
    else:
        parent[pa] = pb


edges = []
for _ in range(M):
    a, b, t = map(int, ip().split())
    edges.append((a, b, t))

edges.sort(key=lambda x: -x[2])  # 경로의 최소값이 최대값이 되도록
for (a, b, t) in edges:
    if find_p(a) == find_p(b):
        continue
    union_p(a, b)
    graph[a].append((t, b))
    graph[b].append((t, a))

# Maximum Spanninf Tree에서 s -> e 길찾기 하기

answer = 0
q = deque()
q.append((s, 10**6))
visited = [0] * (N+1)
visited[s] = 1

while q:
    cur, cost = q.popleft()
    if cur == e:
        answer = cost
        break
    for n_cost, next in graph[cur]:
        if visited[next] == 0:
            cost = min(cost, n_cost)
            q.append((next, cost))
            visited[next] = 1

print(answer)
