import sys
ip = sys.stdin.readline

"""
t = t1 + t2

h1 = root(x ^ 2 - t ^ 2)
h2 = root(y ^ 2 - t ^ 2)

t : h1 = t2 : c   // 빗변 x 
t : h2 = t1 : c   // 빗변 y 

c * t = h1 * t2
c * t = h2 * t1


t1 = c * ( t1 + t2) / h2
t2 = c * ( t1 + t2 ) / h1

c*t/ h2 + c * t / h1 = t
c * (h1 + h2) / h1 * h2 = 1
c = (h1 * h2) / (h1 + h2)

"""

x, y, c = map(float, ip().split())

s = 0
e = min(x, y)
ans = 0
while s < e - 0.000001:
    t = (s + e) / 2
    h1 = (x ** 2 - t ** 2) ** 0.5
    h2 = (y ** 2 - t ** 2) ** 0.5
    cur_c = (h1 * h2) / (h1 + h2)
    if cur_c >= c:
        ans = t
        s = t
    else:
        e = t
print("{:.3f}".format(ans))
