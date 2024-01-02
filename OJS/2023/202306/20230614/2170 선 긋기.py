import sys
ip = sys.stdin.readline

N = int(ip())

_list = []

for _ in range(N):
    _list.append(tuple(map(int, ip().split())))

_list.sort()

l = _list[0][0]
r = _list[0][1]
answer = 0
for s, e in _list[1:]:
    if r >= e:
        continue

    elif s <= r < e:
        r = e

    elif r < s:
        answer += r - l
        l = s
        r = e

answer += r - l
print(answer)
