import sys
ip = sys.stdin.readline
N = int(ip())
M = int(ip())
arr = list(map(int, ip().split()))
start = 0
end = N-1
arr.sort()
ans = 0
while start < end:
    cur = arr[start] + arr[end]
    if cur == M:
        ans += 1
        start += 1
        end -= 1
    elif cur < M:
        start += 1
    else:
        end -= 1
print(ans)
