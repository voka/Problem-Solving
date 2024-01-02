import sys
ip = sys.stdin.readline
n,m = map(int,ip().split())
m_list = list(map(int,ip().split()))
c_list = list(map(int,ip().split()))
c_max = 10001
dp = [[0]*(c_max) for _ in range(n+1)] 
dp[0][0] = 0
for i in range(1,n+1):
    for j in range(c_max):
        if j - c_list[i-1] >= 0:
            dp[i][j] = max(m_list[i-1] + dp[i-1][j-c_list[i-1]],dp[i][j]) 
        dp[i][j] = max(dp[i-1][j],dp[i][j])
for i in range(c_max):
    cur = dp[n][i]
    if cur >= m:
        print(i)
        break