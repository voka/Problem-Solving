import sys
ip = sys.stdin.readline

N = int(ip())
K = int(ip())
_list = list(map(int, ip().split()))
_list.sort()
dp = [0]*(N)
for i in range(N-1):
    dp[i] = _list[i+1] - _list[i]
dp.sort(reverse=True)
print(sum(dp[K-1:]))

# 1 6 9 3 6 7
# 1 3 6 6 7 9
# 고속도로 위에 최대 K개의 집중국을 세울 수 있다
# 수신 가능 영역은 고속도로 상에서 연결된 구간으로 나타나게 된다
# N개의 센서가 적어도 하나의 집중국과는 통신이 가능해야 하며
# 집중국의 수신 가능 영역의 길이의 합을 최소화
