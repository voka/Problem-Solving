import sys
ip = sys.stdin.readline
N = int(ip())
_list = []
for i in range(N):
    temp = list(map(int, ip().split()))
    _list.append(temp)
a_b_dict = {}
for i in range(N):
    for j in range(N):
        cur = _list[i][0] + _list[j][1]
        if cur not in a_b_dict:
            a_b_dict[cur] = 1
        else:
            a_b_dict[cur] += 1
answer = 0

for i in range(N):
    for j in range(N):
        cur = -(_list[i][2] + _list[j][3])
        if cur in a_b_dict:
            answer += a_b_dict[cur]
print(answer)
