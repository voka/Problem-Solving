import sys
import copy
from itertools import product
ip = sys.stdin.readline
N = int(ip())
origin_maps = [list(map(int, ip().split())) for _ in range(N)]
# right, left, up, down
# start, end, iter
com_for = [(N-1, -1, -1), (0, N, 1)]
maps = [[]]


def col_compress(j, idx):
    start, end, it = com_for[idx]
    last = start
    for i in range(start, end, it):
        if maps[i][j] != 0:
            if i != last:
                maps[last][j] = maps[i][j]
                maps[i][j] = 0
            last = last + it


def row_compress(i, idx):
    start, end, it = com_for[idx]
    last = start
    for j in range(start, end, it):
        if maps[i][j] != 0:
            if j != last:
                maps[i][last] = maps[i][j]
                maps[i][j] = 0
            last = last + it


def row_merge(i, idx):
    start, end, it = com_for[idx]
    for j in range(start + it, end, it):
        if maps[i][j] == maps[i][j-it]:
            maps[i][j-it] *= 2
            maps[i][j] = 0


def col_merge(j, idx):
    start, end, it = com_for[idx]
    for i in range(start + it, end, it):
        if maps[i][j] == maps[i-it][j]:
            maps[i-it][j] *= 2
            maps[i][j] = 0


def row_cal(idx):
    for i in range(N):
        row_compress(i, idx)
        row_merge(i, idx)
        row_compress(i, idx)


def col_cal(idx):
    for j in range(N):
        col_compress(j, idx)
        col_merge(j, idx)
        col_compress(j, idx)


def get_max_block():
    _max = -1
    for i in range(N):
        _max = max(_max, max(maps[i]))
    return _max


answer = 0
to_do_list = product([0, 1, 2, 3], repeat=5)
for _list in to_do_list:
    maps = copy.deepcopy(origin_maps)
    for do in _list:
        if do < 2:
            row_cal(do % 2)
        else:
            col_cal(do % 2)
    answer = max(answer, get_max_block())
print(answer)
