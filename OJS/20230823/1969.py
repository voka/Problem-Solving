import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())
_list = [list(ip().rstrip()) for _ in range(N)]
_list.sort()
ans = 0
my_ans = []
my_key = ['A', 'C', 'G', 'T']
for k in range(M):
    cur_idx = 0
    cur_min = N
    for i in range(4):
        cur = 0
        for j in range(N):
            if _list[j][k] != my_key[i]:
                cur += 1
        if cur_min > cur:
            cur_idx = i
            cur_min = cur
    ans += cur_min
    my_ans.append(my_key[cur_idx])
print(''.join(my_ans))
print(ans)
