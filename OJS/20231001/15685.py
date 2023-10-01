from pprint import pprint
import sys
ip = sys.stdin.readline
"""
maps = 100 X 100

방향 
    0: x좌표가 증가하는 방향 (→)
    1: y좌표가 감소하는 방향 (↑)
    2: x좌표가 감소하는 방향 (←)
    3: y좌표가 증가하는 방향 (↓)

(0,0) 기준 90도 회전 ***

사분면 
    1: x > 0 and y > 0
        ->  x -> -y , y -> x
    2: x < 0 and y > 0
        -> x -> -y, y -> x 
    3: x < 0 and y < 0
        -> x -> -y, y -> x
    4: x > 0 and y < 0   
        -> x -> -y, y -> x 
        

x == 0 and y > 0
-> x,y 교환 후 y 에 - 붙이기

x == 0 and y < 0 
-> x,y 교환 후 y 에 - 붙이기

x > 0 and y == 0 
-> x,y 교환 후

x < 0 and y == 0
-> x,y 교환 후

주의 
- 입력으로 주어지는 드래곤 커브는 좌표평면 바깥으로 넘어가지 않는다. 
    (코드만 잘 짜면 좌표 범위 검사를 할 필요가 없다.)
todo: 좌표평면에서 4개의 꼭지점 모두가 드래곤 커브인 정사각형 개수 구하기
"""

maps = [[0]*101 for _ in range(101)]

# 0,0 기준 90도 돌리기


def turn_90(x, y):
    if y == 0:
        return y, x
    if y == 0:
        return y, -x
    return -y, x

# 0,0을 기준으로 만들고 90도 돌리고 다시 값을 더해주기  -> 평행이동


def point_turn_90(dx, dy, x, y):
    nx, ny = turn_90(x-dx, y-dy)
    nx, ny = nx+dx, ny+dy
    return nx, ny


# 방향 0(x 증기)에 대해 generation 10 까지 모든 방향에 대해 만들어주기
gen = []
gen.append({0: [(0, 0), (1, 0)]})

for i in range(11):
    plus_point = []
    last_x, last_y = gen[i][0][-1]
    for j in range(len(gen[i][0])-2, -1, -1):
        x, y = gen[i][0][j]
        plus_point.append((point_turn_90(last_x, last_y, x, y)))
    gen.append({0: gen[i][0] + plus_point})

# 방향 1,2,3에 대한 generation을 만들어주는 함수


def turn_90_all_point(fm, to):
    for i in range(11):
        turn_points = []
        for x, y in gen[i][fm]:
            nx, ny = turn_90(x, y)
            turn_points.append((nx, ny))
        gen[i][to] = turn_points


turn_90_all_point(0, 3)
turn_90_all_point(3, 2)
turn_90_all_point(2, 1)

# gen에 있는 좌표들을 x,y 만큼 평행 이동 해서 maps에 표시하기


def draw_point(x, y, d, g):
    cur_points = gen[g][d]
    for cx, cy in cur_points:
        nx, ny = cx + x, cy + y
        maps[ny][nx] = 1

# 0,0 ~ 99,99 를 정사각형의 왼쪽 위 기준으로 잡고 모든 꼭지점이 1이라면 정사각형 개수 더해주기


def count_square():
    c = 0
    for i in range(100):
        for j in range(100):
            if maps[i][j] == 1 and maps[i][j+1] == 1 and maps[i+1][j] == 1 and maps[i+1][j+1] == 1:
                c += 1
    return c


N = int(ip())
for _ in range(N):
    x, y, d, g = map(int, ip().split())
    draw_point(x, y, d, g)
print(count_square())
