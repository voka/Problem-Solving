import sys
ip = sys.stdin.readline
N = int(ip())
limit = list(map(int, ip().split()))
M = int(ip())
boxs = list(map(int, ip().split()))
limit.sort(reverse=True)
boxs.sort(reverse=True)


def check():
    i = 0
    t = 0
    while boxs:
        for l in limit:
            for j in range(len(boxs)):
                if l >= boxs[j]:
                    del boxs[j]
                    break
        t += 1
    return t


if limit[0] < boxs[0]:
    print(-1)
else:
    print(check())
