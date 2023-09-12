import sys
ip = sys.stdin.readline 
N = int(ip())
maps = [list(ip().rstrip()) for _ in range(N)]
ans = -1
def swap(cx,cy,px,py):
    tmp = maps[cy][cx]
    maps[cy][cx] = maps[py][px]
    maps[py][px] = tmp
    
def cal_long_candy():
    ans = -1
    for i in range(N): # 행 검사
        tmp = 1
        pre = maps[i][0]
        for j in range(1,N):
            if maps[i][j] == pre :
                tmp += 1
            else:
                ans = max(ans,tmp)
                tmp = 1
                pre = maps[i][j]
        ans = max(ans,tmp)
    for j in range(N): # 열 검사
        tmp = 1
        pre = maps[0][j]
        for i in range(1,N):
            if maps[i][j] == pre :
                tmp += 1
            else:
                ans = max(ans,tmp)
                tmp = 1
                pre = maps[i][j]
        ans = max(ans,tmp)
    return ans

for i in range(N):
    for j in range(N-1):
        # 오른쪽 
        if maps[i][j] != maps[i][j+1]:
            swap(j,i,j+1,i)
            ans = max(ans,cal_long_candy())
            swap(j,i,j+1,i)
        
        # 아래쪽
        if i != N-1 and maps[i][j] != maps[i+1][j]:
            swap(j,i,j,i+1)
            ans = max(ans,cal_long_candy())
            swap(j,i,j,i+1)

print(ans)