import sys
from collections import deque 
ip = sys.stdin.readline 
N,M,s = map(int,ip().split())
s = s - 1 

graph = [[] for _ in range(N)]

for i in range(M):
    u,v = map(int,ip().split())
    graph[u-1].append(v-1)
    graph[v-1].append(u-1)

for i in range(N):
    graph[i] = sorted(graph[i],reverse=True)


def bfs(s):
    cnt = 1
    visited = [False for _ in range(N)]
    answer = [0 for _ in range(N)]
    myque = deque()
    myque.append(s)
    visited[s] = True
    answer[s] = cnt 
    while myque:
        c = myque.pop()
        for next_i in graph[c]:
            if visited[next_i] == False:
                visited[next_i] = True
                myque.appendleft(next_i)
                cnt += 1
                answer[next_i] = cnt
    for i in answer:
        print(i)
bfs(s)


