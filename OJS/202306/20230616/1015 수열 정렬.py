import sys
ip = sys.stdin.readline
N = int(ip())
arr = list(map(int, ip().split()))  # B-array
new_list = []
for idx, val in enumerate(arr):
    new_list.append((idx, val))
new_list.sort(key=lambda x: (x[1], x[0]))
answer_arr = []
for idx, (a, b) in enumerate(new_list):
    answer_arr.append((a, b, idx))
answer_arr.sort(key=lambda x: x[0])
answer = " ".join([str(i[2]) for i in answer_arr])
print(answer)

"""
b0 b1 02
2 3 1 
b1 b0 b2
0  1  2
1 0 2

"""
