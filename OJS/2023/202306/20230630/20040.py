import sys
ip = sys.stdin.readline
n, m = map(int, ip().split())
parent = [i for i in range(n)]


def find_parent(x):
    if parent[x] != x:
        parent[x] = find_parent(parent[x])
    return parent[x]


def union_parent(a, b):
    pa = find_parent(a)
    pb = find_parent(b)
    if pa == pb:
        return 1
    elif pa < pb:
        parent[pb] = pa
    else:
        parent[pa] = pb
    return 0


flag = True
answer = 0
for i in range(m):
    a, b = map(int, ip().split())
    if flag:
        result = union_parent(a, b)
        if result == 1:
            answer = i + 1
            flag = False
print(answer)
