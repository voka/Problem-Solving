import sys
ip = sys.stdin.readline

N = int(ip())
arr = set()
_list = [int(ip()) for _ in range(N)]
_list.sort()  # 제일 큰 값이 뒤로 가도록 정렬
for i in _list:
    for j in _list:
        arr.add(i + j)


def find_largest():
    for i in range(N-1, -1, -1):
        for j in range(i+1):
            if _list[i] - _list[j] in arr:
                return _list[i]


print(find_largest())
