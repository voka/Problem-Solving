import sys
ip = sys.stdin.readline
_list = list(map(int, ip().split()))
INF = 500000
n = len(_list) - 1
dp = [[[INF+1]*5 for _ in range(5)] for _ in range(n+1)]
dp[-1][0][0] = 0  # 밟아야 하는 위치, 왼쪽발, 오른쪽 발

# # 반대 경우
is_opposite = [(1, 3), (2, 4), (3, 1), (4, 2)]


def get_move(a, b):
    if a == b:
        return 1
    elif a == 0:
        if b == 0:
            return 0
        return 2
    elif (a, b) in is_opposite:
        return 4
    else:
        return 3


for i in range(n):  # 밟아야 하는 곳
    for r in range(5):  # 오른발의 위치
        for pl in range(5):  # 왼발의 이전 위치
            left_move = get_move(pl, _list[i])
            dp[i][_list[i]][r] = min(
                dp[i][_list[i]][r], dp[i-1][pl][r] + left_move)
    for l in range(5):  # 왼발 위치
        for pr in range(5):  # 오른발의 이전 위치
            right_move = get_move(pr, _list[i])
            dp[i][l][_list[i]] = min(
                dp[i][l][_list[i]], dp[i-1][l][pr] + right_move)

ans = INF + 1
for i in range(5):
    ans = min(ans, min(dp[n-1][i]))
print(ans)
