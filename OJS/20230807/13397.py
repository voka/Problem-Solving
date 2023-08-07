import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())
_list = list(map(int, ip().split()))
start = 0
end = 10000


def can_do(mid):
    cnt = 1
    cur_min = cur_max = _list[0]
    for i in range(1, N):
        cur_min = min(cur_min, _list[i])
        cur_max = max(cur_max, _list[i])
        cur = abs(cur_max - cur_min)
        if cur > mid:
            cur_min = cur_max = _list[i]
            cnt += 1
    return cnt


ans = 100000
while start <= end:
    mid = (start + end) // 2
    # cnt, temp = can_do(mid)
    # print(cnt, temp)
    if can_do(mid) <= M:
        ans = min(ans, mid)
        end = mid - 1
    else:
        start = mid + 1
print(ans)
