import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())
points = []
edges = []
parent = [i for i in range(N)]


def find_p(x):
    if parent[x] != x:
        parent[x] = find_p(parent[x])
    return parent[x]


def union_p(a, b):
    pa = find_p(a)
    pb = find_p(b)
    parent[max(pa, pb)] = min(pa, pb)


for i in range(N):
    x, y = map(int, ip().split())
    points.append((x, y))

for i in range(M):
    a, b = map(int, ip().split())
    union_p(a-1, b-1)

for i in range(N-1):
    for j in range(i+1, N):
        dist = ((points[i][0] - points[j][0]) ** 2 +
                (points[i][1] - points[j][1]) ** 2) ** (1/2)
        edges.append((dist, i, j))
edges.sort()
ans = 0
for (dist, i, j) in edges:
    if find_p(i) != find_p(j):
        ans += dist
        union_p(i, j)
print("%.2f" % (ans))
