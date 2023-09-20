import sys
ip = sys.stdin.readline 
# 2048 같은 게임 
N,M = map(int,ip().split())
# 이전 빨간 구슬의 위치와 이동한 빨간 구슬 위치 사이에 구멍이 있으면 통과 
# 파란 구슬도 빨간 구슬과 똑같이 검사해서 해당된다면 Fail
# 1. 파란 구슬이 구멍에 들어간 경우 -> Fail
# 2. 빨간 구슬만 구멍에 들어간 경우 -> Success
# 3. 빨간 구슬과 파랑구슬이 같은 위치에 있는 경우
    # 이전 위치와 차이값이 큰걸 방향에 따라 벽이 없을때 까지 뒤로 보내기 
    
maps = []
red_ball = []
blue_ball = []
out_point = []
dx = [1,-1,0,0]
dy = [0,0,1,-1]

for i in range(N):
    temp = list(ip().rstrip())
    _input = []
    for j in range(M):
        if temp[j] == '#':
            _input.append(0)
        if temp[j] == '.':
            _input.append(1)
        if temp[j] == 'O':
            _input.append(2)
            out_point.append(j)
            out_point.append(i)
        if temp[j] == 'R':
            red_ball.append(j)
            red_ball.append(i)
            _input.append(1)
        if temp[j] == 'B':
            blue_ball.append(j)
            blue_ball.append(i)
            _input.append(1)
    maps.append(_input)

def get_mdistance(x1,y1,x2,y2): # 두 좌표의 위치 차이값 반환
    return abs(x1-x2) + abs(y1-y2)

def allocate(i,x,y):
    x,y = x - dx[i], y - dy[i]
    while maps[y][x] == 0: # 이 부분을 != 0 이렇게 작성해서 많이 틀림 ... 
        x,y = dx[i] + x, dy[i] + y
    return x,y



 # dir(방향)에 따라 기울이기 동작하는 함수
 # 공이 굴러가던 도중에 구멍을 만나면 result 가 True로 바뀜
def todo(dir,cx,cy):
    result = False
    while True:
        nx,ny = cx+dx[dir], cy + dy[dir]
        if 0<= nx < M and 0<= ny < N and maps[ny][nx] != 0:
            cx,cy = nx,ny
            if maps[ny][nx] == 2:
                result = True
                break
        else:
            break
    return cx,cy,result

ans = 11
def solve(rx,ry,bx,by,cnt): # 재귀함수
    global ans
    #print(rx,ry,cnt, bx,by)
    if cnt >= ans: # 11번 보다 많이 실행하면 멈춤
        return
    for i in range(4): # 우,좌,상,하 순으로 탐색 
        nrx,nry,r_ans = todo(i,rx,ry) # 빨간공 기울이기 
        nbx,nby,b_ans = todo(i,bx,by) # 파란공 기울이기 
        if b_ans == True: # 파란 공이 구멍에 들어가는 경우
            continue # 더이상 진행해도 의미 없으므로 다른 방향으로 기울임
        elif r_ans == True: # 빨간색 공만 구멍에 들어갈 경우 
            #print("ANSWER")
            #print(rx,ry,nrx,nry,cnt)
            #print(check + [i])
            ans = min(ans,cnt) # 현재까지 기울인 횟수를 ans에 넣어줌
            return
        elif nrx == nbx and nry == nby: # 두 공이 겹치는 경우
            #print(i, nrx,nry)
            r_distance =  get_mdistance(rx,ry,nrx,nry) #빨간공의 기울이기 전 위치와 기울인 후 위치 차이값 반환 
            b_distance =  get_mdistance(bx,by,nrx,nry) #파란공의 기울이기 전 위치와 기울인 후 위치 차이값 반환 
            if r_distance > b_distance: # 빨간공이 더 멀리 있다면 
                nrx,nry = allocate(i,nrx,nry) # 빨간공의 위치 조정 -> 현재 위치에서 방향에 따라 벽이 없을때 까지 뒤로 가기  
            else:
                nbx,nby = allocate(i,nbx,nby)
            #print(nrx,nry,nbx,nby)
        
        solve(nrx,nry,nbx,nby,cnt + 1) # 공이 구멍에 들어간 경우가 아니라면 재귀함수 실행
    
solve(red_ball[0],red_ball[1],blue_ball[0],blue_ball[1],1) 

if ans == 11:
    print(-1)
else:
    print(ans)
"""
Example
6 7
#######
##.##.#
#...#O#
#R....#
#.B...#
#######

"""