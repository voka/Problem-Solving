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


"""

0,6 3,9 
m1x -> 3 // 3 -> 1
m2x -> 1 *2 -> 2
m1y -> 15 // 3-> 5
"""


def devide(sx, sy, ex, ey):
    if is_fill_1(sx, sy, ex, ey):
        if maps[sy][sx] == 1:
            return [0, 0, 1]
        elif maps[sy][sx] == 0:
            return [0, 1, 0]
        else:
            return [1, 0, 0]
    else:
        plus_x = (ex - sx) // 3
        plus_y = (ey - sy) // 3

        m1x = sx + plus_x
        m2x = sx + plus_x * 2
        m1y = sy + plus_y
        m2y = sy + plus_y * 2
        a = devide(sx, sy, m1x, m1y)
        b = devide(m1x, sy, m2x, m1y)
        c = devide(m2x, sy, ex, m1y)
        d = devide(sx, m1y, m1x, m2y)
        e = devide(m1x, m1y, m2x, m2y)
        f = devide(m2x, m1y, ex, m2y)
        g = devide(sx, m2y, m1x, ey)
        h = devide(m1x, m2y, m2x, ey)
        i = devide(m2x, m2y, ex, ey)
        rx = a[0] + b[0] + c[0] + d[0] + e[0] + f[0] + g[0] + h[0] + i[0]
        ry = a[1] + b[1] + c[1] + d[1] + e[1] + f[1] + g[1] + h[1] + i[1]
        rz = a[2] + b[2] + c[2] + d[2] + e[2] + f[2] + g[2] + h[2] + i[2]
        return [rx, ry, rz]


answer = devide(0, 0, N, N)
print(answer[0])
print(answer[1])
print(answer[2])
