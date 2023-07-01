import sys
sys.setrecursionlimit(3 * 10**5)
ip = sys.stdin.readline
N, M, R = map(int, ip().split())
graph = [[] for _ in range(N+1)]
for _ in range(M):
    a, b = map(int, ip().split())
    graph[a].append(b)
    graph[b].append(a)
for road in graph:
    road.sort()
visited = [0]*(N+1)
count = 1


def dfs(s):
    global count
    visited[s] = count
    for n in graph[s]:
        if visited[n] == 0:
            count += 1
            dfs(n)


dfs(R)

for i in range(1, N+1):
    print(visited[i])
