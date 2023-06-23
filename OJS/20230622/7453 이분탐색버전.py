import sys
from bisect import bisect_left as bl, bisect_right as br
ip = sys.stdin.readline
N = int(ip())
_list = []
for i in range(N):
    temp = list(map(int, ip().split()))
    _list.append(temp)
ab, cd = [], []


for i in range(N):
    for j in range(N):
        ab.append(_list[i][0] + _list[j][1])
        cd.append(_list[i][2] + _list[j][3])
ab.sort()
cd.sort()

answer = 0

s_s = 0
s_e = len(ab)
e_e = len(cd) - 1

while s_s < s_e and e_e >= 0:
    target = ab[s_s] + cd[e_e]
    if target == 0:
        ns = br(ab, ab[s_s])
        ne = bl(cd, cd[e_e])
        # print("answer {}".format(answer))
        answer += (ns - s_s) * (e_e - ne + 1)
        # print("answer {}".format(answer))
        # print(ab[s_s:ns])
        # print(cd[ne:e_e+1])
        s_s, e_e = ns, ne
    elif target > 0:
        e_e -= 1
    else:
        s_s += 1

print(answer)
