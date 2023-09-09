import sys
from copy import deepcopy
ip = sys.stdin.readline 

cur = 100
want = int(ip())
m = int(ip().rstrip())
break_num = set()
if m!= 0 :
    break_num = set(list(map(str,ip().split())))

cur_min = abs(want - cur)
for i in range(1000001):
    cur = str(i)
    flag = True
    for c in cur:
        if c in break_num:
            flag = False
            break
    if flag:
        cur_min = min(cur_min,len(str(i)) + abs(want - i))
print(cur_min)
            