import sys
import pprint
from collections import deque
ip = sys.stdin.readline
dx, dy = [1, 0, -1, 0], [0, 1, 0, -1]

R, C = map(int, ip().split())
maps = []
for i in range(R):
    maps.append(list(ip().rstrip()))

parent = [i for i in range(R*C + 1)]


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


# 1. bfs로 얼음 녹이기
# -> 다음 녹일 곳을 deque로 저장
# 2. 두 백조의 좌표를 이용해서 union_parent
# 3. union_parent 의 결과로 두 백조의 좌표가 동일한 부모를 가지면 종료

# 전처리 . -> 1 , X -> 0 , L -> 2 로 바꾸고 좌표 저장
L_point = []

for i in range(R):
    for j in range(C):
        cur = maps[i][j]
        if cur == ".":
            maps[i][j] = 1
        elif cur == "L":
            maps[i][j] = 1
            L_point.append(i*C + j)
        else:
            maps[i][j] = 0

visited = [[0]*C for _ in range(R)]

melt_point = deque()


def bfs(x, y):
    visited[y][x] = 1
    myque = deque()
    myque.append((x, y))
    while myque:
        cx, cy = myque.pop()
        for i in range(4):
            nx, ny = cx + dx[i], cy + dy[i]
            if 0 <= nx < C and 0 <= ny < R and not visited[ny][nx]:
                if maps[ny][nx] == 1:
                    union_parent(ny * C + nx, cy * C + cx)
                    visited[ny][nx] = 1
                    myque.appendleft((nx, ny))
                elif maps[ny][nx] == 0:
                    melt_point.appendleft((nx, ny))


for i in range(R):
    for j in range(C):
        if visited[i][j] == 0 and maps[i][j] == 1:
            bfs(j, i)


count = 0
while melt_point:
    if find_parent(L_point[0]) == find_parent(L_point[1]):
        break
    next_melt_point = deque()
    while melt_point:
        cx, cy = melt_point.pop()
        maps[cy][cx] = 1
        for i in range(4):
            nx, ny = cx + dx[i], cy + dy[i]
            if 0 <= nx < C and 0 <= ny < R:
                if not visited[ny][nx]:
                    visited[ny][nx] = 1
                    next_melt_point.appendleft((nx, ny))
                if maps[ny][nx] == 1:
                    union_parent(ny * C + nx, cy * C + cx)
    melt_point = next_melt_point
    count += 1
    # print(count)
    # print(parent)
    # pprint.pprint(maps)

print(count)
