import sys
ip = sys.stdin.readline
n = int(ip())
s = 0
e = n
while s <= e:
    m = (s+e) // 2
    if m ** 2 >= n:
        e = m - 1
    else:
        s = m + 1
print(s)
