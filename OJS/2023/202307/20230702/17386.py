import sys
ip = sys.stdin.readline
l1x1, l1y1, l1x2, l1y2 = map(int, ip().split())
l2x1, l2y1, l2x2, l2y2 = map(int, ip().split())


def CCW(x1, y1, x2, y2, x3, y3):
    return x1*y2 + x2 * y3 + x3*y1 - (x2*y1 + x3*y2 + x1*y3)


def is_large(a, b):
    if a > b:
        return b, a
    return a, b


result1 = CCW(l1x1, l1y1, l1x2, l1y2, l2x1, l2y1) * \
    CCW(l1x1, l1y1, l1x2, l1y2, l2x2, l2y2)
result2 = CCW(l2x1, l2y1, l2x2, l2y2, l1x1, l1y1) * \
    CCW(l2x1, l2y1, l2x2, l2y2, l1x2, l1y2)

ans = 0
if result1 == 0 and result2 == 0:
    l1x1, l1x2 = is_large(l1x1, l1x2)
    l2x1, l2x2 = is_large(l2x1, l2x2)
    l1y1, l1y2 = is_large(l1y1, l1y2)
    l2y1, l2y2 = is_large(l2y1, l2y2)
    if l1x1 <= l2x1 <= l1x2 or l1x1 <= l2x2 <= l1x2:
        if l1y1 <= l2y1 <= l1y2 or l1y1 <= l2y2 <= l1y2:
            ans = 1
    elif l2x1 <= l1x1 <= l2x2 or l2x1 <= l1x2 <= l2x2:
        if l2y1 <= l1y1 <= l2y2 or l2y1 <= l1y2 <= l2y2:
            ans = 1
elif result1 <= 0 and result2 <= 0:
    ans = 1
print(ans)
