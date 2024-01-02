import sys
ip = sys.stdin.readline


def find_parent(parent, a):
    if parent[a] != a:
        parent[a] = find_parent(parent, parent[a])
    return parent[a]


def union_parent(parent, a, b, is_cycle):
    pa = find_parent(parent, a)
    pb = find_parent(parent, b)

    if pa == pb or pa in is_cycle or pb in is_cycle:  # 두 부모가 같거나 둘 중 하나라도 cycle이 있는 집합에 포함된다면 !
        is_cycle.add(pa)
        is_cycle.add(pb)
    if pa < pb:
        parent[pb] = pa
    else:
        parent[pa] = pb


T = 1
print_answer = []
while True:
    n, m = map(int, ip().split())
    if n == 0 and m == 0:
        break
    parent = [i for i in range(n)]
    is_cycle = set()
    for _ in range(m):
        v, w = map(int, ip().split())
        union_parent(parent, v-1, w-1, is_cycle)

    for i in range(n):
        find_parent(parent, i)
    trees = set(parent)
    answer = 0
    # print(parent, trees, is_cycle)
    for t in trees:
        if t not in is_cycle:
            answer += 1
            is_cycle.add(t)
    if answer == 0:
        cur = "Case {}: No trees.".format(T)
    elif answer == 1:
        cur = "Case {}: There is one tree.".format(T)
    else:
        cur = "Case {}: A forest of {} trees.".format(T, answer)
    T += 1
    print_answer.append(cur)
for p in print_answer:
    print(p)
