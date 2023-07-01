import sys
ip = sys.stdin.readline
T = int(ip())


def can_grouping(x1, x2, y1, y2, r):
    return (x1-x2)**2 + (y1-y2)**2 <= r ** 2


def find_parent(parent, x):
    if parent[x] != x:
        parent[x] = find_parent(parent, parent[x])
    return parent[x]


def union_parent(parent, a, b):
    pa = find_parent(parent, a)
    pb = find_parent(parent, b)
    if pa < pb:
        parent[pb] = pa
    else:
        parent[pa] = pb


while T:
    T -= 1
    n = int(ip())
    parent = [i for i in range(n)]
    _list = []
    for i in range(n):
        _list.append(tuple(map(int, ip().split())))
    for i in range(n-1):
        for j in range(i+1, n):
            x1, y1, r1 = _list[i]
            x2, y2, r2 = _list[j]
            if can_grouping(x1, x2, y1, y2, r1 + r2):
                union_parent(parent, i, j)
    for i in range(n):
        find_parent(parent, i)
    print(len(set(parent)))
