import sys

ip = sys.stdin.readline

N = int(ip())

_list = [list(map(str, ip().rstrip())) for _ in range(N)]

parent = [i for i in range(N)]

mydict = dict()

for i in range(26):
    mydict[chr(ord('a') + i)] = i + 1
    mydict[chr(ord('A') + i)] = 26 + i + 1


def find_p(x):
    if parent[x] != x:
        parent[x] = find_p(parent[x])
    return parent[x]


def union_p(a, b):
    pa = find_p(a)
    pb = find_p(b)
    parent[min(pa, pb)] = max(pa, pb)


total = 0

edges = []
for i in range(N):
    for j in range(N):
        cur = _list[i][j]
        if cur != '0':
            cost = mydict[cur]
            total += cost
            edges.append((cost, i, j))

ans = 0
edges.sort()
for (cost, i, j) in edges:
    if find_p(i) != find_p(j):
        union_p(i, j)
        ans += cost
flag = True
pre = find_p(0)
for i in range(1, N):  # 모두 같아야 함 parent 의 요소가
    if pre != find_p(i):
        flag = False
        break

if flag:
    print(total - ans)
else:
    print(-1)
