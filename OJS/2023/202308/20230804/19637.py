import sys
from bisect import bisect_left as bl
ip = sys.stdin.readline

N, M = map(int, ip().split())
badge_dict = {}
badge_scores = []
for _ in range(N):
    a, b = map(str, ip().split())
    b_int = int(b)
    if b_int not in badge_dict:
        badge_dict[b_int] = a
        badge_scores.append(b_int)
strong_list = [int(ip()) for _ in range(M)]

for i in range(M):
    a = bl(badge_scores, strong_list[i])
    print(badge_dict[badge_scores[a]])
