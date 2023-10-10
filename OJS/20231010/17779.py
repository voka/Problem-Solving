import sys
ip = sys.stdin.readline 
from pprint import pprint
"""
지도를 5개의 구역으로 나누고 거기서 최대 영역과 최소영역의 차이 구하기 

구역 1
x -> x ~ x + d1
y => y ~ h - d1

구역 2
x -> x ~ x + d2
y => y ~ y + d2

구역 3


"""
N = int(ip())
maps = [list(map(int,ip().split())) for _ in range(N)]

def cal_min_max_differ(mymaps):
    result = [0]*5
    for i in range(N):
        for j in range(N):
            result[mymaps[i][j]-1] += maps[i][j]
    result.sort()
    return abs(result[0] - result[-1])
            
    pass

def draw_line(temp,x,y,d1,d2):
    
    # line 1
    for i in range(d1+1):
        temp[y+i][x-i] = 5
    
    # line 2
    for i in range(d2+1):
        temp[y+i][x+i] = 5
    
    # line 3
    
    for i in range(d2+1):
        temp[y+d1+i][x-d1+i] = 5
        
    # line4
    for i in range(d1+1):
        temp[y+d2+i][x+d2-i] = 5
    
    #pprint(temp)
    return temp


def fill(temp,x,y,d1,d2):
    # fill5 
    for i in range(N):
        s = 0 
        while s < N and temp[i][s] != 5:
            s += 1
        e = N-1 
        while e >= 0 and temp[i][e] != 5:
            e -= 1
        if s < e :
            for j in range(s+1,e):
                 temp[i][j] = 5 
            
    # fill 1
    for i in range(y+d1):
        for j in range(x+1):
            if temp[i][j] != 5:
                temp[i][j] = 1
    # fill 2
    for i in range(y+d2+1):
        for j in range(x+1,N):
            if temp[i][j] != 5:
                temp[i][j] = 2
     # fill 3
    for i in range(y+d1,N):
        for j in range(x-d1+d2):
            if temp[i][j] != 5:
                temp[i][j] = 3
    # fill 4
    for i in range(y+d2+1,N):
        for j in range(x-d1+d2,N):
            if temp[i][j] != 5:
                temp[i][j] = 4
    
    #pprint(temp)
    
    return temp
    
        

def divide_5(y,x,d1,d2):
    temp = [[0]*(N) for _ in range(N)]
    draw_line(temp,x,y,d1,d2)
    fill(temp,x,y,d1,d2)
    return cal_min_max_differ(temp)
    

"""
d1,d2 >= 1
x + d1 + d2 <= N 
1 <= x
1 <= y - d1
d1 + 1 <= y
y + d2 <= N 


d1 <= y - 1
d2 <= N - y


"""

def solve():
    ans = N*N*101
    for y in range(N):
        for x in range(N):
            for d1 in range(1,N):
                for d2 in range(1,N):
                    if (d1 >= 1) and (d2 >= 1) and (0 <= y < y+d1+d2 < N) and (0 <= x-d1 < x < x + d2 < N):
                        #print(y,x,d1,d2)
                        ans = min(ans,divide_5(y,x,d1,d2))
                        
    print(ans)
solve()