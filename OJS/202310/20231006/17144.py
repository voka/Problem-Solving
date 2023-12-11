from pprint import pprint
import sys
ip = sys.stdin.readline

"""
미세먼지 제거

map : RxC 

r,c -> y,x

공기청정기 
크기 -> 2행 차지

미세먼지 
    r,c 에 있는 미세 먼지 양 maps[r][c]


초기 상태 
    공기청정기 -> 1번열


1초간 일어나는 작업들 
    확산 
    1. 미세먼지 확산은 미세먼지가 있는 모든 칸에서 동시에 일어남
    2. 인접한 방향에 공기청정기가 있거나 칸이 없으면 그 방향으로는 확산 일어나지 않음 
    3. 확산되는 양은 maps[r][c]//5 이다. 
    4. 확산되지 않고 남아있는 양은 maps[r][c] - (maps[r][c] // 5)* (확산된 방향)
    공기청정기 작동 
    1. 위쪽 공기청정기의 바람은 반시계 방향으로 순환 
    2. 아래쪽 공기청정기의 바람은 시계방향로 순환
    3. 바람이 불면 미세먼지가 바람의 방향대로 모두 한칸씩 이동
    4. 이 바람은 미세먼지가 없고, 공기 청정기로 들어간 미세먼지는 모두 사라짐

"""

R, C, T = map(int, ip().split())
maps = []

sx, sy = -1, -1
ex, ey = -1, -1

for i in range(R):
    temp = list(map(int, ip().split()))
    for j in range(C):
        if temp[j] == -1:
            if sx == -1 and sy == -1:
                sx, sy = j, i
            else:
                ex, ey = j, i
    maps.append(temp)


dx = [1, 0, -1, 0]
dy = [0, -1, 0, 1]


def splash():
    plus_maps = [[0]*C for _ in range(R)]
    for i in range(R):
        for j in range(C):
            if maps[i][j] != -1 and maps[i][j] >= 5:
                # count_dir = 0  # 퍼지는 방향
                cur = maps[i][j] // 5  # 퍼지는 양
                for k in range(4):
                    nx, ny = j + dx[k], i + dy[k]
                    if 0 <= nx < C and 0 <= ny < R and maps[ny][nx] != -1:
                        plus_maps[ny][nx] += cur
                        maps[i][j] -= cur
    # 확산된 양 더하기
    for i in range(R):
        for j in range(C):
            maps[i][j] += plus_maps[i][j]


udx = [0, 1, 0, -1]
udy = [-1, 0, 1, 0]

ddx = [0, 1, 0, -1]
ddy = [1, 0, -1, 0]


def up_cleaning():
    cleaning(sx, sy, 0, sy, udx, udy)


def down_cleaning():
    cleaning(ex, ey, ey, R-1, ddx, ddy)


# 공기 청정기 시작 지점 x,y
# 공기 청정기 y 범위 sy,ey
# 작동 방향
def cleaning(x, y, sy, ey, dir_x, dir_y):
    i = 0
    while i < 4:
        nx, ny = x + dir_x[i], y+dir_y[i]
        # print(nx, ny)
        if 0 <= nx < C and sy <= ny <= ey:  # 범위 안이라면
            if maps[y][x] == -1:
                maps[ny][nx] = 0
            elif maps[ny][nx] == -1:
                maps[y][x] = 0
            else:
                maps[y][x] = maps[ny][nx]
            x, y = nx, ny
        else:  # 범위 밖이라면 방향 전환
            i += 1


def answer():
    cnt = 0
    for i in range(R):
        for j in range(C):
            if maps[i][j] != -1:
                cnt += maps[i][j]
    return cnt


def solve():
    for i in range(T):
        # pprint(maps)
        splash()
        # pprint(maps)
        up_cleaning()
        # pprint(maps)
        down_cleaning()
        # pprint(maps)
    print(answer())


solve()
