import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())
_list = [[] for _ in range(N)]
for i in range(N):
    _list[i] = list(map(int, ip().split()))
if N == 1:
    print(1)
else:
    flag_j = [-100]*(M)
    answer = 1
    for i in range(N):
        if max(_list[i]) - min(_list[i]) >= M:
            answer = 0
            break
        for j in range(M):
            cur = _list[i][j] - _list[i][j-1]
            if flag_j[j] == -100:
                flag_j[j] = cur
            else:
                if (flag_j[j] != cur):
                    answer = 0
                    break
    print(answer)

"""
3 5
10 6 8 9 7
5 1 3 4 2
15 11 13 14 12


2 4 6 8 10
1 3 5 7 9 

"""
