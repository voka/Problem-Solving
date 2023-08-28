import sys 
ip = sys.stdin.readline 
delete_set = set()

def cal(num):
    ans = num 
    while num > 0:
        ans += num % 10 
        num = num // 10
    return ans 

for i in range(1,10001):
    temp = cal(i)
    delete_set.add(temp)

for i in range(1,10001):
    if i not in delete_set:
        print(i)
        
