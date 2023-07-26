import sys
ip = sys.stdin.readline
# 소형 기관차 3
# 각 소형 기관차가 움직일 수 있는 객차는 정해져 있고 모두 같음
# 객차내의 손님은 이동할 수 없으며 최대한 많은손님을 운송하려고 노력해야함.
# 번호가 연속적으로 이어진 객차를 끌게 해야함.
# 부분수열의 합 ? 비슷한 느낌

myque = []
n = int(ip())
_list = [0]+list(map(int, ip().split()))
dp = [[0]*(n+1) for _ in range(4)]
carry = int(ip())
for i in range(1, n+1):
    _list[i] += _list[i-1]

for i in range(1, 4):
    for j in range(i*carry, n+1):
        dp[i][j] = max(dp[i][j-1], _list[j] -
                       _list[j-carry] + dp[i-1][j-carry])
print(dp[-1][-1])
