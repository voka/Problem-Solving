import sys
ip = sys.stdin.readline
N = int(ip())

maps = [list(map(int, ip().split())) for _ in range(N)]


def is_fill_1(sx, sy, ex, ey):
    pre = maps[sy][sx]
    for i in range(sy, ey):
        for j in range(sx, ex):
            if maps[i][j] != pre:
                return False
    return True


def devide(sx, sy, ex, ey):
    if is_fill_1(sx, sy, ex, ey):
        # print(sx, sy, ex, ey, maps[sx][sy])
        if maps[sy][sx] == 1:
            return [0, 1]
        else:
            return [1, 0]
    else:
        mx = (sx + ex)//2
        my = (sy + ey)//2
        a = devide(sx, sy, mx, my)
        b = devide(mx, sy, ex, my)
        c = devide(sx, my, mx, ey)
        d = devide(mx, my, ex, ey)
        # print(a, b, c, d)
        return [a[0] + b[0] + c[0] + d[0], a[1] + b[1] + c[1] + d[1]]


answer = devide(0, 0, N, N)
print(answer[0])
print(answer[1])
