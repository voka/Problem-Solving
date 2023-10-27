import sys
ip = sys.stdin.readline
from collections import deque 

N = int(ip())
myque = deque()
for i in range(N):
    command = ip().rstrip()
    if command.startswith("push"):
        com,num = command.split()
        myque.append(num)
    elif command.startswith("pop"):
        if myque:
            cur = myque.popleft()
            print(cur)
        else:
            print(-1)
    elif command.startswith("size"):
        print(len(myque))
    elif command.startswith("empty"):
        if len(myque) == 0:
            print(1)
        else:
            print(0)
    elif command.startswith("front"):
        if myque:
            cur = myque.popleft()
            print(cur)
            myque.appendleft(cur)
        else:
            print(-1)
    elif command.startswith("back"):
        if myque:
            cur = myque.pop()
            print(cur)
            myque.append(cur)
        else:
            print(-1)