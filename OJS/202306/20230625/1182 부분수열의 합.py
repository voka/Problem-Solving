from itertools import combinations
import sys
ip = sys.stdin.readline
N, S = map(int, ip().split())
_list = list(map(int, ip().split()))
answer = 0
for k in range(1, N+1):
    for i in combinations(_list, k):
        total = sum(i)
        if total == S:
            answer += 1
print(answer)
