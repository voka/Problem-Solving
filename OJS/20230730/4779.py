import sys
ip = sys.stdin.readlines


def solve(mystr, _len):
    if mystr == '-':
        return mystr
    else:
        m1 = _len//3
        m2 = _len - m1
        # print(0, m1, m2, _len)
        # print(mystr[:m1], mystr[m1:m2], mystr[m2:])
        return solve(mystr[:m1], m1) + ' '*m1 + solve(mystr[m2:], m1)


# cur = int(ip())
_list = ip()
for cur in _list:
    cur = int(cur)
    result = solve('-'*(3**cur), 3**cur)
    print(result)
