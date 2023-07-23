import sys
ip = sys.stdin.readline

N, K = map(int, ip().split())

p = 10007
dp = [1] * (N+1)

for i in range(1, N+1):
    dp[i] = (dp[i-1]*i) % p


def fermat(n, p, m):
    if p == 1:
        return n % m
    if p % 2:
        return ((fermat(n, p//2, m)**2)*n) % m
    else:
        return (fermat(n, p//2, m) ** 2) % m


# print(dp)
# print(dp[N], dp[N-K], dp[K], (dp[N]//(dp[K]*dp[N-K])))
# print((dp[N]//(dp[K]*dp[N-K]) % p) % p)
print(dp[N] * fermat((dp[K]*dp[N-K]), p-2, p) % p)
