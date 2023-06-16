import sys
ip = sys.stdin.readline
N, k = map(int, ip().split())
arr = list(map(int, ip().split()))
arr.sort()
print(arr[-k])
