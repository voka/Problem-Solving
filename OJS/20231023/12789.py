import sys
ip = sys.stdin.readline 

stack = []
N = int(ip())
_list = list(map(int,ip().split()))
l_idx = 0
num = 1
while True :    
    if num == N + 1 and l_idx == N :
        break
    
    if l_idx < N and _list[l_idx] == num:
        num += 1
        l_idx += 1
    elif len(stack) > 0 :
        if stack[-1] == num:
            num += 1
            stack.pop()
        elif l_idx < N:
            stack.append(_list[l_idx])
            l_idx += 1
        else:
            break
    elif l_idx < N:
        stack.append(_list[l_idx])
        l_idx += 1

if num == N + 1 and len(stack) == 0:
    print("Nice")
else:
    print("Sad")


