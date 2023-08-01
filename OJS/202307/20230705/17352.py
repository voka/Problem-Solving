import sys
ip = sys.stdin.readline
N = int(ip())
parent = [i for i in range(N+1)]


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


for i in range(N-2):
    a, b = map(int, ip().split())
    union_parent(a, b)


for i in range(N):
    find_parent(i)

pre = parent[1]
pre_i = 1
next_i = -1
for i in range(2, N+1):
    if pre != parent[i]:
        next_i = i
        break
    pre_i = i
print("{} {}".format(pre_i, next_i))
