import sys
import heapq
ip = sys.stdin.readline
T = int(ip())
while T:
    T -= 1
    N = int(ip())
    arr = []
    for i in range(N//10 + 1):
        arr.extend(list(map(int, ip().split())))
    low, high = [], []

    def put_value(val):
        if len(low) == 0 and len(high) == 0:
            heapq.heappush(low, -val)
        elif len(low) > len(high):
            l = -heapq.heappop(low)
            compare_and_put(l, val)
        else:
            h = heapq.heappop(high)
            compare_and_put(h, val)

    def compare_and_put(a, b):
        if a < b:
            heapq.heappush(low, -a)
            heapq.heappush(high, b)
        else:
            heapq.heappush(low, -b)
            heapq.heappush(high, a)

    def get_mid_value():
        result = heapq.heappop(low)
        heapq.heappush(low, result)
        return -result
    answer = []
    for i in range(N):
        put_value(arr[i])
        if i % 2 == 0:
            answer.append(str(get_mid_value()))
    count = 0
    na = len(answer)
    print(na)
    for i in range(na):
        count += 1
        if count == 10 or i == na - 1:
            print(answer[i])
        else:
            print(answer[i], end=" ")
