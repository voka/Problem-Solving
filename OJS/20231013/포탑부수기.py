"""
maps -> NXM

100 * 1000 -> 100000 * 100 -> 10000000 -> 가능
조건
    각 포탑은 공격력이 있음
        - 줄어들거나 늘어날 수 있음 .
        - 0 이하가 되면 부서짐
        - 맨 처음에도 부서진 포탑이 존재할 수 있음
        - 맨 처음에는 모든 포탑이 공격한 경험이 있음 .
    총 K 번 반복

동작
    def choice_attacker
    1. 공격자 선정
        - 가장 약한 포탑 포탑이 공격자로 선정
           - 가장 약한 포탑이란
            1. 공격력 낮은 포탑
            2. 가장 최근에 공격한 포탑
            3. 행 + 열 합이 가장 큰 포탑
            4. 열 값이 가장 큰 포탑
        - 선정되면 N+M 만큼 공격력 증가

    def attack
    2. 공격자의 공격
        프로그램
        def choice_target
        1. 공격 대상 선정

        def raiser_attack()
        2. 공격자 -> 공격 대상 최단 경로 찾기

        def find_shortest_path
        3. 최단 경로 있으면 찾아서 공격대상, 경로에 있는 모든 포탑의 공격력 낮추기

        def bazooka_attack()
        4. 안되면 포탄 공격
        설명
        - 공격자로 선택된 포탑이 가장 강한 포탑을 공격
        - 가장 강한 포탑
            1. 공격력이 가장 높은 포탑
            2. 공격한지 가장 오래된 포탑
            3. 행 + 열 값이 가장 작은 포탑
            4. 열 값이 가장 작은 포탑
        1. 레이저 공격 먼저 시도 (이동 공격 -> 움직인 경로 상에 있는 모든 포탑에게 피해를 줌)
            - 조건
                - 상,하,좌,우 이동 가능
                - 부서진 포탑이 있는 곳은 지날 수 없음
                - 가장자리에서 막힌 방향으로 진행할 경우 반대편으로 나옴
                    -> 까다로운 조건
            - 공격자의 위치에서 공격 대상 최단 경로로 공격
                - 최단 경로가 2개 이상
                - 우/하/좌/상의 우선순위로 먼저 움직인 경로가 선택됨
                - 피해를 입은 포탑은 해당 수치만큼 공격력이 줄어듬.
                - 공격대상을 제외한 레이저 경로에 있는 포탑도 공격자의 절반만큼 공격을 받음
                    - 공격자의 공격력 // 2 만큼 공격력 수치가 감소함.
            - 최단 경로가 존재하지 않으면 포탄 공격
        2. 안되면 포탄 공격 시도
            - 공격 대상은 공격자 공격력만큼 피해를 받음
            - 주위 8개 방향에 있는 포탑도 피해를 받음
                - 가장자리에 포탄이 떨어지면 반대편 쪽에 피해를 줌
            -
    def update_maps
    3. 포탑의 공격력 <= 0 이면 부서진 것으로 처리
    def repair
    4. 포탑 정비
        - 공격과 무관했던 포탑들의 공격력 1 증가
            - 피해를 주지도 않고, 받지도 않은 경우

"""
import sys
from pprint import pprint
ip = sys.stdin.readline
N, M, K = map(int, ip().split())
maps = []
active = {} # 활성화 된 포탑들

for i in range(N):
    temp = list(map(int, ip().split()))
    for j in range(M):
        if temp[j] > 0:
            active[(j,i)] = [temp[j],0] # 공격력, 최근 공격 여부 (처음은 0으로 시작, i 번째에는 i번으로 표시를 남겨서 큰 숫자가 우선순위를 가지도록)
    maps.append(temp)

def change_point(x,y):
    if x < 0:
        x = x + M
    if x >= M:
        x = x - M
    if y < 0:
        y = y + N
    if y >= N:
        y = y - N
    return x, y

