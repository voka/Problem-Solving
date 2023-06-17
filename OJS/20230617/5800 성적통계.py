import sys
ip = sys.stdin.readline
K = int(ip())


def get_larget_gap(_list):
    result = -1
    pre = _list[0]
    for i in _list[1:]:
        result = max(result,  i - pre)
        pre = i
    return result


for i in range(1, K+1):
    print("Class {}".format(i))
    _list = list(map(int, ip().split()[1:]))
    _list.sort()
    print("Max {}, Min {}, Largest gap {}".format(
        max(_list), min(_list), get_larget_gap(_list)))
