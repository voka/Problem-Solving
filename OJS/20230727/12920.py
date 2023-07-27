import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())
temp = [map(int, ip().split()) for _ in range(N)]
items = []
for i in range(N):
    cv, cc, ck = temp[i-1]
    k = 1
    # 7개가 들어있는 물건에 대해 1, 2, 4배를 한 물건을 list에 담게 되면 1 ~ 7 까지의 모든 수를 나타낼 수 있게 된다.
    # 모든 자연수는 2의 거듭제곱의 합으로 나타낼 수 있음.
    while ck > 0:   # 복사하는 개수를 최소화 하기 위해 2의 지수 사용
        j = min(k, ck)  # 재곱 수 분할
        cur_v = cc * j
        cur_w = cv * j
        items.append((cur_w, cur_v))
        k *= 2
        ck -= j
new_n = len(items)
dp = [[0]*(M+1) for _ in range(new_n+1)]
for i in range(1, new_n+1):
    cur_w, cur_v = items[i-1]
    for w in range(1, M+1):
        if w >= cur_w:
            dp[i][w] = max(dp[i-1][w - cur_w] + cur_v, dp[i-1][w])
        else:
            dp[i][w] = dp[i-1][w]
# print(dp)
print(dp[new_n][M])
