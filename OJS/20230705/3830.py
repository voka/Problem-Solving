import sys
ip = sys.stdin.readline
sys.setrecursionlimit(10**5)
while True:
    N, M = map(int, ip().split())
    if N == 0 and M == 0:
        break
    parent = [i for i in range(N+1)]
    weight = [0]*(N+1)  # weight[i] -> i의 루트 노드보다  weight[i]만큼 더 무거움

    def find_parent(x):
        if parent[x] == x:
            return x
        px = find_parent(parent[x])  # 대표 갱신
        weight[x] += weight[parent[x]]  # 대표 무게값 갱신
        parent[x] = px
        return parent[x]

    def union_parent(a, b, w):
        pa = find_parent(a)
        pb = find_parent(b)
        if pa != pb:  # root node를 pa로 만들기 위한 과정
            # root node를 pa 로 만들려면 rootb와 roota의 무게 차이를 알아야 한다.

            # weight[a] -> a 가 roota 보다 무거운 값
            # weight[b] -> b 가 rootb 보다 무거운 값
            # - weight[b] -> rootb가 b 보다 무거운 값
            # w -> b 가 a 보다 무거운 값
            # weight[a] + w -> b 가 roota보다 무거운 값
            # weight[a] + w - weight[b] ->  rootb가 roota 보다 무거운 값
            weight[pb] = weight[a] - weight[b] + w  # pb가 pa 보다 얼마나 무거운지
            parent[pb] = pa

    for o in range(M):
        _list = ip().rstrip()
        command = _list[0]
        # print()
        if command == "!":
            a, b, w = map(int, _list[1:].split())
            union_parent(a, b, w)
            # print(a, b, w)
        elif command == "?":
            a, b = map(int, _list[1:].split())
            # if find_parent
            # print(a, b)
            if find_parent(a) != find_parent(b):
                print("UNKNOWN")

            else:
                print(weight[b] - weight[a])
