"""
조건
    maps: NxN 
        각 칸은 흰색, 빨간색, 파란색 중 하나로 색칠되어 있음. 
    말의 개수 -> K , 각 말에 따라 이동 방향도 미리 정해져 있음. 
    말 겹치기 가능 -> 겹쳐진 말은 같이 이동하게 됨
    이동방향 
        - 1 : 오른쪽
        - 2 : 왼쪽 
        - 3 : 위쪽 
        - 4 : 아래쪽
    
게임 방법
    중요 : 말이 4개 이상 쌓이는 순간 게임이 종료된다.
    1. 체스판 위에 말 K개를 두고 시작 
    2. 한 번의 턴에는 1~K 번 말까지 순서대로 이동시켜야함. 
        1 이동
        2 이동
    3. 흰색칸(0)으로 이동할때, 칸에 이미 말이 있는 경우에는 칸에 있는 말 위에 A번 말을 올려놓는다.

    4. 빨간색칸(1)으로 이동할때, 이동한 후엔 이동한 말과 그 위에 있는 모든 말의 순서를 반대로 바꾼다.
        - 기존 칸에 있는 말들 순서는 변화가 없다.
    5. 파란색칸(2)으로 이동할때, 이동하려는 말의 이동방향을 반대로 하고 한칸 이동한다. 
        - 반대편 칸에도 파란섹이 있는 경우에는 이동하지 않고 가만히 있음 . 
    6. 체스판을 벗어나려는 말의 경우에도 파란색과 동일하게 
"""
import sys
ip = sys.stdin.readline 

N,K = map(int,ip().split())
maps = [list(map(int,ip().split())) for _ in range(N)]
horse = {}
horse_info = {}
for i in range(K):
    y,x,dir = map(int,ip().split())
    horse[i] = (x-1,y-1,dir-1)
    if (x-1,y-1) in horse_info:
        horse_info[(x-1,y-1)].append(i)
    else:
        horse_info[(x-1,y-1)] = [i]
# 이동방향 
#         - 1 : 오른쪽
#         - 2 : 왼쪽 
#         - 3 : 위쪽 
#         - 4 : 아래쪽
dx = [1,-1,0,0]
dy = [0,0,-1,1]
#print(horse)


def find_idx_at_point(cx,cy,target):
    for i in range(K):
        if horse_info[(cx,cy)][i] == target:
            return i

def arrive_red(cx,cy,nx,ny,num):
    #print("red")
    #print(cx,cy,nx,ny)
    
    find_idx = find_idx_at_point(cx,cy,num)
    order_list = horse_info[(cx,cy)][find_idx:]
    order_list.reverse()
    
    if (nx,ny) not in horse_info: # 말이 없는 경우 
        horse_info[(nx,ny)] = order_list
    else: # 말이 있는 경우
        horse_info[(nx,ny)].extend(order_list)
    
    for i in order_list: # 위에 있는 말들 이동
        x,y,dir = horse[i]
        horse[i] = (nx,ny,dir)

    if find_idx == 0:
        del horse_info[(cx,cy)]
    else:
        del horse_info[(cx,cy)][find_idx:] # 이전 자리에 있던 말 정보 삭제
        
def arrive_white(cx,cy,nx,ny,num):
    #print("white")
    #print(cx,cy,nx,ny)
    find_idx = find_idx_at_point(cx,cy,num)
    order_list = horse_info[(cx,cy)][find_idx:]
    if (nx,ny) not in horse_info: # 말이 없는 경우 
        horse_info[(nx,ny)] = order_list
    else: # 말이 있는 경우
        horse_info[(nx,ny)].extend(order_list)
    
    for i in order_list: # 위에 있는 말들 이동
        x,y,dir = horse[i]
        horse[i] = (nx,ny,dir)
    if find_idx == 0:
        del horse_info[(cx,cy)]
    else:
        del horse_info[(cx,cy)][find_idx:] # 이전 자리에 있던 말 정보 삭제

def arrive_blue(cx,cy,dir,num):
    #print("blue")
    #print(cx,cy)
    #print(dir)
    # 반대 방향으로 바꾸기 
    if dir in [0,1]:
        changed_dir = 1 - dir
    if dir in [2,3]:
        changed_dir = 5 - dir
    #print(changed_dir)
    # 방향 변경
    horse[num] = (cx,cy,changed_dir)
    #print(horse[num])
    # 반대 방향으로 이동했을때 밖으로 나가거나 파란색 블럭이면 움직이지 않음
    nnx,nny = cx + dx[changed_dir], cy + dy[changed_dir]
    if  0 <= nnx < N and 0 <= nny < N : # 경계 영역 안에 있고
        if maps[nny][nnx] == 0 : # 파란색 칸이 아니면 이동
            arrive_white(cx,cy,nnx,nny,num)
        elif maps[nny][nnx] == 1:
            arrive_red(cx,cy,nnx,nny,num)
from pprint import pprint 

def check_is_end():
    #pprint(horse)
    #pprint(horse_info)
    check_dict = {}
    for i in horse:
        x,y,dir = horse[i]
        if (x,y) in check_dict:
            check_dict[(x,y)] += 1
            if check_dict[(x,y)] >= 4:
                return True
        else:
            check_dict[(x,y)] = 1
    return False


def move_all_horse():
    #pprint(horse)
    tasks = list(horse.keys())
    for i in tasks:
        x,y,dir = horse[i]
        nx,ny = x + dx[dir], y + dy[dir]
        if 0<=nx<N and 0<=ny<N:
            if maps[ny][nx] == 0:
                arrive_white(x,y,nx,ny,i)
            elif maps[ny][nx] == 1:
                arrive_red(x,y,nx,ny,i)
            elif maps[ny][nx] == 2:
                arrive_blue(x,y,dir,i)
        else:
            arrive_blue(x,y,dir,i)
        if check_is_end():
            return True
    return False


def solve():
    t = 1
    while t <= 1000:
        #print("Cur====> " + str(t))
        if move_all_horse():
            break
        t += 1
    if t > 1000:
        print(-1)
    else:
        print(t)    
solve()

"""
4 4
0 0 2 0
0 0 1 0 
0 0 1 2
0 2 0 0 
2 1 1
3 2 3
2 2 1
4 1 2


"""