"""
아기상어 

maps: NxN
물고기 : M 마리
아기상어 : 1마리 

초기 크기 
아기상어 : 2 

1초에 상하좌우로 한칸씩 이동함
자기보다 큰 물고기가 있는 칸은 지나갈 수 없음 
나머지는 다 지나갈 수 있음. 

자기보다 작은 물고기를 먹을 수 있고, 같으면 통과 가능, 크면 통과 불가능
자신의 크기와 같은 수의 물고기를 먹을면 크기가 1 증가함
(2이면 2마리 먹어야지 3이됨)


이동을 결정하는 방식 

maps에 더이상 먹을 수 있는 물고기가 없다면 엄마에게 도움 요청

주위 물고기 수 == 1: 
    해당 칸으로 이동 
주위 물고기 수 > 1:
    가장 가까운 곳에 있는 물고기를 먹으러 간다. (칸의 개수의 최소)
    가장 가까운 곳에 물고기가 많다면 가장 위쪽, 
    그러한 물고기가 여러마리면 가장 왼쪽에 있는 물고기를 먹음.
    
0 -> 빈칸
1 ~ 6 -> 물고기 크기
9 -> 아기 상어 위치

"""

import sys
import heapq
from pprint import pprint
from collections import deque
ip = sys.stdin.readline

# 아기 상어 위치
sx, sy = 0, 0

# 아기 상어 크기
sm = 2
# 현재 까지 먹은 물고기 개수
eat = 0

fish_dict = {}  # 물고기 크기별 수

for i in range(-1, 7):
    fish_dict[i] = 0

N = int(ip())
sea = [list(map(int, ip().split())) for _ in range(N)]
for i in range(N):
    for j in range(N):
        cur = sea[i][j]
        if cur == 9:
            sea[i][j] = 0
            sy, sx = i, j
        elif cur != 0:
            fish_dict[cur] += 1


# bfs 진행
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]


def get_all_minimun_distance():
    distance = [[N*N*N]*N for _ in range(N)]
    distance[sy][sx] = 0
    pq = []
    heapq.heappush(pq, (0, sy, sx))
    while pq:
        t, y, x = heapq.heappop(pq)
        if t > distance[y][x]:
            continue
        for i in range(4):
            nx, ny = x+dx[i], y+dy[i]
            if 0 <= nx < N and 0 <= ny < N and sea[ny][nx] <= sm:
                if distance[ny][nx] > t + 1:
                    distance[ny][nx] = t + 1
                    heapq.heappush(pq, (t+1, ny, nx))
    return distance


# def cal_distance(ex, ey):

#     return abs(sx-ex) + abs(sy-ey)

# 먹을 물고기가 있을 경우에만 진행


def find_closest_fish():
    global sm, eat
    cx, cy = -1, -1
    close_m = N*N*N  # 최대로 갈 수 있는 길이
    distance_map = get_all_minimun_distance()
    # pprint(distance_map)
    for i in range(N):
        for j in range(N):
            if sea[i][j] != 0 and sea[i][j] < sm:
                cur = distance_map[i][j]
                if close_m > cur:
                    close_m = cur
                    cx, cy = j, i
    # print(cx, cy)
    if cx == -1 and cy == -1:  # 먹을 수 있는 상어는 있지만 이동하지 못하는 경우
        sm = 0
        return 0, 0, 0
    # print(cx, cy)
    # 물고기 dict에서 제거
    fish_dict[sea[cy][cx]] -= 1
    # 물고기는 지도에서 제거
    sea[cy][cx] = 0
    # 먹은 개수 증가
    eat += 1
    # 먹은 개수가 크기와 같으면 크기 증가 하고 먹은 개수 0으로 초기화
    if eat == sm:
        sm += 1
        eat = 0
    return cx, cy, distance_map[cy][cx]

# 가장 가까운 물고기를 찾고 그 지점까지 얼마나 걸리는지


def move():
    global sx, sy  # 아기 상어 위치
    ex, ey, time = find_closest_fish()
    sx, sy = ex, ey
    return time

# 아기상어 보다 몸집이 작은 물고기들이 존재 하는지


def small_than_shark():
    cnt = 0
    start = min(max(sm-1, 0), 6)
    for i in range(start, 0, -1):
        cnt += fish_dict[i]
    # print(cnt)
    return cnt > 0


time = 0
cnt = 0
while True:
    # pprint(sea)
    # print(sm, eat, time)
    if small_than_shark() == False:
        break
    time += move()
    # pprint(sea)
    cnt += 1
print(time)
