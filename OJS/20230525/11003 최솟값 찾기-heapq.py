import sys
import heapq
ip = sys.stdin.readline
N, L = map(int, ip().split())
A_list = list(map(int, ip().split()))
value_queue = []  # root node 의 범위를 확인하고 현재 범위에 있는 값만 항상 힙에 둔다!


# def get_Di(i):
#     if i < L:
#         val, idx = heapq.heappop(value_queue)
#         heapq.heappush(value_queue, (val, idx))
#         return val
#     else:
#         while True:
#             val, idx = heapq.heappop(value_queue)
#             if i-L < idx <= i:
#                 heapq.heappush(value_queue, (val, idx))
#                 return val


for i in range(N):
    heapq.heappush(value_queue, (A_list[i], i))
    while value_queue[0][1] <= i-L:
        heapq.heappop(value_queue)
    print(value_queue[0][0], end=" ")
