"""

상어의 번호 -> 1 ~ M 
자기 영역을 지키려고 다른 상어를 쫓아내려함 
번호 1인 상어는 최강이라 다른 상어를 다 쫓아낼 수 있음

상어는 자기가 있는 칸에 냄새를 뿌림 
냄새는 상어가 k 번 이동하고 나면 사라짐 

이동 방향 결정 우선순위
1. 아무 냄새 안나는 칸 
2. 자신의 냄새가 있는 칸 

각 상어별 특정 이동 우선순위가 4줄씩 있음. 
1. 상어가 위를 향할때
2. 상어가 아래를 향할때
3. 상어가 왼쪽을 향할때 
4. 상어가 오른쪽을 향할때 

모든 상어가 이동을 마치면 같은 칸에 있는 상어들 중
가장 작은 번호를 가진 상어를 제외하고 밖으로 퇴출됨. 

solve -> 1번 상어만 격자에 남게 되기까지 몇 초가 걸리는 지
(1000초가 넘었을 경우에는 -1 출력)

"""
import sys
from pprint import pprint
ip = sys.stdin.readline 
N,M,K = map(int,ip().split())

# 방향 : 0 위, 아래, 왼쪽, 오른쪽
dx = [0,0,0,-1,1]
dy = [0,-1,1,0,0]
cur_dir = {} # 현재 상어의 방향
cur_point = {} # 상어들의 좌표 
sea_detail = [[0]*N for _ in range(N)] # 어떤 상어의 냄새인지 저장
sea = [list(map(int,ip().split())) for _ in range(N)]

for i in range(N):
    for j in range(N):
        num = sea[i][j]
        if num != 0 :
            sea_detail[i][j] = num # 초기에 자신의 영역에 냄새를 뿌림 
            cur_point[num] = [j,i] # 상어들의 초기 위치
            sea[i][j] = K

temp = list(map(int,ip().split())) # 초기 방향

for i in range(M):
    cur_dir[i+1] = temp[i]

priority_dir_list = {} # [상어번호][방향] => [방향 우선순위]
for i in range(M):
    priority_dir = {} # [방향] => [방향 우선순위]
    for j in range(4):
        cur = list(map(int,ip().split()))
        priority_dir[j+1] = cur
    priority_dir_list[i+1] = priority_dir
    
#pprint(priority_dir_list[1])
#현재 sea에 상어가 1마리 이상 있는지 확인하는 함수
def is_there_one_shark():
    return len(cur_dir) == 1
#한 번 호출 할 때 마다 시간이 1씩 줄어드는 함수
def clean_sea():
    for i in range(N):
        for j in range(N):
            if sea[i][j] != 0:
                sea[i][j] -= 1
                if sea[i][j] == 0: # K 초가 지나면 
                    sea_detail[i][j] = 0 # 냄새도 지워주기
                

# 상어 번호를 인자로 받는다.
def move_shark(num):
    [x,y] = cur_point[num] # 좌표
    cdir = cur_dir[num] # 방향 
    cpriority_dir = priority_dir_list[num][cdir] #우선순위 방향
    not_find = True # 빈칸을 찾지 못했는지 확인하는 변수
    # 1. 우선순위 방향으로 돌아보면서 빈칸을 찾는다. 
    for i in cpriority_dir:
        nx,ny = x + dx[i], y + dy[i]
        if 0 <= nx < N and 0 <= ny < N:
            if sea[ny][nx] == 0: # 2. 빈칸을 발견하면 그쪽으로 이동을 하며 자신이 원래 있던 칸에는 냄새를 뿌린다. 
                #print("blank")
                not_find = False
                cur_dir[num] = i # 상어의 방향 재설정
                cur_point[num] = [nx,ny] # 상어의 위치 재설정 
                break # 찾았다면 종료
                
    #빈칸을 찾지 못했다면 자기 냄새 있는 칸 찾음 -> 자기 냄새 있는 칸 찾으면 k 초기화됨
    if not_find: 
        for i in cpriority_dir:
            nx,ny = x + dx[i], y + dy[i]
            if 0 <= nx < N and 0 <= ny < N:
                #print("my smell")
                if sea_detail[ny][nx] == num:
                    cur_dir[num] = i # 상어의 방향 재설정
                    cur_point[num] = [nx,ny] # 상어의 위치 재설정 
                    break# 찾았다면 종료
                
    
# 상어가 있는 칸의 위치를 확인하며 sea에는 K를, sea_detail에는 냄새 정보를 업데이트 해준다.
def is_there_more_than_one_shark():
    point_dict = {}
    for num in cur_point:
        [x,y] = cur_point[num]
        if (x,y) in point_dict:
            point_dict[(x,y)].append(num)
        else:
            point_dict[(x,y)] = [num]
    
    for (x,y) in point_dict:
        sea[y][x] = K # 냄새 뿌리기
        c_list = point_dict[(x,y)]
        if len(c_list) > 1 :
            super_shark = min(c_list)
            sea_detail[y][x] = super_shark
            for num in c_list:
                if super_shark == num:
                    continue
                del cur_dir[num]
                del cur_point[num]
        else:
            sea_detail[y][x] = c_list[0]
        
# 모든 상어 움직이기 -> 이때 sea나 sea_detail을 업데이트하면 "동시에 움직인다"를 수행할 수 없음 그래서 나중에 칸 확인하면서 sea와 sea_detail을 업데이트 해줌 
def move_all_shark():
    for num in cur_dir:
        #print(num)
        move_shark(num)
        

def solve():
    t = 0
    while t <= 1000:
        if is_there_one_shark():
            break
        move_all_shark() # 상어를 움직이고 
        clean_sea() # 상어가 움직였으니 이전 칸의 숫자를 1 줄여준다. 
        is_there_more_than_one_shark() # 상어가 있는 곳의 위치를 확인해서 냄새, K를 업데이트 해준다.
        # print( str(t) +" - After")
        # print("number of shark is " + str(len(cur_dir)))
        # print("num")
        # pprint(sea)
        # print("smell")
        # pprint(sea_detail)
        # print("direction")
        # pprint(cur_dir)
        # print()
        t += 1
    if t >= 1001:
        print(-1)
    else:
        print(t)
    
   
# print("Before")
# pprint(sea)
# pprint(sea_detail)
solve()

# for i in range(16):
#     move_all_shark()
#     clean_sea()
#     is_there_more_than_one_shark()
#     print( str(i) +" - After")
#     print("number of shark is " + str(len(cur_dir)))
#     pprint(sea)
#     pprint(sea_detail)
#     print()