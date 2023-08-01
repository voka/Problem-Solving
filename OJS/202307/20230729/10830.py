import sys
ip = sys.stdin.readline
N, B = map(int, ip().split())
maps = [list(map(int, ip().split())) for _ in range(N)]
"""
1 2 
3 4 

1 * 1 + 2 * 3 => 7
1 * 2 + 2 * 4 => 10
3 * 1 + 4 * 3 => 15
3 * 2 + 4 * 4 => 22
"""


def mul_matrix(a, b):
    result = [[0]*N for _ in range(N)]
    for i in range(N):
        for j in range(N):
            temp = 0
            for k in range(N):
                temp += a[i][k]*b[k][j]
            result[i][j] = temp % 1000
    return result


def solve(a, b):
    # print(b)
    if b == 1:
        for i in range(N):
            for j in range(N):
                a[i][j] %= 1000
        return a
    tmp = solve(a, b//2)
    # print(tmp)
    if b % 2:
        return mul_matrix(mul_matrix(tmp, tmp), a)
    else:
        return mul_matrix(tmp, tmp)


result = solve(maps, B)
# print(result)
for i in result:
    print(*i)
