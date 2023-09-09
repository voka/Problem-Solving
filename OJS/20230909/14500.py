import sys
ip = sys.stdin.readline 
dxy_1 = [[(1,0),(2,0),(3,0)], [(0,1),(0,2),(0,3)]]
dxy_2 = [[(1,0),(1,1),(0,1)]]
dxy_3 = [[(1,0),(2,0),(2,1)],[(1,0),(2,0),(2,-1)],[(0,1),(0,2),(1,2)],[(0,1),(0,2),(-1,2)],[(0,-1),(1,0),(2,0)],[(0,1),(1,0),(2,0)],[(1,0),(0,1),(0,2)],[(-1,0),(0,1),(0,2)]]
dxy_4 = [[(1,0),(1,1),(2,1)],[(1,0),(1,-1),(2,-1)],[(0,1),(1,1),(1,2)],[(0,1),(-1,1),(-1,2)]]
dxy_5 = [[(1,0),(1,1),(2,0)],[(0,1),(-1,1),(0,2)],[(1,0),(1,-1),(2,0)],[(0,1),(1,1),(0,2)]]
dxy_list = [dxy_1,dxy_2,dxy_3,dxy_4,dxy_5]
# x,y 대칭 1 , 


N,M = map(int,ip().split())
maps = [list(map(int,ip().split())) for _ in range(N)]

def can_put(cx,cy,dxy):
    tmp = maps[cy][cx]
    for dx,dy in dxy:
        nx,ny = cx + dx, cy + dy
        if 0<=nx<M and 0<=ny<N:
            tmp += maps[ny][nx]
        else:
            return 0
    return tmp
def cal(_list):
    tmp = 0
    for i in range(N):
        for j in range(M):
            for dxy in _list:
                tmp = max(tmp,can_put(j,i,dxy))
    return tmp

ans = -1 
for dxy in dxy_list:
    ans = max(ans,cal(dxy))
print(ans)




