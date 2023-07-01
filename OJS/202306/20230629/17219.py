import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())
mynote = {}
for i in range(N):
    site, pw = map(str, ip().split())
    mynote[site] = pw
for _ in range(M):
    print(mynote[ip().rstrip()])
