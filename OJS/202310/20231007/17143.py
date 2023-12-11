
"""

1초동안 발생하는 일 
1. 낚시왕이 오른쪽으로 한칸 이동
2. 아래쪽에서 가장 가까운 상어를 잡는다. 
3. 상어들이 이동한다. 
    - 이동하던 도중 경계를 만나면 방향을 반대로 바꿔 진행한다. 
    - 방향이 바뀌어도 속력은 그대로다. 
4. 이동이 끝난 후 한칸에 두마리 이상 있을경우 크기가 큰 놈이 작은놈을 모두 잡아 먹는다.

todo: 잡은 상어의 크기의 합

input => 같은 칸에 둘 이상의 상어가 들어있는 경우 x 



"""
import sys
ip = sys.stdin.readline

R,C,M = map(int,ip().split())

shark_dict = {} # (x,y) : 크기, 방향, 속력
for i in range(M):
    r, c, s, d, z = map(int,ip().split())
    shark_dict[(c,r)] = [(z,s,d)]
    
# 1: 위, 2: 아래, 3: 오른쪽, 4: 왼쪽
dx = [0,0,0,1,-1]
dy = [0,-1,1,0,0]
# 반복문 버전
def move_shark(x,y,s,d):
    i = 0
    while i < s:
        i += 1
        nx,ny = x+dx[d], y+dy[d]
        if 1 <= nx <= C and 1 <= ny <= R: # 방향그대로 진행 가능한 경우
            x,y = nx,ny
        else:# 방향을 바꿔야 하는 경우에는
            if d == 1 or d == 2:
                d = 3 - d
            else:
                d = 7 - d 
            i -= 1
    return x,y,s,d

# 인덱스 계산 버전
def move_shark_index(x,y,s,d):
    # 1. 속력 만큼 더한다.
    nx,ny = x + dx[d]*s, y + dy[d]*s
    # 방향 1번 바꿔야 하는 경우 
    """
    r,c = 4,6
    x,y -> 1,3
    s,d = 4,2(아래쪽)
    
    nx,ny = 1,3 + 4 = >1,7
    # 위 아래 방향일 경우
    mox = ny // r
    7 // 4 -> 1 -> 방향을 1번만 바꿔도 된다. 
    rest = ny % r 
    7 % 4 -> 3 -> 반대 방향으로 3칸 전진해야 한다.
    d =
    
    # 오른쪽 왼쪽 방향일 경우 
    mox = nx // c 
    
    
    """    
    i = 0
    
    
    while i < s:
        i += 1
        nx,ny = x+dx[d], y+dy[d]
        if 1 <= nx <= C and 1 <= ny <= R: # 방향그대로 진행 가능한 경우
            x,y = nx,ny
        else:# 방향을 바꿔야 하는 경우에는
            if d == 1 or d == 2:
                d = 3 - d
            else:
                d = 7 - d 
            i -= 1
    return x,y,s,d
            
    
def move_all_shark():
    new_shark_dict = {}
    #print(shark_dict)
    for x,y in shark_dict:
        for z,s,d in shark_dict[(x,y)]:
            x,y,s,d = move_shark(x,y,s,d)
            if (x,y) in new_shark_dict:
                new_shark_dict[(x,y)].append((z,s,d))
            else:
                new_shark_dict[(x,y)] = [(z,s,d)]
    #print(new_shark_dict)
    return new_shark_dict

def shark_eat():
    for x,y in shark_dict:
        cur = shark_dict[(x,y)]
        if len(cur) > 1:
            #print(shark_dict[(x,y)])
            cur = sorted(cur,reverse=True) #  제일 큰 상어만 남기기 위해 정렬 (크기순)
            shark_dict[(x,y)] = [cur[0]]
            #print(shark_dict[(x,y)])

# 낚시를 하는 시점에는 물고기가 칸당 한마리만 남았을 경우
def fishing(man_x):
    cur = 0
    for i in range(1,R+1):
        #print(man_x,i)
        if (man_x,i) in shark_dict:
            z,s,d = shark_dict[(man_x,i)][0]
            cur = z
            del shark_dict[(man_x,i)]
            break
    return cur


def solve():
    global shark_dict
    man_x_point = 0
    ans = 0
    while man_x_point <= C:
        man_x_point += 1
        ans += fishing(man_x_point)
        #print(ans)
        shark_dict = move_all_shark()
        shark_eat()
    print(ans)

solve()
