"""

조건
    참가자 : M 명
    미로 : N X N
        (r,c) 형태 -> y,x
        좌상단 -> (1,1) -> 입력에서 -1씩 하면 됨.
    미로 상태
        - 빈칸
            참가자가 이동 가능한 칸
        - 벽
            참가자가 이동 불가능한 칸
            1 ~ 9의 내구도
            회전하면 내구도가 1 깎임
            내구도가 0이 되면 빈칸이됨
        - 출구
            참가자가 도달하면 즉시 탈출하는 칸
    참가자의 이동
        - 움직이는 조건
            1. 모두 동시에
            2. 두 위치((x1,y1), (x2,y2))간의 최단 거리는  ∣x1−x2∣+∣y1−y2∣ 로 정의
            3. 상,하,좌,우로 움직이며 벽이 없는 곳으로 이동 가능
            4. 움직인 칸 - 출구 최단거리 < 현재칸 ~ 출구 최단 거리
                움직인 칸 -> nx,ny
                현재 칸 -> cx,cy
                출구 -> tx,ty
                -> abs(tx - nx) + abs(ty - ny) < abs(tx - cx) + abs(ty - cy)
            5. 움직일 수 있는 칸이 2개 이상
                상, 하 움직임 선호
            6. 움직일 수 없는 상황이라면 움직이지 않음.
            7. 한 칸에 2명 이상의 참가자가 있을 수 있음.
    미로의 회전
        1. 한 명 이상의 참가자, 출구를 포함한 가장 작은 정사각형을 잡음
        2. 가장 작은 크기의 정사각형이 2개 이상이라면 좌상단의 r 좌표가 작은 것이 우선,
        -> 좌상단 y 좌표 작은 것
        3. 그것도 같다면 c 좌표가 작은 것이 우선
        -> 좌상단 x 좌표 작은 것
        4. 선택된 정사각형은 시계방향으로 90도 회전, 내구도 -1
        -> (x1,y1)(좌상단),(x2,y1)(우상단),(x1,y2)(좌하단),(x2,y2)(우하단) 범위 내에 있는 모든 x,y에 대해
        -> (x1,y1 ~ x2-1,y1) 사이에 있는 좌표 -> (1,0) 뱡향 이동
            -> 시작 -> x2, 끝 -> x1+1 , 방향 -> (-1,0)
            -> maps[i][y1] = maps[i-1][y1]
        -> (x2,y1 ~ x2,y2-1) 사이에 있는 좌표 -> (0,1) 방향 이동
            -> 시작 -> y2-1, 끝 -> y1+1 , 방향 -> (0,-1)
            -> maps[x2][i] = maps[x2][i-1]
        -> (x1+1,y2 ~ x2,y2) 사이에 있는 좌표 -> (-1,0) 방향 이동
            -> 시작 -> x1+1, 끝 -> x2 , 방향 -> (1,0)
            -> maps[i-1][y2] = maps[i][y2]
        -> (x1,y1+1 ~ x1,y2) 사이에 있는 좌표 -> (0,-1) 방향 이동
            -> 시작 -> y1+1, 끝 -> y2 , 방향 -> (0,1)
            -> maps[x1][i-1] = maps[x1][i]
        반복
            -> 정사각형 크기 -> l 이라고 하면 -> 좌표 차이 -> 한 변의 길이가 2 이번 크기는 1이 라고 생각
            l이 1 이상일때 까지 반복
            조건
                - 모든 꼭지점 좌표에서 -1씩

    - 지도


초기 상태
    - 모든 참가자는 초기에 빈칸에 존재함
    - 출구 또한 빈칸에 존재함

구해야 하는 것
    - 모든 참가자가 탈출하거나, K 초가 지났을때
    모든 참가자들의 이동거리 합, 출구 좌표를 출력(회전한)
주의 사항

입력
    N,M,K
    maps
    M 개의 참가자 좌표

    N -> 미로 크기
    maps:
        maps[i][j] == 0:
            빈칸
        1 <= maps[i][j] <= 9
            벽을 의미

"""

