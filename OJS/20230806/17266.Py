import sys
ip = sys.stdin.readline
N = int(ip())
M = int(ip())
_list = list(map(int, ip().split()))

start = 1
end = 100000
n = len(_list)


def _is(h):
    pre_x = 0  # 확인하는 부분
    for x in _list:
        if x - h <= pre_x:  # 확인해야 하는 부분에 가로등이 비추는지 확인
            pre_x = x + h  # 확인해야 하는 부분 이동
        else:
            return False
    return N <= pre_x  # 마지막 종점이 N 보다 큰지 확인


ans = 100000
while start <= end:
    h = (start + end) // 2
    if _is(h):
        end = h - 1
        ans = min(ans, h)
    else:
        start = h + 1
print(ans)
