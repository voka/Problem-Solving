import sys
ip = sys.stdin.readline 
from pprint import pprint
from copy import deepcopy
"""
방향 -> 8가지 방향(대각선 이동 가능)
물고기 수 -> 한칸당 1마리씩 존재 -> 총 16마리 
물고기는 고유한 숫자를 가지고 있다. 

- 물고기의 이동
    - 번호가 작은 물고기부터 순서대로 이동 
    - 한 칸 이동 가능
    - 빈 칸 혹은 다른 물고기가 있는 칸으로 이동 가능 
        - 경계를 넘거나 (4x4) 상어가 있는 곳은 이동 불가 
    - 이동할 수 있는 칸이 있다면 45도 반시계 회전을 계속 한다. 
    - 이동할 수 있는 칸이 없으면 이동하지 X 
    - 물고기가 있는 칸으로 이동시 두 물고기의 위치가 바뀜 

물고기의 이동이 끝나면 상어가 이동함. 
- 청소년 상어의 움직임
    - 한 번에 여러 칸을 이동할 수 있다. 
    - 물고기가 있는 칸으로 이동했으면 물고기를 잡아 먹고, 물고기의 방향으로 방향을 변경한다. 
    - 하지만 중간에 있는 물고기는 먹을 수 없다. 
    - 물고기가 없는 칸으로는 이동할 수 없다. 
    - 이동할 수 있는 칸이 없으면 집으로 감 

"""

ans = 0
# ↑, ↖, ←, ↙, ↓, ↘, →, ↗
dx = [0,-1,-1,-1,0,1,1,1]
dy = [-1,-1,0,1,1,1,0,-1]

dir_maps = [[0]*4 for _ in range(4)]
fish_maps = [[0]*4 for _ in range(4)] # 상어 -> 20, 빈칸 -> 0

for i in range(4):
    temp = list(map(int,ip().split()))
    j = 0
    while j < 4:
        num,dir = temp[j*2],temp[j*2+1]
        dir_maps[i][j] = dir - 1 
        fish_maps[i][j] = num
        j+=1

def find_all_num_fish_point(fish_maps):
    result = {}
    for i in range(4):
        for j in range(4):
            if fish_maps[i][j] != 20 and fish_maps[i][j] != 0:
                result[fish_maps[i][j]] = [j,i]
    return result
            
def move_fish(start,fish_dict,dir_maps,fish_maps):
    [x,y] = fish_dict[start]
    num = fish_maps[y][x]
    dir = dir_maps[y][x]
    for i in range(8):
        c_dir = (i + dir)%8
        nx,ny = x + dx[c_dir], y + dy[c_dir]
        if 0 <= nx < 4 and 0 <= ny < 4:
            n_num = fish_maps[ny][nx]
            if n_num == 0:# 빈칸인 경우
                # 원래 있던 장소 빈칸으로 만들기 
                fish_maps[y][x] = 0
                #dir_maps[y][x] = 100
                # 빈칸이였던 곳으로 물고기 이동 
                dir_maps[ny][nx] = c_dir # 방향 변경 
                fish_maps[ny][nx] = num # 물고기 위치 변경
                fish_dict[num] = [nx,ny]
                return fish_dict,dir_maps,fish_maps
            elif n_num != 20:# 상어가 없고 물고기가 있는 경우
                dir_maps[y][x] = dir_maps[ny][nx]
                dir_maps[ny][nx] = c_dir
                fish_maps[ny][nx],fish_maps[y][x] = num,n_num
                fish_dict[n_num],fish_dict[num] = fish_dict[num],fish_dict[n_num]
                return fish_dict,dir_maps,fish_maps
    return fish_dict,dir_maps,fish_maps

def move_all_fish(dir_maps,fish_maps):
    fish_dict = find_all_num_fish_point(fish_maps)
    ndir_maps = deepcopy(dir_maps)
    nfish_maps = deepcopy(fish_maps)
    
    for i in range(1,17):
        if i in fish_dict:
            # print( str(i) + " before")
            # print(nfish_maps)
            # print(ndir_maps)
            fish_dict,ndir_maps,nfish_maps = move_fish(i,fish_dict,ndir_maps,nfish_maps)
            # print("after")
            # print(nfish_maps)
            # print(ndir_maps)
    return ndir_maps,nfish_maps

answer = 0
def move_shark(x,y,ans,c_dir,dir_maps,fish_maps):
    global answer
    # 잡아먹기
    #print(num,x,y)
    #print(fish_maps)
    #print(dir_maps)
    ndir_maps,nfish_maps = move_all_fish(dir_maps,fish_maps) # 모든 물고기 이동
    for i in range(1,4): # 같은 방향으로 최대 3번 진행 가능
        nx,ny = x + dx[c_dir]*i , y + dy[c_dir]*i 
        #print(dir,nx,ny)
        if 0 <= nx < 4 and 0 <= ny < 4:
            if  0 < nfish_maps[ny][nx] <= 16: # 물고기가 있으면 현재 칸을 0으로 만들고 다음 칸으로 이동
                nfish_maps[y][x] = 0
                temp = nfish_maps[ny][nx] 
                nfish_maps[ny][nx] = 20
                move_shark(nx,ny,ans + temp,ndir_maps[ny][nx],ndir_maps,nfish_maps)
                nfish_maps[ny][nx] = temp
    if answer < ans  :
        answer = ans

start_value = fish_maps[0][0]
fish_maps[0][0] = 20

move_shark(0,0,start_value,dir_maps[0][0],dir_maps,fish_maps)
print(answer)