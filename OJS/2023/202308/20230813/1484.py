import sys
ip = sys.stdin.readline
G = int(ip())
# cur, memory는 모두 자연수
# 15 -> 4**2 - 1**2, 5**2 -
"""
2, 1 -> 3
3, 2 -> 5
4. 3 -> 7
5, 4 -> 9
6, 5 -> 11
7, 6 -> 13
8, 7 -> 15
.... 
멈추는 조건

cur - mem  == 1 and cur **2 - mem**2 > G 인 경우 while문 종료
"""
answer = []
cur, mem = 2, 1  # G는 자연수
while True:
    val = cur**2 - mem**2
    if cur - mem == 1 and val > G:
        break
    if val == G:
        answer.append(cur)
        cur += 1
    elif val > G:
        mem += 1
    else:
        cur += 1
if len(answer) == 0:
    print(-1)
else:
    for i in answer:
        print(i)
