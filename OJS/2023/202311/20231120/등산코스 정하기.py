import heapq

def dijkstra(dist, graph, gate, gate_set, summit_set):
    myque = []
    dist[gate] = 0
    heapq.heappush(myque,(0,gate))
    while myque:
        cost,idx = heapq.heappop(myque)
        if cost > dist[idx] :
            continue
        for (next_idx,next_cost) in graph[idx]:
            if next_idx not in gate_set:
                #현재 구간 중 가장 많이 걸리는 시간
                cur_intensity = max(next_cost,cost)
                if cur_intensity < dist[next_idx]:
                    dist[next_idx] = cur_intensity
                    if next_idx not in summit_set:
                        heapq.heappush(myque,(cur_intensity,next_idx))
    return dist

def solution(n, paths, gates, summits):
    answer = []
    min_intensity = 100000001
    summits.sort()
    gate_set = set(gates)
    summit_set = set(summits)
    graph = [[] for _ in range(n+1)]
    dist = [100000001]*(n+1)
    for [i,j,w] in paths:
        graph[i].append((j,w))
        graph[j].append((i,w))
    for g in gates:
        dijkstra(dist,graph, g, gate_set, summit_set)
    #print(dist)
    for summit in summits:
        if min_intensity > dist[summit]:
            min_intensity = dist[summit]
            answer = [summit,min_intensity]
    return answer