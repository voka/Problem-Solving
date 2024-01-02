import sys
from bisect import bisect_left as bl, bisect_right as br
ip = sys.stdin.readline
N = int(ip())

_list = list(map(int, ip().split()))

_list.sort()
ans = 0
for i in range(N-2):
    s, e = i+1, N-1
    while s < e:
        _sum = _list[i] + _list[s] + _list[e]
        if _sum == 0:  # 같은 숫자 개수 세기
            if _list[s] == _list[e]:  # 시작, 끝 숫자가 같으면
                ans += (e - s)
            else:
                index = bl(_list, _list[e])  # 시작, 끝 숫자가 다르면
                ans += (e - index + 1)
        if _sum > 0:
            e -= 1
        else:
            s += 1
print(ans)
