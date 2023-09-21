import sys,math
ip = sys.stdin.readline 
N = int(ip())
A_list = list(map(int,ip().split()))
B,C = map(int,ip().split())
"""
시험장 안에 총감독 1, 부감독 1
총 응시자 수 -> A명
총감독의 감시 -> B명 -> 1명  
부감독의 감시 -> C명 ->  다수 
"""
answer = 0
for A in A_list:
    if A <= B :
        answer += 1
    else:
        answer += 1 + math.ceil((A - B) / C)
print(answer)


