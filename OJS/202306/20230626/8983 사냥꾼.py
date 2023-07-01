import sys
from bisect import bisect_left as bl, bisect_right as br
ip = sys.stdin.readline
M, N, L = map(int, ip().split())
M_list = list(map(int, ip().split()))
M_list.sort()
answer = 0
for _ in range(N):
    x, y = map(int, ip().split())
    target = L - y
    if target < 0:
        continue
    low_target = x - target
    up_target = x + target
    s = bl(M_list, low_target)
    ss = bl(M_list, up_target)
    if 0 <= s < M and low_target <= M_list[s] <= up_target:
        answer += 1
    elif 0 <= ss < M and low_target <= M_list[ss] <= up_target:
        answer += 1
print(answer)
