import sys
ip = sys.stdin.readline 
N = int(ip())
_list = list(map(int,ip().split()))

count_dict = {}

for i in range(N):
    cur = _list[i]
    if cur in count_dict:
        count_dict[cur] += 1
    else:
        count_dict[cur] = 1
        
#print(count_dict)

ans = [-1] * N 
stack = []

for i in range(N):
    cur = _list[i]
    while len(stack) > 0:
        top = _list[stack[-1]]
        if count_dict[top] < count_dict[cur]:
            idx = stack.pop()
            ans[idx] = cur
        else:
            break
    stack.append(i)


print(*ans)
