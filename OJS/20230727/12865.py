import sys
ip = sys.stdin.readline
N, K = map(int, ip().split())
items = [map(int, ip().split()) for _ in range(N)]
dp = [[0]*(K+1) for _ in range(N+1)]

"""
cur_weight = 
dp[i][items[i][0]]] = max()
"""

for i in range(1, N+1):
    cw, cv = items[i-1]  # 가방에 넣을려는 물건의 무게, 가치
    for j in range(1, K+1):
        if cw <= j:  # 현재 무게가 j 보다 작아 넣을 수 있다면
            # 이전 물건까지 봤을때 j에서 형재 물건을 넣을 수 있을 만큼의 무게를 뺌 지점에 현재 물건의 가치를 더한 값과 현재 물건을 넣지 않고 이전 물건의 가치를 비교
            dp[i][j] = max(dp[i-1][j-cw] + cv, dp[i-1][j])
        else:  # 물건을 넣을 수 없다면 이전 물건의 가치를 그대로 적용
            dp[i][j] = dp[i-1][j]
print(dp[N][K])
