import sys
ip = sys.stdin.readline 

N,M,B = map(int,ip().split())
maps = [list(map(int,ip().split())) for _ in range(N)]

# for i in range(N):
#     _list = list(map(int,ip().split())) 
#     for j in range(M):
#         start = min(start,_list[j])
#         end = max(end,_list[j])
#     maps.append(_list)
ans = 500 * 500 * 256 + 1




def can_do(b,h):
    rtime = 0
    minus_box = 0
    for i in range(N):
        m = maps[i]
        for j in range(M):
            cur = m[j] - h
            if cur == 0 :
                continue
            elif cur > 0:
                rtime += cur * 2
                b += cur
            elif cur < 0:
                cur = -cur
                rtime += cur
                minus_box += cur
    if minus_box > b: # 음수 검사를 반복문 안에서 해서 틀렸음. 
        # 지금은 놓을 수 없는 박스가 있더라도 최종적으로 봤을떄 놓은 박스수 <= 가지고 있는 박스수 이면 된다. 
        return False,0
    return True, rtime
            
        
start = 0
end = 257
#print(start,end)
height = 0
while start < end:
    cresult,cans = can_do(B,start)
    if cresult and ans >= cans:
        ans = cans
        height = start
    start +=1 

print(ans, height)
        

    
        