import sys
ip = sys.stdin.readline
T = int(ip())
while T:
    T -= 1
    k = int(ip())
    n = int(ip())
    maps = [[0]*(n+1) for _ in range(k+1)]
    for i in range(n+1):
        maps[0][i] = i
    for i in range(1, n+1):
        for m in range(1, k+1):
            cur = 0
            for j in range(i+1):
                cur += maps[m-1][j]
            maps[m][i] = cur
    print(maps[-1][-1])
