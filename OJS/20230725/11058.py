import sys
ip = sys.stdin.readline
N = int(ip())

dp = [0 for _ in range(101)]

for i in range(1, N+1):
    dp[i] = max(i, dp[i-3]*2, dp[i-4]*3, dp[i-5]*4)
    #
print(dp[N])
