import sys
from collections import deque
ip = sys.stdin.readline

N, M = map(int, ip().split())

maps = [list(map(int, ip().split())) for _ in range(N)]

dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]

visited = [[0]*M for _ in range(N)]

myset = set()


def bfs(i, j):
    result = [(i, j)]
    myque = deque()
    myque.appendleft((i, j))
    while myque:
        ci, cj = myque.pop()
        myset.add((ci, cj))  # 다리는 섬을 지나갈 수 없음
        for i in range(4):
            ni, nj = dy[i] + ci, dx[i] + cj
            if 0 <= ni < N and 0 <= nj < M and visited[ni][nj] == 0:
                if maps[ni][nj] == 1:
                    result.append((ni, nj))
                    myque.append((ni, nj))
                visited[ni][nj] = 1
    return result


def get_area():
    result = []
    for i in range(N):
        for j in range(M):
            if maps[i][j] == 1 and visited[i][j] == 0:
                visited[i][j] = 1
                result.append(bfs(i, j))
    return result


area_list = get_area()
an = len(area_list)
# print(area_list)

parent = [i for i in range(an)]


def find_p(x):
    if parent[x] != x:
        parent[x] = find_p(parent[x])
    return parent[x]


def union_p(a, b):
    pa = find_p(a)
    pb = find_p(b)
    parent[min(pa, pb)] = max(pa, pb)


edges = []
for i in range(an):
    for j in range(i+1, an):
        i_list = area_list[i]
        j_list = area_list[j]
        for ii, ij in i_list:
            for ji, jj in j_list:
                min_i, max_i = min(ii, ji), max(ii, ji)
                min_j, max_j = min(ij, jj), max(ij, jj)
                i_diff = max_i - min_i
                j_diff = max_j - min_j
                if i_diff == 0 and j_diff > 2:  # y 가 같은 경우 j를 이용해 가로 길이 측정
                    flag = True
                    for j_id in range(min_j+1, max_j):
                        if (ii, j_id) in myset:
                            flag = False
                            break
                    if flag:
                        # print(i, j)
                        # print(j_diff, ii, ij, ji, jj)
                        edges.append((j_diff-1, i, j))
                if j_diff == 0 and i_diff > 2:  # x 가 같은 경우 i를 이용해 세로 길이 측정
                    flag = True
                    for i_id in range(min_i+1, max_i):
                        if (i_id, jj) in myset:
                            flag = False
                            break
                    if flag:
                        # print(i, j)
                        # print(i_diff, ii, ij, ji, jj)
                        edges.append((i_diff-1, i, j))

edges.sort()
answer = 0
for cost, i, j in edges:
    # print(cost, i, j)
    if find_p(i) != find_p(j):
        union_p(i, j)
        answer += cost

for i in range(an):
    find_p(i)
flag = True
pre = parent[0]
for i in range(1, an):
    if pre != parent[i]:
        flag = False

if flag:
    print(answer)
else:
    print(-1)

"""
크루스칼 알고리즘 응용버전 느낌 ㅇㅇ
1. 섬을 구분한다.
- 섬의 각 좌표를 리스트화 하여 섬을 나눈다. 
2. 구분한 섬 마다 섬을 지나지 않으면서 x 혹은 y 좌표만 같은 두 섬의 길이를 구해 edge에 담는다. 
3. edge를 거리의 최소순으로 정렬한다. 
4. 두 섬을 연결하며 든 비용을 구한다. 

"""
