import sys
from bisect import bisect_left as bl, bisect_right as br
ip = sys.stdin.readline

T = int(ip())
an = int(ip())
a_list = list(map(int, ip().split()))
bn = int(ip())
b_list = list(map(int, ip().split()))

a_sums = []
b_sums = []
for i in range(an):
    for j in range(i+1, an+1):
        a_sums.append(sum(a_list[i:j]))
for i in range(bn):
    for j in range(i+1, bn+1):
        b_sums.append(sum(b_list[i:j]))
a_sums.sort()
b_sums.sort()


def solve(_small, _list):
    answer = 0
    for c in _small:
        t = T - c
        bs = bl(_list, t)
        be = br(_list, t)
        answer += be - bs
    return answer


if len(a_sums) > len(b_sums):
    print(solve(b_sums, a_sums))
else:
    print(solve(a_sums, b_sums))
