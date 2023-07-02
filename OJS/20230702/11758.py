# CCW 알고리즘
import sys
ip = sys.stdin.readline
x1, y1 = map(int, ip().split())
x2, y2 = map(int, ip().split())
x3, y3 = map(int, ip().split())


def CCW(x1, y1, x2, y2, x3, y3):
    return x1*y2 + x2 * y3 + x3*y1 - (x2*y1 + x3*y2 + x1*y3)


result = CCW(x1, y1, x2, y2, x3, y3)
ans = 0
if result > 0:
    ans = 1
elif result < 0:
    ans = -1
print(ans)
