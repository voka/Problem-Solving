import sys
ip = sys.stdin.readline
n, q = map(int, ip().split())
sys.setrecursionlimit(10**6)
parent = [i for i in range(n+1)]
up = [i for i in range(n+1)]
rank = [1 for _ in range(n+1)]


def find_parent(x):
    if parent[x] != x:
        parent[x] = find_parent(parent[x])
    return parent[x]


def union_parent(a, b):
    pa = find_parent(a)
    pb = find_parent(b)
    if pa == pb:
        return
    parent[pa] = pb


for i in range(2, n+1):  # 직속 부모 노드
    up[i] = int(ip())

stack = []
for _ in range(n-1 + q):  # n-1개의 쿼리는 정점 제거 , q 개의 쿼리는 a,b 정점이 이어져 있는지 질의하는 것 !!!
    # n-1개의 모든 간선을 제거하기 때문에 모든 쿼리의 순서를 반대로 처리해도 된다.
    stack.append(list(map(int, ip().split())))
answer = []
# print(parent)
while stack:
    query = stack.pop()
    # print(query)
    if query[0] == 0:
        node = query[1]
        # print("merge")
       # print(node, up[node])
        union_parent(node, up[node])
    elif query[0] == 1:
        [a, b] = query[1:]
        # print("query")
        # print(a, b)
        if find_parent(a) == find_parent(b):
            answer.append("YES")
        else:
            answer.append("NO")
while answer:
    print(answer.pop())
