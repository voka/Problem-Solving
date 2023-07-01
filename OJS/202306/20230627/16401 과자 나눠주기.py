import sys
ip = sys.stdin.readline
M, N = map(int, ip().split())
snacks = list(map(int, ip().split()))


def is_possible(max_len):
    count = 0
    for snack in snacks:
        count += snack//max_len
    if count >= M:
        return True
    else:
        return False


s = 1  # 최소 한개
e = max(snacks)
answer = 0
while s <= e:
    m = (s+e)//2
    if is_possible(m):
        answer = max(answer, m)
        s = m + 1
    else:
        e = m - 1
print(answer)
