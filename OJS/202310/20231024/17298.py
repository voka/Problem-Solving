import sys
ip = sys.stdin.readline 

N = int(ip())

_list = list(map(int,ip().split()))

stack = []
ans = [-1]*(N)

for i in range(N):
    while len(stack) > 0:
        if _list[stack[-1]] < _list[i]:
            cur = stack.pop()
            ans[cur] = _list[i]
        else:
            break
    stack.append(i)
        
print(*ans)
