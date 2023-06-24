import sys
ip = sys.stdin.readline
K = int(ip())
stack = []
for i in range(K):
    c = int(ip())
    if c == 0:
        stack.pop()
    else:
        stack.append(c)
print(sum(stack))
