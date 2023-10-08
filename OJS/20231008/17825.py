import sys
ip = sys.stdin.readline 
"""

시작칸 
- 말 4개 

움직임 
- 기본적으로 빨간색 화살표 방향으로만 이동 가능
- 파란색 칸에서 이동을 시작 할 경우에만 파란색 화살표로 이동 
- 도착칸으로 이동하면 주사위에 관계없이 이동 끝냄 

게임 
- 총 10개의 턴으로 이루어짐 
- 매턴마다 1 ~ 5 까지 숫자가 적혀있는 주사위를 굴린다. 
- 도착 칸이 아닌 다른 곳에 있는 말을 골라 주사위에 나온 수 만큼 이동시킴
- 말이 도착하는 칸에 다른 말이 있다면 그 말은 고를 수 없다. (도착칸이면 가능)
- 말이 이동을 마칠때 마다 칸에 적혀있는 수가 점수에 추가된다.


todo
- 주사위에서 나올 수 있는 10개의 수를 미리 알고 있는 경우 얻을 수 있는 점수의 최대값 

4 ** 10
solve 
4개의 말을 고르는 모든 순서를 고려한다.
중간에 말이 출발할 수 없는 경우 그 경우의 수는 버린다. 
끝까지 말을 움직일 수 있는 경우 중에 가장 큰 값을 고른다.
"""

from itertools import product

_list = product([1,2,3,4],repeat=10)

dice_list = list(map(int,ip().split()))

maps = [0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40]
start_10 = [0,2,4,6,8,10,13,16,19,25,30,35,40]
start_20 = [0,2,4,6,8,10,12,14,16,18,20,22,24,25,30,35,40]
start_30 = [0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,28,27,26,25,30,35,40]

# 시작 -> 0 
dice_map = [maps,start_10,start_20,start_30]

# input : s,d,dir -> 시작 인덱스, 주사위 숫자, 방향
# return : 다음 인덱스, 도착한 곳의 값, 현재 방향
def find_end_points(s,d,dir):
    #print(s,d,dir)
    if dir == 0 and s == 5:
        dir = 1
    elif dir == 0 and s == 10 :
        dir = 2
    elif dir == 0 and s == 15 :
        dir = 3
    cur = dice_map[dir]
    # 도착했다면 더 이상 움직이지 않게 -1 반환
    if s + d >= len(cur):
        return -1,0,dir 
    else:
        return s+d,cur[s+d],dir

def play(pick_list):
    score = 0
    h_dict = {1:0,2:0,3:0,4:0}
    dir_dict = {1:0,2:0,3:0,4:0}
    for i in range(10):
        dice = dice_list[i]
        h = pick_list[i]
        end = False # 도작 지점에 말이 없다는 가정
        if h_dict[h] == -1: # 도착한 말을 움직이려 한다면 끝냄 
            score = 0
            break
        next_point,value,next_dir = find_end_points(h_dict[h],dice,dir_dict[h])
        # 도착지점에 말 있는지 검사
        if next_point > 0:
            for j in range(1,5): # 
                if j == h or h_dict[j] == -1:
                    continue
                if dice_map[dir_dict[j]][h_dict[j]] == value:
                    if value in [22,24,26,28]: 
                        if (dir_dict[j] != next_dir): # 방향이 서로 다르면 무효
                            continue
                    if value == 30:
                        if next_dir == 0 and dir_dict[j] != 0:
                            continue 
                        if next_dir != 0 and dir_dict[j] == 0:
                            continue
                    end = True
                    break
        if end:
            score = 0 
            break
        
        dir_dict[h] = next_dir
        h_dict[h] = next_point

        score += value
    return score

def find_max_score():
    ans = 0
    for temp in _list:
        cur = play(temp)
        if cur > ans:
            ans = cur

    print(ans)

find_max_score()

