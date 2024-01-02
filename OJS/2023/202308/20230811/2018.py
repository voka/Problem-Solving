import sys
ip = sys.stdin.readline
N = int(ip())
_list = [i for i in range(N+1)]
i, j = 1, 1
ans = 0


def get_ij_sum(i, j):
    j_sum = j*(j+1)/2
    i_sum = i*(i-1)/2
    return j_sum - i_sum


while i <= j:
    cur = get_ij_sum(i, j)
    if cur == N:
       # print(i, j)
        ans += 1
        i += 1
    elif cur < N:
        j += 1
    elif cur > N:
        i += 1
print(ans)
