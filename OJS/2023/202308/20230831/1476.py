import sys
ip = sys.stdin.readline 

E,S,M = map(int,ip().split())
cur = 1
e,s,m = 1,1,1
while True:
    if e == E and s == S and m == M:
        break
    e +=1 
    s +=1 
    m +=1
    if e > 15:
        e = 1
    if s > 28:
        s = 1
    if m > 19:
        m = 1
    cur += 1

print(cur)