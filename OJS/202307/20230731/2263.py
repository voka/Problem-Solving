import sys
ip = sys.stdin.readline
n = int(ip())
sys.setrecursionlimit(100001)
in_order = list(map(int, ip().split()))
post_order = list(map(int, ip().split()))
my_dict = {in_order[i]: i for i in range(n)}


def devide(in_s, in_e, post_s, post_e):
    if in_s >= in_e or post_s >= post_e:
        return
    mid = post_order[post_e-1]
    print(mid, end=" ")
    i = my_dict[mid]
    devide(in_s, i, post_s, post_s + (i - in_s))
    devide(i+1, in_e, post_s + (i - in_s), post_e-1)


def get_pre_order():
    devide(0, n, 0, n)


get_pre_order()
