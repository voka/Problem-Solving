import sys
ip = sys.stdin.readline
N, d, k, c = map(int, ip().split())
my_dict = {}
circle = []
for i in range(N):
    circle.append(int(ip()))
dp = [0]*(N)
my_dict[c] = 1
for i in range(k):
    cur = circle[i]
    if cur in my_dict:
        my_dict[cur] += 1
    else:
        my_dict[cur] = 1
end = 0
ans = -1
for i in range(k, N+k):
    end = i - k
    pop_item = circle[end]
    if i < N:
        cur = circle[i]
    else:
        cur = circle[i - N]
    if cur in my_dict:
        my_dict[cur] += 1
    else:
        my_dict[cur] = 1
    if my_dict[pop_item] == 1:
        del my_dict[pop_item]
    else:
        my_dict[pop_item] -= 1
    dp[end] = len(my_dict.keys())


print(max(dp))
