import sys
ip = sys.stdin.readline

N = int(ip())
maps = [list(map(int,ip().split())) for _ in range(N)]
sum_array = [[[0]* 11 for _ in range(N+1)] for _ in range(N+1)]

def add(a,b):
    result = [0]*11
    for i in range(1,11):
        result[i] = a[i] + b[i]
    return result
def minus(a,b):
    result = [0]*11
    for i in range(1,11):
        result[i] = a[i] - b[i]
    return result
def get_count(a):
    cnt = 0
    for i in range(1,11):
        if a[i] > 0:
            cnt +=1 
    return cnt


for i in range(1,N+1):
    for j in range(1,N+1):
        sum_array[i][j][maps[i-1][j-1]] += 1
        sum_array[i][j] = add(sum_array[i][j],sum_array[i-1][j])
        sum_array[i][j] = add(sum_array[i][j],sum_array[i][j-1])
        sum_array[i][j] = minus(sum_array[i][j],sum_array[i-1][j-1])
    

Q = int(ip())

for i in range(Q):
    x1,y1,x2,y2 = map(int,ip().split())
    _list = sum_array[x2][y2]
    #print(_list)
    _list = minus(_list,sum_array[x1-1][y2])
    #print(_list)
    _list = minus(_list,sum_array[x2][y1-1])
    #print(_list)
    _list = add(_list,sum_array[x1-1][y1-1])
    #print(_list)
    print(get_count(_list))
