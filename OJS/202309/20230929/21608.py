import sys
from pprint import pprint
ip = sys.stdin.readline
"""
Map : N X N
학생 수 : N ^ 2
(r,c) -> y,x
왼쪽 윗칸 -> 1,1
오른쪽 아래칸 N X N

abs(r1 - r2) + abs(c1 - c2) = 1 이면 "인접" 하다고 표현

a 학생의 자리를 정하는 우선순위
1.  a 학생이 좋아하는 학생이 가장 많이 인접한 칸으로 자리를 정한다.
2. 1번을 만족하는 칸이 여러개라면 인접한 칸 중에서 비어있느 칸이 가장 많은 칸
3. 2번을 만족하는 칸이 여러개라면 행의번호가 가장 작은 칸
4. 3번까지 만족한다면 열의 번호가 가장 적은 칸으로 자리를 정한다.

정답 -> 만족도의 총합
- 자리배치가 모두 끝난 후에 알 수 있음.
- 주번의 좋아하는 학생수에 따라 만족도가 다름
0 -> 0
1 -> 1
2 -> 10
3 -> 100
4 -> 1000
"""

N = int(ip())
maps = [[0]*N for _ in range(N)]
position = {}  # 학생 번호 : 주번 위치 리스트
near_position = {}  # 학생 번호 : 앉은 위치
satisfaction = {0: 0, 1: 1, 2: 10, 3: 100, 4: 1000}  # 만족도 값
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]
students = {}
for i in range(N*N):
    _list = list(map(int, ip().split()))
    students[_list[0]] = _list[1:]

# 앉을때 position에 위치 정보 저장


def sit(num, x, y):
    position[num] = [x, y]
    maps[y][x] = num
    near_position[num] = []
    for i in range(4):
        nx, ny = x + dx[i], y + dy[i]
        if 0 <= nx < N and 0 <= ny < N:
            near_position[num].append([nx, ny])


def near_empty_count(x, y):
    c = 0
    for i in range(4):
        nx, ny = x + dx[i], y + dy[i]
        if 0 <= nx < N and 0 <= ny < N and maps[ny][nx] == 0:
            c += 1
    return c


def find_most_empty_point():
    max_empty = -1
    max_empty_position = [-1, -1]
    for i in range(N):
        for j in range(N):  # 행의 번호가 제일 적도록
            if maps[i][j] == 0:
                empty_count = near_empty_count(j, i)
                if empty_count == 4:
                    return [j, i]
                if empty_count > max_empty:
                    max_empty = empty_count
                    max_empty_position = [j, i]
    return max_empty_position


def chooes(num):
    favoraite = students[num]
    points = {}

    for si in favoraite:
        if si in near_position:  # 가장 좋아하는 친구가 앉았다면
            for [x, y] in near_position[si]:  # 주번 자리 중
                if maps[y][x] == 0:  # 빈 자리라면
                    if (x, y) in points:  # points dict에 표시한다..
                        points[(x, y)][0] += 1
                    else:
                        # [좋아하는 학생수 , 빈자리 수] 저장
                        points[(x, y)] = [1, near_empty_count(x, y)]

    if len(points) == 0:
        [x, y] = find_most_empty_point()
        sit(num, x, y)
    else:
        # print(points.items())
        # sort 조건 우선순위
        # 1. 좋아하는 학생 많은 순
        # 2. 비어있는 자리 많은 순
        # 3. 행 번호 적은 순
        # 4. 열 번호 적은 순
        result = sorted(points.items(), key=lambda x: (
            x[1][0], x[1][1], -x[0][1], -x[0][0]), reverse=True)
        # print(result)
        sit(num, result[0][0][0], result[0][0][1])


def get_satisfaction(num):
    count = 0
    favoraite = students[num]
    [x, y] = position[num]
    for i in range(4):
        nx, ny = x + dx[i], y + dy[i]
        if 0 <= nx < N and 0 <= ny < N:
            if maps[ny][nx] in favoraite:
                count += 1
    return satisfaction[count]


for key in students:
    # print("start : " + str(key))
    chooes(key)
    # pprint(maps)

answer = 0
for key in students:
    answer += get_satisfaction(key)
print(answer)
