import sys
ip = sys.stdin.readline 
N,M,x,y,k = map(int,ip().split())
dice_map = [list(map(int,ip().split())) for _ in range(N)]
command_list = list(map(int,ip().split()))
"""


# 1. 주사위를 굴리면 
#     1. 이동했을때 바닥의 수가 0 이면 -> 주사위 바닥면 수 -> 칸에 복사 
#     2. '' 0 이 아닌 바닥수 -> 주사위 바닥면 복사 , 바닥 수 -> 0 
# 맨 처음엔 1위로 오른쪽이 3 으로 된 상태에서 시작 
# 가장 처음 주사위는 모든 면이 0 으로 적혀있음. 

# - 오른쪽, 왼쪽으로 굴릴때는 % 3. 위, 아래로 굴릴때는 % 4 
# - 주사위가 오른쪽으로 이동 -> 1 (동쪽)
# - 주사위가 왼쪽으로 이동 -> 2 (서쪽)
# - 주사위가 위쪽으로 이동 -> 3 (북쪽)
# - 주사위가 밑쪽으로 이동 -> 4 (남쪽)
# (위,앞,밑,뒤,오른쪽,왼쪽)
# 초기 -> 1,5,6,2,3,4
# 오른쪽으로 굴리면 -> 4,5,3,2,1,6
# 왼쪽으로 굴리면 -> 3,5,4,2,6,1
# 위쪽으로 굴리면 -> 5,6,2,1,3,4
# 아래쪽으로 굴리면 -> 2,1,5,6,3,4
"""

dx = [0,1,-1,0,0]
dy = [0,0,0,-1,1]
#(위,앞,밑,뒤,오른쪽,왼쪽)
dice = [0]*6

def turn_dice(dir):
    global dice
    [a,b,c,d,e,f] = dice
    if dir == 1:
        dice = [f,b,e,d,a,c]
    elif dir == 2:
        dice = [e,b,f,d,c,a]
    elif dir == 3:
        dice = [b,c,d,a,e,f]
    else:
        dice = [d,a,b,c,e,f]
        

def roll_dice(x,y,dir):
    nx,ny = x + dx[dir] ,y + dy[dir]
    if 0<=nx< M and 0 <= ny < N:
        #print(nx,ny,dir,dice)
        turn_dice(dir)
        #print(nbottom)
        # map의 값이 0이 아니라면 주사위로 값을 복사, 칸의 수 -> 0  
        # map의 값이 0이라면 주사위의 바닥면에 있는 수가 칸에 복사됨
        if dice_map[ny][nx] != 0:
            dice[2] = dice_map[ny][nx]
            dice_map[ny][nx] = 0
        else:
            dice_map[ny][nx] = dice[2]
        print(dice[0])
        return nx,ny
    return x,y
x,y = y,x
#print(x,y)
for c in command_list:
    x,y =roll_dice(x,y,c)
