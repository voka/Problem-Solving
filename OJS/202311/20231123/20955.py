import sys
ip = sys.stdin.readline 

N,M = map(int,ip().split())

parents = [i for i in range(N+1)]
def fp(x):
    if parents[x] != x :
        parents[x] = fp(parents[x])
    return parents[x]
def up(a,b):
    fa = fp(a)
    fb = fp(b)
    if fa < fb :
        parents[fb] = fa
    else:
        parents[fa] = fb
cnt = 0 # 사이클이 생기기 때문에 끊어내야 하는 간선 개수
for i in range(M):
    u,v = map(int,ip().split())
    if fp(u) != fp(v): # 두 원소가 서로 다른 집합에 속한 경우
        up(u,v)
    else: # 연결하면 사이클이 생기는 경우
        cnt += 1
    
for i in range(1,N+1):
    fp(i)
cur = set(parents[1:])
print(len(cur)-1 + cnt)
