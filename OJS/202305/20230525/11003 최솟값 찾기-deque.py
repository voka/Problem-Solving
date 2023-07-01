import sys
from collections import deque
ip = sys.stdin.readline
N, L = map(int, ip().split())
myque = deque()
_list = list(map(int, ip().split()))

for i in range(N):
    while myque and myque[0][0] < i-L + 1:  # 범위에서 벗어난 제일 오래된 값 제거
        myque.popleft()
    # 맨 뒤에서 부터 _list[i] 보다 작은 값들을 모두 제거
    while myque and myque[-1][1] > _list[i]:
        myque.pop()
    myque.append((i, _list[i]))
    print(myque[0][1], end=" ")