import sys
from pprint import pprint
ip = sys.stdin.readline
total_distance = 0
N,M,K = map(int,ip().split())
maps = [list(map(int,ip().split())) for _ in range(N)]
people = {} # 참가자들 좌표 들어있는 dict
for i in range(M):
    y,x = map(int,ip().split())
    if (x-1,y-1) in people:
        people[(x-1,y-1)].append(i)
    else:
        people[(x-1,y-1)] = [i]
ty,tx = map(int,ip().split()) # 초기 출구
tx -= 1
ty -= 1
if (tx,ty) in people:
    del people[(tx,ty)]

# 순서 -> 상,하,좌,우
# 움직일 수 있으면 움직이고 반복문 종료
dx = [0,0,-1,1]
dy = [-1,1,0,0]
success_people = 0
def cal_diff(a,b):
    return abs(a-b)
def min_distance(x1,y1,x2,y2):
    return cal_diff(x1,x2) + cal_diff(y1,y2)

def move(x,y,next_people):
    global total_distance,success_people
    c_distance = min_distance(x,y,tx,ty)
    cur_list = people[(x,y)] # 해당 좌표에 있는 사람 한번에 이동 시키기
    for i in range(4):
        nx,ny = x + dx[i], y + dy[i]
        if 0 <= nx < N and 0 <= ny < N and maps[ny][nx] == 0:
            n_distance = min_distance(nx,ny,tx,ty)
            if n_distance == 0:  # 출구 도착 이라면
                success_people += len(cur_list) # 사람 수 만큼 성공한 사람 더해줌
                total_distance += len(cur_list) # 사람 수 만큼 이동거리 더해줌
                return
            elif c_distance > n_distance:# 최단 거리가 더 작다면
                total_distance += len(cur_list) # 사람 수 만큼 이동거리 더하기
                if (nx, ny) in next_people:  # 그 좌표에 사람이 없으면 바로
                    next_people[(nx, ny)].extend(cur_list)
                else:
                    next_people[(nx,ny)] = cur_list
                return
    if (x,y) in next_people:
        next_people[(x,y)].extend(cur_list)
    else:
        next_people[(x,y)] = cur_list

def move_all():
    global people
    _list = list(people.keys())
    #print(_list)
    next_people = {}
    for x,y in _list:
        move(x,y,next_people)
    people = next_people




def check_there_is_exit_and_person(x1,y1,x2,y2):
    for x,y in people:
        if y1 <= y <= y2 and y1 <= ty <= y2 and x1 <= x <= x2 and x1 <= tx <= x2:
            return True
    return False

def get_minimum_rectangle():
    """
    크기
    크기 -> 2 
        0,0 ~ N-2, N-2
    크기 -> 3 
        0,0 ~ N-3, N-3
    크기 -> l
        0,0 ~ N-l, N-l
    ...
    
    크기 -> N
        0,0 ~ 0,0
    """
    for l in range(1,N):
        for y1 in range(N-l):
            for x1 in range(N-l):
                x2,y2 = x1 + l, y1 + l
                if check_there_is_exit_and_person(x1,y1,x2,y2):
                    return x1,y1,x2,y2,l
turn_exit_cnt = 0
def turn_people_point(sx,sy,ex,ey):
    if (sx, sy) in people:
        temp = people[(sx, sy)]
        people[(ex, ey)] = temp
        del people[(sx, sy)]

def turn_exit_point(sx,sy,ex,ey):
    global tx,ty,turn_exit_cnt
    if sx == tx and sy == ty:
        tx,ty = ex,ey
        turn_exit_cnt = 1

# 꼭지점만 90도 회전
def turn_point(x1,y1,x2,y2):
    # 지도 회전
    temp = maps[y1][x1]
    maps[y1][x1] = max(0,maps[y2][x1]-1)
    maps[y2][x1] = max(0,maps[y2][x2]-1)
    maps[y2][x2] = max(0,maps[y1][x2]-1)
    maps[y1][x2] = max(0,temp-1)

    # 사람 회전
    temp = None
    if (x1,y1) in people:
        temp = people[(x1,y1)]
        del people[(x1,y1)]

    turn_people_point(x1,y2,x1,y1)
    turn_people_point(x2,y2,x1,y2)
    turn_people_point(x2,y1,x2,y2)

    if temp is not None:
        people[(x2,y1)] = temp

    # 출입문 회전
    if turn_exit_cnt == 0:
        turn_exit_point(x1,y2,x1,y1)
    if turn_exit_cnt == 0:
        turn_exit_point(x2, y2, x1, y2)
    if turn_exit_cnt == 0:
        turn_exit_point(x2, y1, x2, y2)
    if turn_exit_cnt == 0:
        turn_exit_point(x1, y1, x2, y1)

