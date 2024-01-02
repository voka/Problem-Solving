import sys
from itertools import combinations
from bisect import bisect_left as bl, bisect_right as br
ip = sys.stdin.readline
N, S = map(int, ip().split())
_list = list(map(int, ip().split()))
a_list = _list[:N//2]
b_list = _list[N//2:]
a_list_sum = []
b_list_sum = []


answer = 0

for i in range(1, len(a_list)+1):
    for temp in combinations(a_list, i):
        cur = sum(temp)
        if cur == S:
            answer += 1
        a_list_sum.append(cur)

for i in range(1, len(b_list) + 1):
    for temp in combinations(b_list, i):
        cur = sum(temp)
        if cur == S:
            answer += 1
        b_list_sum.append(cur)

a_list_sum.sort()
b_list_sum.sort()
s_s = 0
s_e = len(a_list_sum)
e_e = len(b_list_sum) - 1
while s_s < s_e and e_e >= 0:
    target = a_list_sum[s_s] + b_list_sum[e_e]
    if target == S:
        ns = br(a_list_sum, a_list_sum[s_s])
        ne = bl(b_list_sum, b_list_sum[e_e])
        answer += (ns - s_s) * (e_e - ne + 1)
        s_s, e_e = ns, ne
    elif target > S:
        e_e -= 1
    else:
        s_s += 1
print(answer)
