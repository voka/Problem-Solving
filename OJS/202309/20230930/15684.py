import sys
from itertools import combinations
ip = sys.stdin.readline
"""
목표 : i번째 사다리의 결과가 i번째에 도착하도록 최소 개수의 가로선 결정하기 

방법 1
1개,2개,3개 넣는 모든 경우의 수를 구해서 다리를 놓았을 경우 
i -> i 결과로 나오는지 확인하기

"""
N, M, H = map(int, ip().split())
# 가로선 정보 높이 : a (가로 위치 시작점))
horizontal_lines = [[0]*(N+1) for _ in range(H+1)]

for i in range(M):
    a, b = map(int, ip().split())
    horizontal_lines[a][b] = 1

todo_dict = {}
for h in range(1, H+1):
    todo_dict[h] = []
    for i in range(1, N):
        if horizontal_lines[h][i] == 0:
            todo_dict[h].append(i)
# print(todo_dict)

todo_list = []
for key in todo_dict:
    for i in todo_dict[key]:
        todo_list.append((key, i))


def outcome(num):
    # print(num)
    for h in range(1, H+1):
        if 2 <= num <= N and horizontal_lines[h][num-1] != 0:
            num = num - 1
        elif 1 <= num <= N-1 and horizontal_lines[h][num] != 0:
            num = num + 1
    # print(num)
    return num


def is_correct():
    for i in range(1, N+1):
        if i != outcome(i):
            return False
    return True


def solve():
    result = is_correct()  # 가로줄을 하나도 추가하지 않았을 경우
    if result:
        return 0
    for todo in todo_list:
        a, b = todo
        horizontal_lines[a][b] = 1
        result = is_correct()  # 가로줄을 하나만 추가한 경우
        if result:
            return 1
        horizontal_lines[a][b] = 0
    if len(todo_list) >= 2:  # 가로줄을 두개 추가한 경우
        todo_list2 = combinations(todo_list, r=2)
        for todo in todo_list2:
            _list = todo
            for a, b in _list:
                horizontal_lines[a][b] = 1
            # print(horizontal_lines)
            result = is_correct()
            if result:
                return 2
            for a, b in _list:
                horizontal_lines[a][b] = 0

    if len(todo_list) >= 3:  # 가로줄을 세개 추가한 경우
        todo_list3 = combinations(todo_list, r=3)
        for todo in todo_list3:
            _list = todo
            for a, b in _list:
                horizontal_lines[a][b] = 1
            result = is_correct()
            if result:
                return 3
            for a, b in _list:
                horizontal_lines[a][b] = 0
    return -1


print(solve())
