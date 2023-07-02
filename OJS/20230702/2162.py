import sys
ip = sys.stdin.readline


N = int(ip())
parent = [i for i in range(N)]


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


def is_meet(l1x1, l1y1, l1x2, l1y2, l2x1, l2y1, l2x2, l2y2):  # 세 점이 일직선 위에 있을 경우 선분교차를 판단하기 위한 작업
    l1x1, l1x2 = is_large(l1x1, l1x2)
    l1y1, l1y2 = is_large(l1y1, l1y2)
    l2x1, l2x2 = is_large(l2x1, l2x2)
    l2y1, l2y2 = is_large(l2y1, l2y2)
    if l1x1 <= l2x1 <= l1x2 or l1x1 <= l2x2 <= l1x2:
        if l1y1 <= l2y1 <= l1y2 or l1y1 <= l2y2 <= l1y2:
            return True
    if l2x1 <= l1x1 <= l2x2 or l2x1 <= l1x2 <= l2x2:
        if l2y1 <= l1y1 <= l2y2 or l2y1 <= l1y2 <= l2y2:
            return True
    return False


def CCW(x1, y1, x2, y2, x3, y3):  # 방향 판단 알고리즘
    return x1*y2 + x2 * y3 + x3*y1 - (x2*y1 + x3*y2 + x1*y3)


def is_large(a, b):
    if a > b:
        return b, a
    return a, b


lines = []
for _ in range(N):
    lines.append(tuple(map(int, ip().split())))

for i in range(N-1):
    for j in range(i+1, N):
        l1x1, l1y1, l1x2, l1y2 = lines[i]
        l2x1, l2y1, l2x2, l2y2 = lines[j]
        result1 = CCW(l1x1, l1y1, l1x2, l1y2, l2x1, l2y1) * \
            CCW(l1x1, l1y1, l1x2, l1y2, l2x2, l2y2)
        result2 = CCW(l2x1, l2y1, l2x2, l2y2, l1x1, l1y1) * \
            CCW(l2x1, l2y1, l2x2, l2y2, l1x2, l1y2)
        if result1 == 0 and result2 == 0:
            if is_meet(l1x1, l1y1, l1x2, l1y2, l2x1, l2y1, l2x2, l2y2):
                union_parent(i, j)
        elif result1 <= 0 and result2 <= 0:
            union_parent(i, j)
for i in range(N):
    find_parent(i)

ans_dict = {}
max_count = 0
for i in range(N):
    c = parent[i]
    if c in ans_dict:
        ans_dict[c] += 1
    else:
        ans_dict[c] = 1
for key in ans_dict:
    max_count = max(max_count, ans_dict[key])

# print(parent)
print(len(ans_dict))
print(max_count)
