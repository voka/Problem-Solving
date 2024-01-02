import sys
ip = sys.stdin.readline 

M = int(ip())
N = int(ip())
ans = 0
_min = 10000
i = 1
while i <= 100:
    cur = i **2
    if cur > N:
        break
    if cur >= M :
        ans += cur
        _min = min(_min,cur)
    i += 1
if ans == 0:
    print(-1)
else:
    print(ans)
    print(_min)