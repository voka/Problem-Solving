import sys
ip = sys.stdin.readline 

cur_dict = {}
N = int(ip())
for i in range(N):
    h,c = ip().split()
    if c == "enter":
        cur_dict[h] = 1
    else:
        cur_dict[h] = 0

_list = sorted(list(cur_dict.keys()), reverse=True)
for k in _list:
    if cur_dict[k] == 1:
        print(k)  
    