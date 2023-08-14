import sys
ip = sys.stdin.readline
N, K = map(int, ip().split())
_list = list(map(int, ip().split()))
dp = [0]*(N+1)
my_list = []
cnt = 0
for i in range(N):
    if _list[i] == 1:
        my_list.append(i)
        cnt += 1
    dp[i] = cnt
# print(dp, my_list)
n = len(my_list)
ans = N
if K == 1 and n != 0:
    ans = 1
elif n > K:
    # print(my_list)
    for i in range(n-K + 1):
        # print(my_list[i+K-1] - my_list[i]+1)
        ans = min(ans, my_list[i+K-1] - my_list[i] + 1)
elif n < K:
    ans = -1
print(ans)
