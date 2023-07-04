import sys
ip = sys.stdin.readline

N = int(ip())
M = int(ip())

parent = [i for i in range(N+1)]
MAX_VALUE = 1000000


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


score_board = [[MAX_VALUE]*(N+1) for _ in range(N+1)]
k_dict = {}


def get_max_score(k):
    max_point = 0
    for i in range(1, N+1):
        cur = score_board[k][i]
        if 0 < cur < MAX_VALUE:
            max_point = max(max_point, cur)
    return max_point


def select_one(t):
    k_one = t
    k_max = get_max_score(t)
    for m in k_dict[t]:
        m_max = get_max_score(m)
        if k_max > m_max:
            k_max = m_max
            k_one = m
    return k_one


for _ in range(M):
    a, b = map(int, ip().split())
    score_board[a][b] = 1
    score_board[b][a] = 1
    union_parent(a, b)

for i in range(1, N+1):
    for j in range(1, N+1):
        for k in range(1, N+1):
            if i == j:
                score_board[i][j] = 0
                continue
            new_one = score_board[i][k] + score_board[k][j]
            if score_board[i][j] > new_one:
                score_board[i][j] = new_one


# print(parent)
# print(score_board)
for i in range(1, N+1):  # 업데이트
    find_parent(i)

for i in range(1, N+1):  # 위워회별 멤버 저장
    pi = parent[i]
    if pi not in k_dict:
        k_dict[pi] = [i]
    else:
        k_dict[pi].append(i)

result = list(set(parent[1:]))
k = len(result)
for i in range(k):
    result[i] = select_one(result[i])

print(k)
result.sort()
for i in result:
    print(i)
