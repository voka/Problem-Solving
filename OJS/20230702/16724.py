import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())
maps = []
for _ in range(N):
    maps.append(ip().rstrip())

parent = [i for i in range(N*M)]


def find_parent(x):
    if parent[x] != x:
        parent[x] = find_parent(parent[x])
    return parent[x]


def union_parent(a, b):
    pa = find_parent(a)
    pb = find_parent(b)
    if pa < pb:
        parent[pb] = pa
    else:
        parent[pa] = pb


for i in range(N):
    for j in range(M):
        ci = i*M + j
        comand = maps[i][j]
        if comand == "D":
            ni = ci + M
        elif comand == "U":
            ni = ci - M
        elif comand == "R":
            ni = ci + 1
        else:
            ni = ci - 1
        union_parent(ni, ci)

for i in range(N):
    for j in range(M):
        find_parent(i*M + j)
print(len(set(parent)))
