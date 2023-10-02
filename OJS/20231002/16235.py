"""
입력: N, M, K
maps : N X N
    - 초기 양분은 모든 칸에 5씩
나무의 개수 : M
조건 : K 년

봄
    - 나무가 자신의 나이만큼 양분을 먹고 나이가 증가하는 시기
    - 나이가 어린 나무부터 양분을 먹는다.
    - 양분이 부족해 나이만큼 양분을 먹을 수 없다면 즉시 죽는다.

여름
    - 봄에 죽은 나무가 양분으로 변하게 된다.
    - 죽은 나무의 나이 // 2 값이 해당칸의 양분으로 변한다.

가을
    - 번식하는 나무의 나이는 5의 배수다.
    - 인접한 8개의 칸에 나이가 1인 나무가 생긴다.
    - 땅의 범위를 벗어나는 곳에는 생기지 않는다.

겨울
    - S2D2(기계)가 땅에 양분을 추가한다.
    - c,r에 양분을 추가한다.

todo: K년이 지난 후 땅에 살아있는 나무 구하기

TIP: 이 문제에서 나이가 어린 나무들 부터 양분을 먹는다. 이 부분을 구현할때 정렬을 하거나 heqpq를 쓰지 않아도 된다. 
왜냐하면 
"""
from pprint import pprint
import sys
from collections import deque
ip = sys.stdin.readline

N, M, K = map(int, ip().split())

# 겨울에 더해지는 양분 양
plus_maps = [list(map(int, ip().split())) for _ in range(N)]

# 나무 맵
tree_maps = [[deque() for _ in range(N)] for _ in range(N)]
# pprint(tree_maps)

# 초기 양분들
maps = [[5]*N for _ in range(N)]

for i in range(M):
    y, x, z = map(int, ip().split())
    y -= 1
    x -= 1
    tree_maps[y][x].append(z)
# 나무
# tree_dict = {}

# die_dict = {}
# def spring():
#     for x, y in tree_dict:
#         num = len(tree_dict[(x, y)])
#         temp = []
#         for i in range(num):
#             cur = heapq.heappop(tree_dict[(x, y)])
#             if maps[y][x] < cur:  # 나이보다 작으면
#                 if (x, y) in die_dict:
#                     die_dict[(x, y)].append(cur)
#                 else:
#                     die_dict[(x, y)] = [cur]  # 죽은 나무에 저장
#             else:
#                 maps[y][x] -= cur
#                 heapq.heappush(temp, cur+1)
#         tree_dict[(x, y)] = temp


# def summer():
#     for x, y in die_dict:
#         for tape in die_dict[(x, y)]:
#             maps[y][x] += (tape // 2)

# def spring_and_summer():
#     for x, y in tree_dict:
#         num = len(tree_dict[(x, y)])
#         die_tree = 0
#         temp = []
#         for i in range(num):
#             cur = heapq.heappop(tree_dict[(x, y)])
#             if maps[y][x] < cur:  # 나이보다 작으면
#                 die_tree += (cur // 2)
#             else:
#                 maps[y][x] -= cur
#                 heapq.heappush(temp, cur+1)
#         tree_dict[(x, y)] = temp
#         maps[y][x] += die_tree


dx = [1, 1, -1, -1, 0, 1, -1, 0]
dy = [1, -1, 1, -1, 1, 0, 0, -1]


def splash(x, y):
    for i in range(8):
        nx, ny = x + dx[i], y + dy[i]
        if 0 <= nx < N and 0 <= ny < N:
            tree_maps[ny][nx].appendleft(1)
            # if (nx, ny) in tree_dict:
            #     tree_dict[(nx, ny)].appendleft(1)
            # else:
            #     tree_dict[(nx, ny)] = deque([1])


# def spring_and_summer_and_fall_winter():
#     splash_list = []
#     for x, y in tree_dict:
#         num = len(tree_dict[(x, y)])
#         die_tree = 0
#         for i in range(num):
#             cur = tree_dict[(x, y)].popleft()
#             if maps[y][x] < cur:  # 나이보다 작으면
#                 die_tree += (cur // 2)
#             else:
#                 maps[y][x] -= cur
#                 if (cur + 1) % 5 == 0:
#                     splash_list.append((x, y))
#             tree_dict[(x, y)].append(cur+1)
#         maps[y][x] += die_tree
#     for x, y in splash_list:
#         splash(x, y)

# def fall():
#     splash_list = []
#     for x, y in tree_dict:
#         num = len(tree_dict[(x, y)])
#         for _ in range(num):
#             cur = heapq.heappop(tree_dict[(x, y)])
#             if cur % 5 == 0:
#                 splash_list.append((x, y))
#             heapq.heappush(tree_dict[(x, y)], cur)
#     for x, y in splash_list:
#         splash(x, y)
# spring summer, fall, winter
def ssfw():
    splash_list = []
    for i in range(N):
        for j in range(N):
            num = len(tree_maps[i][j])
            die_tree = 0
            for _ in range(num):
                cur = tree_maps[i][j].popleft()
                if maps[i][j] < cur:  # 나이보다 작으면
                    die_tree += (cur // 2)
                else:
                    maps[i][j] -= cur
                    if (cur + 1) % 5 == 0:
                        splash_list.append((j, i))
                    tree_maps[i][j].append(cur+1)
            maps[i][j] += die_tree
            maps[i][j] += plus_maps[i][j]
    for x, y in splash_list:
        splash(x, y)


def count_tree():
    c = 0
    for i in range(N):
        for j in range(N):
            c += len(tree_maps[i][j])
    return c


for i in range(K):
    # print("start : " + str(i+1))
    # spring()
    # print("end spring")
    # pprint(tree_maps)
    # pprint(maps)
    # summer()
    # print("end summer")
    # pprint(tree_dict)
    # pprint(maps)
    # spring_and_summer()
    # fall()
    # print("end fall")
    # pprint(tree_dict)
    # pprint(maps)
    # spring_and_summer_and_fall()
    # winter()
    # print("end winter")
    # pprint(tree_dict)
    # pprint(maps)
    ssfw()

print(count_tree())