# 바주카 공격은 8방향
bdx = [1,1,1,0,-1,-1,-1,0]
bdy = [1,0,-1,1,1,0,-1,-1]
def bazooka_attack(x,y,tx,ty):
    road = [[x,y],[tx,ty]]
    damage = maps[y][x]
    half = damage // 2
    for i in range(8):
        nx, ny = tx + bdx[i], ty + bdy[i]
        nx, ny = change_point(nx,ny)
        if nx == x and ny == y:
            continue
        if (nx,ny) in active:
            maps[ny][nx] -= half
            active[(nx,ny)][0] -= half
            road.append([nx,ny])
    maps[ty][tx] -= damage
    active[(tx,ty)][0] -= damage
    return road

# 공격 대상 까지 움직일 수 있는지 판단
# 우선순위는 오른쪽, 아래, 왼쪽, 위쪽
dx = [1,0,-1,0]
dy = [0,1,0,-1]

from collections import deque

def bfs(x,y,tx,ty):
    myque = deque()
    visited = [[False]*M for _ in range(N)]
    myque.append((x,y,[[x,y]]))
    while myque:
        cx,cy,road = myque.popleft()
        for i in range(4):
            #nx, ny = cx + dx[i], cy + dy[i]
            # 위치 조정 (반대편에 건너갈 수 있도록)
            #nx, ny = change_point(nx, ny)
            nx = (cx + dx[i]) % M
            ny = (cy + dy[i]) % N
            if maps[ny][nx] > 0 and not visited[ny][nx]:  # 부서지지 않은 포탑, 방문한 적이 없는 경우
                visited[ny][nx] = True
                if nx == tx and ny == ty:
                    return road + [[nx,ny]]
                else:
                    myque.append((nx,ny,road + [[nx,ny]]))
    return []
def find_shortest_path(x,y,tx,ty):

    return bfs(x,y,tx,ty)



def raiser_attack(ax,ay,tx,ty,road):
    damage = maps[ay][ax]
    half = damage//2
    # print('road start')
    # pprint(maps)
    for [x,y] in road:
        if ax == x and ay == y: #공격자인 경우 통과
            continue
        if tx == x and ty == y: #공격 대상인 경우 공격자의 전체 데미지
            maps[y][x] -= damage
            active[(x,y)][0] -= damage
        else:
            maps[y][x] -= half
            active[(x,y)][0] -= half
    # print("road end")
    # pprint(maps)
def attack(x,y,tx,ty):
    road = find_shortest_path(x,y,tx,ty)
    if len(road) != 0:
        raiser_attack(x,y,tx,ty,road)
        return road
    else:
        return bazooka_attack(x,y,tx,ty)

# 정렬해서 첫번째 포탑과 맨 마지막 포탑을 찾는다.
def choice_attacker_and_target():
    temp = list(active.items())
    temp.sort(key=lambda x: (x[1][0],-x[1][1],-(x[0][1] + x[0][0]), -x[0][0]))
    #pprint(temp)
    return temp[0][0],temp[-1][0]


# 공격력이 0 이하인 포탑들을 부순다.
def update_maps(road):
    keys = list(active.keys())
    for (x,y) in keys:
        if maps[y][x] <= 0:
            maps[y][x] = 0
            del active[(x,y)]
# 피해를 주거나 받지 않은 포탑들의 공격력을 1씩 올린다.
def repair(road):
    # print("repair")
    # pprint(maps)
    for i in range(N):
        for j in range(M):
            if [j,i] not in road and (j,i) in active: # 관여되지 않고, 부서지지 않았다면
                active[(j,i)][0] += 1
                maps[i][j] += 1
    #pprint(maps)
def get_super_attacker():
   _max = -1
   #pprint(active)
   for (x,y) in active:
       _max = max(_max,active[(x,y)][0])
   return _max
def solve():
    T = 1
    while T <= K:
        if len(active) == 1:
            break
        #print(" ============= " + str(T))
        (x,y),(tx,ty) = choice_attacker_and_target()
        #print(x,y,tx,ty)
        # 공격자의 공격력을 N+M 만큼 올림
        maps[y][x] += (N + M)
        active[(x,y)][0] += (N+M)
        active[(x,y)][1] = T # 최근 공격한 순서 업데이트
        road = attack(x,y,tx,ty)
        #pprint(road)
        update_maps(road)
        repair(road)
        #pprint(maps)
        T += 1
    print(get_super_attacker())

solve()