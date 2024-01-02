import sys
ip = sys.stdin.readline
"""
문제
- 총 4개의 톱니바퀴가 있고, 회전하는 횟수는 총 K 번임.
- 톱니바퀴 하나에는 8개의 톱니가 있고, 각 톱니는 N, S 극이 있음
- 한 번 회전할때 톱니 한 칸을 기준으로 하며,
- 회전하기 전에 서로 맞다은 극이 다르다면 회전을 하고, 같다면 회전을 하지 않는다.

풀이
- 12시 방향 기준점에 대한 인덱스를 잡고 리스트는 바꾸지 않는 방향
- 예를 들어 12시 방향 인데스가 0일때 반시계 방향 회전이면 (인덱스 + 1) % 8
- 시계 방향 회전이면 인덱스 - 1(인덱스 > 0) , 인덱스 == 0 -> 7
"""
# 연결된 톱니바퀴
gear_neighbor = {1: [2], 2: [1, 3], 3: [2, 4], 4: [3]}
gear = {}

for i in range(4):
    gear[i+1] = list(map(int, list(ip().rstrip())))

gear_12_info = {1: 0, 2: 0, 3: 0, 4: 0}


# 반시계 방향 회전 (12시 방향 인덱스 업데이트)
def rotate_counterclockwise(num):
    cur = gear_12_info[num]
    gear_12_info[num] = (cur+1) % 8


# 시계 방향 회전 (12시 방향 인덱스 업데이트)
def rotate_clockwise(num):
    cur = gear_12_info[num]
    if cur == 0:
        gear_12_info[num] = 7
    if cur > 0:
        gear_12_info[num] = cur - 1

# 12시 방향 인덱스를 통헤 오른쪽, 왼쪽 극 값 반환


def get_left_value(num):
    cur = gear_12_info[num]
    left = (cur - 2)
    if left < 0:
        left += 8
    return gear[num][left]


def get_right_value(num):
    cur = gear_12_info[num]
    right = (cur + 2) % 8
    return gear[num][right]

# 기어를 회전 시킨다.


def turn_gear(num, dir, history):
    # 왼쪽, 오른쪽 값을 구한다
    cur_left = get_left_value(num)
    cur_right = get_right_value(num)
    # print("num : " + str(num))
    # print("dir : " + str(dir))
    # 회전한다.
    if dir == 1:
        rotate_clockwise(num)
    else:
        rotate_counterclockwise(num)

    history.add(num)
    # print(history)

    for neighbor in gear_neighbor[num]:
        if neighbor in history:
            continue
        n_left = get_left_value(neighbor)
        n_right = get_right_value(neighbor)
        if num < neighbor:  # 회전하는 톱니바퀴가 이웃 톱니바퀴의 왼쪽에 있으면
            if cur_right != n_left:  # 극이 서로 다르다면
                turn_gear(neighbor, dir*-1, history)  # 옆의 톱니바퀴도 회전시킴
        else:
            if cur_left != n_right:
                turn_gear(neighbor, dir*-1, history)  # 옆의 톱니바퀴도 회전시킴


K = int(ip())
for i in range(K):
    num, dir = map(int, ip().split())
    turn_gear(num, dir, set())
    # print(gear_12_info)

# print(gear)
print(gear[1][gear_12_info[1]] * 1 + gear[2][gear_12_info[2]] * 2 +
      gear[3][gear_12_info[3]] * 4 + gear[4][gear_12_info[4]] * 8)
