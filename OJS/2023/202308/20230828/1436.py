import sys
ip = sys.stdin.readline 

def check666(num):
    cnt = 0
    for i in str(num):
        if i == '6':
            cnt += 1
        else:
            cnt = 0
        if cnt == 3:
            return True
    return False
n = int(ip())
cnt = 0
for i in range(666,2666800):
    if check666(i):
        cnt += 1
        if cnt == n :
            print(i)
            break