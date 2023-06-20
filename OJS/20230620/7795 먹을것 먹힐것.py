import sys
ip = sys.stdin.readline
T = int(ip())


def lower_bound(_list, target):
    start = 0
    end = len(_list)
    # print("START {0}".format(target))
    while start < end:
        mid = (end+start)//2
        # print("CUR {0}.... {1}".format(_list[mid], _list[mid] > target))
        if _list[mid] < target:
            start = mid + 1
        else:
            end = mid  # 이분탐색과 lower_bound, upper_bound 다른 부분
    return end


while T:
    T -= 1
    N, M = map(int, ip().split())
    a_list = list(map(int, ip().split()))
    b_list = list(map(int, ip().split()))
    a_list.sort()
    b_list.sort()
    answer = 0
    for i in a_list:
        cur = lower_bound(b_list, i)
        # print(i, cur)
        if cur > 0:
            answer += cur
    print(answer)
