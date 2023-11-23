import sys
from heapq import heappush as hpush, heappop as hpop

INF = 10**9 + 1
ip = sys.stdin.readline 

V, E = map(int,ip().split())
mac_v = V+1 # 모든 맥도날드에 연결된 점
star_v = V+2 # 모든 스타벅스에 연결된 점
mac_v_set = set()
star_v_set = set()

graph = [[] for _ in range(V+3)]

for i in range(E):
    u,v,w = map(int,ip().split())
    graph[u].append((v,w))
    graph[v].append((u,w))

M,x = map(int,ip().split())
m_list = list(map(int,ip().split()))
for i in m_list:
    mac_v_set.add(i)
    graph[mac_v].append((i,0))

S,y = map(int,ip().split())
s_list = list(map(int,ip().split()))
for i in s_list:
    star_v_set.add(i)
    graph[star_v].append((i,0))


def find_min_path_list(start):
    myque = []
    dist = [INF]*(V+3)
    dist[start] = 0
    hpush(myque,(0,start))
    while myque:
        cost,cur = hpop(myque)
        if dist[cur] < cost:
            continue
        for n_idx,ncost in graph[cur]:
            nncost = ncost + cost
            if dist[n_idx] > nncost:
                dist[n_idx] = nncost
                hpush(myque,(nncost,n_idx))
    return dist

mac_min_path = find_min_path_list(mac_v)
star_min_path = find_min_path_list(star_v)
answer = INF
for h in range(V+1):
    # 맥노날드나 스타벅스에 존재하지 않는 집인경우
    if h not in mac_v_set and h not in star_v_set:
        h_mac = mac_min_path[h]
        h_star = star_min_path[h]
        # 두가지 조건을 모두 만족할 경우
        if h_mac <= x and h_star <= y:
            answer = min(answer,h_mac + h_star)

if answer == INF:
    print(-1)
else:
    print(answer)
    

