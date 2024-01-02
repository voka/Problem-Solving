import sys
ip = sys.stdin.readline
N = int(ip())
mod = 1000000000
dp = [[[0]*(1 << 10) for _ in range(10)]
      for _ in range(N+1)]  # 0 ~ 9 까지 사용한 자리수를 1111111111 로 나타냄 !!
last = (1 << 10) - 1  # 이진수 1111111111
for i in range(1, 10):
    dp[1][i][1 << i] = 1
for i in range(2, N+1):
    for j in range(10):
        for k in range(1, last+1):  # k|(1<<j) -> j 번짜 자리를 사용했다는 뜻
            if j > 0:
                dp[i][j][k | (1 << j)] = (
                    dp[i][j][k | (1 << j)] + dp[i-1][j-1][k]) % mod
            if j < 9:
                dp[i][j][k | (1 << j)] = (
                    dp[i][j][k | (1 << j)] + dp[i-1][j+1][k]) % mod
answer = 0
for i in range(10):
    answer = (answer + dp[N][i][last]) % mod
print(answer)