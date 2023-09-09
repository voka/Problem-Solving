import sys
ip = sys.stdin.readline
while True:
    n, m = map(int, ip().split())
    if n == 0 and m == 0:
        break
    m_dict = {}
    n_dict = {}
    for i in range(n):
        n_dict[int(ip())] = 1

    for i in range(m):
        m_dict[int(ip())] = 1
    ans = 0
    for k in n_dict.keys():
        if k in m_dict:
            ans += 1
    print(ans)
