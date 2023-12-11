import sys
from collections import deque
ip = sys.stdin.readline 
INF = 10001*1000
N,M = map(int,ip().split())
graph = [[] for _ in range(N+1)]
dist = [[INF]*(N+1) for _ in range(N+1)]
for i in range(N-1):
    a,b,w = map(int,ip().split())
    graph[a].append((b,w))
    graph[b].append((a,w))
    dist[a][b] = w
    dist[b][a] = w

def find_path(s,e):
    if dist[s][e] < INF:
         return dist[s][e]
    else:
        dist[s][s]= 0
        myque = deque()
        myque.append((0,s))
        visited = [False] * (N+1)
        visited[s] = True
        while myque:
            cost, cur = myque.popleft()
            if cur == e:
                return dist[s][e]
            for n_idx,ncost in graph[cur]:
                if not visited[n_idx]:
                    visited[n_idx] = True
                    dist[s][n_idx] = min(cost + ncost,dist[s][n_idx])
                    myque.append((cost + ncost,n_idx))
                

for i in range(M):
    start,end = map(int,ip().split())
    print(find_path(start,end))   
    
    
# import sys
# from collections import deque
# ip = sys.stdin.readline 
# INF = 10001*1000
# N,M = map(int,ip().split())
# graph = [[] for _ in range(N+1)]
# for i in range(N-1):
#     a,b,w = map(int,ip().split())
#     graph[a].append((b,w))
#     graph[b].append((a,w))

# def find_path(s,e):
#     myque = deque()
#     myque.append((0,s))
#     visited = [False] * (N+1)
#     visited[s] = True
#     while myque:
#         cost, cur = myque.popleft()
#         if cur == e:
#             return cost
#         for n_idx,ncost in graph[cur]:
#             if not visited[n_idx]:
#                 visited[n_idx] = True
#                 myque.append((cost + ncost,n_idx))
                
# for i in range(M):
#     start,end = map(int,ip().split())
#     print(find_path(start,end))   
    