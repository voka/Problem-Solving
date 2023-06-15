"""
크기가 3×3인 배열 A가 있다. 배열의 인덱스는 1부터 시작한다. 1초가 지날때마다 배열에 연산이 적용된다.

R 연산: 배열 A의 모든 행에 대해서 정렬을 수행한다. 행의 개수 ≥ 열의 개수인 경우에 적용된다.
C 연산: 배열 A의 모든 열에 대해서 정렬을 수행한다. 행의 개수 < 열의 개수인 경우에 적용된다.

행 또는 열의 크기가 100을 넘어가는 경우에는 처음 100개를 제외한 나머지는 버린다.

정렬 기준 
1. 등장한 횟수 (큰 순) -> 작은 것이 앞에 
2. 수의 크기 (큰 순) -> 작은 것이 앞에

(수, 등장횟수)

연산을 수행하지 않는 열이나 행은 0으로 채워야함 

정렬시에 0은 취급 하지 않음 .

연산 100번 수행해도 A[r][c] = k 가 되지 않으면 -1 출력
"""

import sys
ip = sys.stdin.readline
r, c, k = map(int, ip().split())
maps = [[0]*(101) for _ in range(101)]
cur_r = cur_c = 3
for i in range(cur_r):
    _list = list(map(int, ip().split()))
    for j in range(cur_c):
        maps[i][j] = _list[j]


def print_map():
    for i in range(cur_r):
        for j in range(cur_c):
            print(maps[i][j], end=" ")
        print()


def R_function(cur_r, cur_c):
    cc = cur_c
    nc = -1
    for i in range(cur_r):
        my_dict = {}
        for j in range(cur_c):
            cur = maps[i][j]
            if cur == 0:
                continue
            if cur not in my_dict:
                my_dict[cur] = 1
            else:
                my_dict[cur] += 1
            maps[i][j] = 0
        _list = list(my_dict.items())
        _list.sort(key=lambda x: (x[1], x[0]))
        cc = min(100, len(_list)*2)
        # print(_list)
        nc = max(cc, nc)
        for j in range(0, cc, 2):
            # print(j, j+1, (j+1)//2)
            maps[i][j], maps[i][j+1] = _list[j//2]
    return nc


def C_function(cur_r, cur_c):
    cr = cur_r
    nr = -1
    for j in range(cur_c):
        my_dict = {}
        for i in range(cur_r):
            cur = maps[i][j]
            if cur == 0:
                continue
            if cur not in my_dict:
                my_dict[cur] = 1
            else:
                my_dict[cur] += 1
            maps[i][j] = 0
        _list = list(my_dict.items())
        _list.sort(key=lambda x: (x[1], x[0]))
        cr = min(100, len(_list)*2)
        nr = max(cr, nr)
        for i in range(0, cr, 2):
            maps[i][j], maps[i+1][j] = _list[i//2]
    return nr


T = 101
while T:
    # print(T, cur_r, cur_c)
    # print_map()
    if maps[r-1][c-1] == k:
        break
    T -= 1
    if (cur_c > cur_r):
        cur_r = C_function(cur_r, cur_c)
    else:
        cur_c = R_function(cur_r, cur_c)
if T > 0:
    print(100 - T + 1)
else:
    print(-1)
