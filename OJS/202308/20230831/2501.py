import sys
ip = sys.stdin.readline 

def get_num_list(num):
    i = 1
    result = []
    while i <= num :
        if num % i == 0:
            result.append(i)
        i+= 1
    return result

N,K = map(int,ip().split())

_list = get_num_list(N)
if len(_list) < K:
    print(0)
else:
    print(_list[K-1])
            