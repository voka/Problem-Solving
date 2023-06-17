import sys
ip = sys.stdin.readline
N = int(ip())
_list = list(map(int, ip().split()))
_list.sort()
if (N % 2 == 0):
    print(_list[N//2 - 1])
else:
    print(_list[N//2])
