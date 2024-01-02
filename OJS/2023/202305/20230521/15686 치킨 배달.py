import sys
from itertools import combinations
ip = sys.stdin.readline


def distance(r1, c1, r2, c2):
    return abs(r1-r2) + abs(c1 - c2)
# 0 -> 빈칸
# 1 -> 집
# 2 -> 치킨집
# 치킨 거리 -> 집과 가장 가까운 치킨집 사이의 거리
# 도시의 치킨 거리 -> 모든 치킨 거리의 합


house_dict = []
chicken_dict = {}
N, M = map(int, ip().split())
maps = [list(map(int, ip().split())) for _ in range(N)]
# 2500 * 100 -> 25000개
cidx = 0
for i in range(N):
    for j in range(N):
        if maps[i][j] == 2:
            chicken_dict[cidx] = (j, i)
            cidx += 1
        elif maps[i][j] == 1:
            house_dict.append((j, i))
hn = len(house_dict)


def get_city_chicken_distance(delted_idxs):
    _list = [1e9]*hn
    for hidx in range(hn):
        (j, i) = house_dict[hidx]
        for n in chicken_dict:
            if n in delted_idxs:
                continue
            (j2, i2) = chicken_dict[n]
            cur_dix = distance(j, i, j2, i2)
            if _list[hidx] > cur_dix:
                _list[hidx] = cur_dix
    # print(_list)
    return sum(_list)


answer = 1e9
if M >= len(chicken_dict):
    answer = get_city_chicken_distance([])
else:
    for i_list in combinations([i for i in range(cidx)], len(chicken_dict) - M):
        answer = min(answer, get_city_chicken_distance(i_list))
print(answer)
