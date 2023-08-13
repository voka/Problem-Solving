import sys
ip = sys.stdin.readline

N, K = map(int, ip().split())
_list = list(map(int, ip().split()))
my_dict = {}
i = 0
pre = 0
ans = -1
while i < N:
    cur = _list[i]
    if cur in my_dict:
        my_dict[cur] += 1
    else:
        my_dict[cur] = 1
    if my_dict[cur] > K:
        while True:  # 시작한 곳 부터 증가하면서 cur 을 줄임.
            pre_cur = _list[pre]
            my_dict[pre_cur] -= 1
            pre += 1
            if pre_cur == cur:
                break
    ans = max(ans, i - pre + 1)

    # print(i, pre, my_dict[cur], ans)
    i += 1
print(ans)
