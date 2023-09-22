import sys
from pprint import pprint 
ip = sys.stdin.readline 
dx = [-1,0,1,0]
dy = [0,1,0,-1]
left = { # 왼쪽으로 이동할때 날라가는 모래 
        (-2,0):5, 
        (1,1):1, (1,-1):1,
        (0,1):7,(0,-1):7, 
        (0,2):2,(0,-2):2,
        (-1,1):10,(-1,-1):10
        }
right = {# 오른쪽으로 이동할때 날라가는 모래 
        (2,0):5, 
        (-1,1):1, (-1,-1):1,
        (0,1):7,(0,-1):7, 
        (0,2):2,(0,-2):2,
        (1,1):10,(1,-1):10
        }
up = { # 위쪽으로 이동할때 날라가는 모래 
        (0,-2):5, 
        (1,1):1, (-1,1):1,
        (1,0):7,(-1,0):7, 
        (2,0):2,(-2,0):2,
        (1,-1):10,(-1,-1):10
        }
down = {# 아래쪽으로 이동할때 날라가는 모래 
        (0,2):5, 
        (1,-1):1, (-1,-1):1,
        (1,0):7,(-1,0):7, 
        (2,0):2,(-2,0):2,
        (1,1):10,(-1,1):10
        }
sand_rule = [left,down,right,up]

N = int(ip())
sand_maps = [list(map(int,ip().split())) for _ in range(N)]

start_point = N//2
ans = 0

def sand_put_away(x,y,dir): # 모래 날리기 
    global ans 
    sand_m = sand_maps[y][x]
    use_sand = 0
    sand_maps[y][x] = 0
    cur = sand_rule[dir]
    for (sdx,sdy) in cur.keys():
        #print(sdx,sdy)
        nx,ny = x + sdx, y + sdy
        per = cur[(sdx,sdy)]
        sand = (sand_m * per) // 100 # 각 비율별로 소수점 계산 한 모래 옮기기
        use_sand += sand # 옮겨진 모래 계산하기 (바깥으로 간 것도 포함)
        #print(sand_m,per,sand)
        if 0 <= nx < N and 0<= ny < N :
            sand_maps[ny][nx] += sand  # 안 영역에 있으면 더해주기 
        else:
            ans += sand # 바깥 영역으로 갔다면 나간 모래에 더해주기
    ax,ay = x + dx[dir] , y + dy[dir]# 남은 모래 a 영역에 넣기 
    if 0 <= ax < N and 0<= ay < N :
        sand_maps[ay][ax] += (sand_m - use_sand)
    else:
        ans += (sand_m - use_sand)
def solve(x,y,dis,dir,scnt): # dis : 이동할 거리, dir : 방향 , scnt: dis로 2번 이동 했으면 거리를 늘리기 위한 장치 
    
    if x == 0 and y == 0 :
        return
    if scnt == 2:
        scnt = 0
        dis += 1
    for _ in range(dis):
        nx,ny = x+ dx[dir],y+dy[dir]
        sand_maps[ny][nx] += sand_maps[y][x]
        #pprint(sand_maps)
        sand_put_away(nx,ny,dir)
        #pprint(sand_maps)
        if x == 0 and y ==0:
            return
        x,y = nx,ny
    solve(x,y,dis,(dir+1)%4,scnt+1)

solve(start_point,start_point,1,0,0)

#print(total, get_total(sand_maps))
print(ans)


