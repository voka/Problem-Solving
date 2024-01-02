import sys
ip = sys.stdin.readline 


stack = []


N = int(ip())
for i in range(N):
    command = ip().rstrip()
    if command[0] == '1':
        command,num = command.split()
        stack.append(num)
    if command == '2':
        if len(stack) > 0:
            print(stack.pop())
        else:
            print(-1)
    if command == '3':
        print(len(stack))
    if command == '4':
        if len(stack) == 0:
            print(1)
        else:
            print(0)
    if command == '5':
        if len(stack) > 0:
            print(stack[-1])
        else:
            print(-1)
    
        
    