# 면 부분 회전
def turn_line(x1,y1,x2,y2,l,exit_flag):
    """
    (x1,y1,x2,y2)
    0,0 -> 2,0
    0,1 -> 1,0
    0,2 -> 0,0

    # 왼쪽->상단 이동
    for i in range(l)
        (x1,y1+i) -> (x2-i,y1)
    """
    temp = maps[y1][x1+1:x2]
    temp_people = {}

    for i in range(1,l):
        #print(x1,y1+i,x2-i,y1)
        maps[y1][x2-i] = max(0,maps[y1+i][x1]-1)
        if turn_exit_cnt == 0:
            turn_exit_point(x1,y1+i,x2-i,y1)
        if (x2-i,y1) in people:
            temp_people[(x2-i,y1)] = people[(x2-i,y1)]
            del people[(x2-i,y1)]
        turn_people_point(x1, y1 + i, x2 - i, y1)
    """
     0,2 -> 0,0
    1,2 -> 0,1
    2,2 -> 0,2
    # 밑 -> 왼쪽 이동
    for i in range(l)
        (x1+i,y2) -> (x1,i)
    """
    for i in range(1,l):
        #print(x1+i,y2,x1,y1+i)
        maps[y1+i][x1] = max(0,maps[y2][x1+i]-1)
        if turn_exit_cnt == 0:
            turn_exit_point(x1+i,y2,x1,y1+i)
        turn_people_point(x1+i,y2,x1,y1+i)
    """
    2,2 -> 0,2
    2,1 -> 1,2
    2,0 -> 2,2
    # 오른 -> 밑쪽 이동
    for i in range(l)
        (x2,y2-i) -> (x1+i,y2)

    """
    for i in range(1,l):
        #print(x2,y2-i,x1+i,y2)
        maps[y2][x1+i] = max(0,maps[y2-i][x2]-1)
        if turn_exit_cnt == 0:
            turn_exit_point(x2,y2-i,x1+i,y2)
        turn_people_point(x2,y2-i,x1+i,y2)
    """
    2,0 -> 2,2
    1,0 -> 2,1
    0,0 -> 2,0

    # 위 -> 오른쪽 이동 
    for i in range(l):
        (x2-i,y1) -> (x2,y2-i)
    """
    for i in range(1,l):
        #print(x2-i,y1,x2,y2-i)
        maps[y2-i][x2] = max(0,temp[-i]-1)
        if turn_exit_cnt == 0:
            turn_exit_point(x2-i,y1,x2,y2-i)
        if (x2-i,y1) in temp_people:
            people[(x2,y2-i)] = temp_people[(x2-i,y1)]
def rotate():
    global turn_exit_cnt
    turn_exit_cnt = 0
    # pprint(maps)
    # pprint(people)
    # #print("출구")
    # print(tx,ty)
    x1,y1,x2,y2,l = get_minimum_rectangle()

    while l >= 1:
        #print(x1,y1,x2,y2,l)
        # 꼭지점 회전
        exit_flag = turn_point(x1,y1,x2,y2)
        # 면 부분 회전
        if l >= 2:
            turn_line(x1, y1, x2, y2, l,exit_flag)
        x1 += 1
        y1 += 1
        x2 -= 1
        y2 -= 1
        l -= 2

    # 맨 안쪽 1x1 사각형 안에 있는 벽 내구도 줄이기
    if l == 0:
        maps[y1][x1] = max(0,maps[y1][x1]-1)


def solve():
    t = 0
    while t < K:
        #pprint(maps)
        #pprint(people)
        # print("출구")
        #print(tx, ty)
        move_all()
        if success_people == M:
            break
        rotate()
        t += 1

    print(total_distance)
    print(ty+1,tx+1)


solve()
"""
5 1 100
0 0 0 0 0 
0 0 0 0 9
0 0 0 9 0
0 0 0 0 9
0 0 0 0 0
3 5
1 5
"""
