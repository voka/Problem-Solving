import sys
sys.setrecursionlimit(100000)
ip = sys.stdin.readline 

N,M,R = map(int,ip().split())
visited = [False for _ in range(N+1)]
graph = [[] for _ in range(N+1)]
for i in range(M):
    u,v = map(int,ip().split())
    graph[u].append(v)
    graph[v].append(u)

for i in range(1,N+1):
    graph[i] = sorted(graph[i],reverse=True)

answer = [0]*(N+1)
cnt = 0
def dfs(r):
    global cnt
    cnt += 1
    answer[r] = cnt
    visited[r] = True
    for next_v in graph[r]:
        if visited[next_v] == False:
            dfs(next_v)     
dfs(R)
for i in answer[1:]:
    print(i)