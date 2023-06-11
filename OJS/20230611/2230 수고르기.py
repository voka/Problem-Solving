import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())
_list = [0]*(N)
for i in range(N):
    _list[i] = int(ip())
_list.sort()
answer = _list[-1] - _list[0]
s = e = 0
while (s < N and e < N):
    c = _list[e] - _list[s]
    if (c >= M):
        answer = min(answer, c)
        s += 1
    else:
        e += 1

print(answer)
